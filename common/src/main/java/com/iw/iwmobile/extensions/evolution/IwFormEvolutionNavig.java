/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.extensions.evolution;

import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.UIManager;
import com.iw.iwmobile.Brain;
import com.iw.iwmobile.IwColorUtil;
import com.iw.iwmobile._fakelibs.Toast;
import com.iw.iwmobile.comm.*;
import com.iw.iwmobile.components.IwButton;
import com.iw.iwmobile.entities.Message;
import com.iw.iwmobile.entities.MessageOption;
import com.iw.iwmobile.entities.MessageType;
import com.iw.iwmobile.entities.Utilities;
import com.iw.iwmobile.forms.IwFormBase;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;

/**
 *
 * @author helio
 */
public class IwFormEvolutionNavig extends IwFormBase {

    long idAdmission;
    String patientShortName;
    Date dateMin;
    //private MultiList mlEvolutions;
    private Button btSearch;
    private Button btAdd;
    private boolean hideSummary = false;
    private boolean evolEditable = false; // default value 
    private boolean isUserInActiveShift = false;
    private boolean hasPendingCheckout = false;
    private boolean hasGeoLocation = false;
    private int admissiontype = -1;
    IwExtContextInterface xCTx;
    private int regType = -1;
    private Map<String,MobRecordset> my_resultMap;
    private boolean disabled_AddEvol_Without_Checkin = false;
    public IwFormEvolutionNavig(String idAdmission, String patientShortName, Date dateMin,
                                boolean hasPendingCheckout, boolean hasGeoLocation, int admissiontype ) {
        this(idAdmission, patientShortName, dateMin, false, -1, hasPendingCheckout, hasGeoLocation, admissiontype);
    }

    public IwFormEvolutionNavig(String idAdmission, String patientShortName, boolean hasPendingCheckout,
                                boolean hasGeoLocation, int admissiontype) {
        this(idAdmission,patientShortName,null,hasPendingCheckout,hasGeoLocation,admissiontype);
    }
    
    public IwFormEvolutionNavig(String idAdmission, String patientShortName, Date dateMin, boolean hideSummary,
                                int regType, boolean hasPendingCheckout, boolean hasGeoLocation, int admissiontype ) {
        super();
        this.idAdmission = Long.parseLong(idAdmission);
        this.patientShortName = patientShortName;
        this.hideSummary = hideSummary;
        this.regType = regType;
        this.hasPendingCheckout = hasPendingCheckout;
        this.hasGeoLocation = hasGeoLocation;
        this.admissiontype = admissiontype;
        
        if (dateMin!=null){
            this.dateMin = dateMin;
        }
        initializeContext();
        
        String title =
            getIwTranslation(TT_EVOLUTIONS_ABBREV)
            + ": " 
            + patientShortName
            + " ["
            + idAdmission
            + "]";
        setTitle(title);
        
        Brain.getInstance()
            .setFormMenu(
                IwFormEvolutionNavig.this,
                Brain.getInstance().MENU_STANDARD
            );        
        
        // important when Form has a List inside its container.
        // because scrolling will come from a list.
        setScrollableY(false);
        
        preInit();
        init();
        searchRows(null);
        
    }

    private void initializeContext(){
        try{
            this.xCTx = new IwExtContext(this.idAdmission, this.patientShortName);
        }
        catch(Exception ex){
            return;
        }
    }
    

    IwFormEvolutionNavig self() {
        return this;
    } 

    private void preInit() {
        btAdd = create_btAdd();
        btSearch = create_btSearch();
    }

    
    private void init() {
        // define container for buttons
        Container southContainer = new Container(new BorderLayout());
            Container southButtons = new Container(new GridLayout(1,2));
                southButtons.addComponent(btSearch);
                southButtons.addComponent(btAdd);
        southContainer.addComponent(BorderLayout.CENTER,southButtons);
        southContainer.getStyle().setBgColor(IwColorUtil.LOW_RED);

        // define content container
        Container c = getContentPane();
            c.setLayout(new BorderLayout());
            Container cList = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            cList.setScrollableY(true);

        c.addComponent(BorderLayout.CENTER, cList);
        c.addComponent(BorderLayout.SOUTH, southContainer);
    }
    
    private void searchRows (MobRecordset rsFilter) {
        
        IwServiceCallerInterface caller =
                Brain.getInstance().getIwServiceCaller();
        
        IwHttpRequesterCallBack<Map<String,MobRecordset>> callback =
                new IwHttpRequesterCallBack<Map<String,MobRecordset>>() {

            @Override
            public void onFailure(MobRecordsetError rsError) {
                btSearch.setEnabled(true);
                evolsSelected.clear();
                updateButtonsState();
                Dialog.show("Alert", rsError.getTranslation(), "OK", null);
            }

            @Override
            public void onSuccess(Map<String,MobRecordset> resultMap) {
                my_resultMap = resultMap;

                Container cList = createMultiButtonList(resultMap);
                getContentPane().addComponent(BorderLayout.CENTER, cList);
                
                btSearch.setEnabled(true);
                evolsSelected.clear();
                
                MobRecordset rsSecurity = resultMap.get("rsSecurity");
                if (rsSecurity != null) {
                    for (MobRow r : rsSecurity.rows) {
                        if ( "true".equals(r.field("evolEditable").getValue())) {
                            evolEditable = true;
                        }
                        else {
                            evolEditable = false;
                        }
                    }
                }
                updateButtonsState();
                getContentPane().forceRevalidate();
            }
        };

        btSearch.setEnabled(false);
        caller.getEvolutions(this.idAdmission, callback);
    }
    
    private TreeMap<String,HashMap> tmEvolutions = new TreeMap<>();
    
    private Container createMultiButtonList(Map<String,MobRecordset> resultMap){
        Container cList = new Container(new BoxLayout(BoxLayout.Y_AXIS)); 
        cList.setScrollableY(true);
        
        Image paperImg = 
                Brain.getInstance().getImage(Brain.IMAGE_EVOLUTION_ENABLED);
                //Brain.getInstance().getStateMashine()._GetResources().getImage("paper.jpg");
        if (paperImg != null) {
            paperImg = paperImg.scaledHeight(50);
        }
        
        MobRecordset rsEvolutions = resultMap.get("rsEvolutions");
        try{
            rsEvolutions = sortEvolutions(rsEvolutions);
        }
        catch(Exception ex){
            Message.show(MessageType.ERROR, MessageOption.OK, ex.getMessage());
        }
        
        tmEvolutions = new TreeMap<>();
        HashMap<String,Object> itemMap = null;
        evolsSelected = new ArrayList<Long>();
        
        for(MobRow r : rsEvolutions.rows) {
            
            String shortName = r.field("PROFSHORTNAME").getValue();
            shortName = (shortName == null)? "" : shortName.trim();
            
            String fullName = r.field("PROFESSIONALNAME").getValue();
            fullName = (fullName == null)? "" : fullName.trim();
            
            String firstName = "";
            StringTokenizer st = new StringTokenizer(fullName);
            if (st.countTokens() > 0) {
                firstName = st.nextToken();
            }
            
            String name = (shortName.length() > 0)? shortName : firstName;
            
            String line1 =
                new StringBuilder()
                    .append(name)
                    .append("[")
                    .append(r.field("REGISTRYTYPE").getValue())
                    .append(": ")
                    .append(r.field("REGISTRYNUMBER").getValue())
                    .append("]")
                    .toString();
            
            String line2 = r.field("SCSPECIALITYNAME").getValue();
            
            String line3 = r.field("TEMPLATENAME").getValue();
                                
            String line4 =
                new StringBuilder()
                .append(
                    
                    Brain.getInstance()
                        .fmtDate((Date) r.field("STARTDATE").objValue())
                    + " "
                    + Brain.getInstance()
                        .fmtTime((Date) r.field("STARTDATE").objValue())
                    // old implemetation - windows phon
                    //e doesn't implemet SimpleDatefoemat    
                    //new SimpleDateFormat("dd/MM/yyyy HH:mm")
                    //    .format((Date) r.field("STARTDATE").objValue())
                        
                )
                .append(" - #")
                .append(r.field("ID").getValue())
                .toString();
                           
            itemMap = new HashMap<String,Object>();
            if (paperImg != null) {
                itemMap.put("icon" , paperImg);
            }
            itemMap.put("Line1", line1);
            itemMap.put("Line2", line2);
            itemMap.put("Line3", line3);
            itemMap.put("Line4", line4);
            itemMap.put("idEvolution", r.field("ID").objValue());
            itemMap.put("emblem", "false");
            itemMap.put("idProfessional", r.field("IDProfessional").objValue());
            
            tmEvolutions.put(((Long)r.field("ID").objValue()).toString(),itemMap);
            
            MultiButton btn = new MultiButton();
            btn.setTextLine1(line1);
            btn.setTextLine2(line2);
            btn.setTextLine3(line3);
            btn.setTextLine4(line4);
            btn.setName(((Long)r.field("ID").objValue()).toString());
            if (Brain.getInstance().isOnlineMode()){
                btn.setCheckBox(true);
            }
            else{
                btn.setRadioButton(true);
                btn.setGroup("RadioGroup");
            }
            btn.setIcon(paperImg);
            cList.add(btn);

            btn.addActionListener((evt) -> {
                refreshEvolsSelected(btn.getParent());
            });
            
        }
        
        if (rsEvolutions==null||rsEvolutions.rows.size() == 0) {
            Dialog.show(
                "INFO",
                getIwTranslation(TT_MSG_NO_EVOLUTIONS),
                "OK",
                null);
        }
        
        return cList;
    }
    
    private void refreshEvolsSelected(Container c){
        evolsSelected = new ArrayList<Long>();
        for (int i = 0;i<c.getComponentCount();i++){
            MultiButton mb = (MultiButton)c.getComponentAt(i);
            if (mb.isSelected()){
                Long id = Long.parseLong(mb.getName());
                evolsSelected.add(id);
            }
        }
        updateButtonsState();
        
    }

    private void saveMyState() {
    }

    public void restoreMyState() {
    }
    
    private Button create_btSearch() {
        
//        Image searchImg =
//            Brain.getInstance().getImage(Brain.IMAGE_SEARCH_ENABLED);
//
//        Button btn = new IwButton();
        com.codename1.ui.plaf.Style myStyle = UIManager.getInstance().getComponentStyle("Button");
        myStyle.setBgColor(IwColorUtil.GREEN);
        myStyle.setFgColor(IwColorUtil.BLACK);
        myStyle.setBorder(RoundBorder.createLineBorder(5));
        Image searchImg = FontImage.createMaterial(FontImage.MATERIAL_SEARCH,myStyle).scaledWidth(100);
//        Image addImg =
//            Brain.getInstance().getImage(Brain.IMAGE_ADD_ENABLED);

        Button btn = new IwButton(); //TT_ADD



        btn.setIcon(searchImg);
        btn.setVisible(true); // Initial state
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                searchRows(null);
            }
        });
        return btn;
    } 

    private Button create_btAdd() {
        com.codename1.ui.plaf.Style myStyle = UIManager.getInstance().getComponentStyle("Button");
        myStyle.setBgColor(IwColorUtil.GREEN);
        myStyle.setFgColor(IwColorUtil.BLACK);
        myStyle.setBorder(RoundBorder.createLineBorder(5));
        Image img = FontImage.createMaterial(FontImage.MATERIAL_ADD,myStyle).scaledWidth(100);
//        Image addImg =
//            Brain.getInstance().getImage(Brain.IMAGE_ADD_ENABLED);
        
        Button btn = new IwButton(); //TT_ADD
        btn.setIcon(img);
        btn.setVisible(true); // Initial state
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Brain.getInstance().setFormEvolutionNavigInstance(IwFormEvolutionNavig.this);
                Form f = showAddEvolution();
                Brain.getInstance().setFormAddEvolutionInstance(f);
            }
            
            private Form showAddEvolution() {
                saveMyState();
                Form f = null;
                if (Brain.getInstance().isOnlineMode()) {
                    f = new IwFormAddEvolution11(
                            idAdmission,
                            patientShortName
                    );
                }

                String backTitle = getIwTranslation(TT_BACK);
                Command backCommand = new Command(backTitle) {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        
                        if (ev != null
                            && ev.getSource() != null
                            && ev.getSource() instanceof Button) {
                            //System.out.println("Sai edição atraves do Button Save");
                            // Only go back IwFormEvolutionNavig form.
                            // If the nack command was launched by save Button,
                            // It isn't necessary ask for permission
                            // to avoid data lost.
                            IwFormEvolutionNavig.this.showBack();
                            IwFormEvolutionNavig.this.searchRows(null);
                            restoreMyState();
                        }
                        else {
                            // Ask for permission to go back.
                            //System.out.println("Sai edição outro mecanismo.");
                            StringBuilder sbMsg = 
                                new StringBuilder()
                                    .append("Podem existir dados não salvos.\n")
                                    .append("Deseja mesmo sair da tela\n")
                                    .append("de adição de Evoluções?\n");
                            Boolean userResp = Dialog.show(
                                "Alerta",
                                sbMsg.toString(),
                                getIwTranslation(TT_YES),
                                getIwTranslation(TT_NO)
                            );
                            if (userResp) {
                                IwFormEvolutionNavig.this.showBack();
                                IwFormEvolutionNavig.this.searchRows(null);
                                restoreMyState();
                            }
                        }
                    } 
                };
                
                f.setBackCommand(backCommand);
                f.show();
                return f;
                
            }            
            
        });
        return btn;
    }


    private HashMap getEvolutionSelected(){
        return tmEvolutions.get(((Long)evolsSelected.get(0)).toString());
    }
    

    private void updateButtonsState() {
    }

    private ArrayList<Long> evolsSelected = new ArrayList<Long>();

    private MobRecordset sortEvolutions(MobRecordset rsEvolutions) throws Exception{
        
        if (rsEvolutions==null||rsEvolutions.rows.isEmpty()) {
            return rsEvolutions;
        }
        MobRecordset rsEvolutionsAux = Utilities.clone(rsEvolutions, false, false);
        MobRecordset rsResult = new MobRecordset();
        
        ArrayList ar1 = new ArrayList(rsEvolutions.rows.size());
        ArrayList ar2 = new ArrayList(rsEvolutions.rows.size());
        Object[] ar3 = new Object[rsEvolutions.rows.size()];
        
        for (MobRow rEvolutions : rsEvolutions.rows){
            ar1.add(rEvolutions);
        }
        for (MobRow rEvolutionsAux : rsEvolutionsAux.rows){
            ar2.add(rEvolutionsAux);
        }
                
        for (int i=0;i<ar1.size();i++){
            MobRow ri = (MobRow)ar1.get(i);
            Long ID_i = (Long)ri.field("ID").objValue();
            int count = 0;
            for (int j=0;j<ar2.size();j++){
                MobRow rj = (MobRow)ar2.get(j);
                Long ID_j = (Long)rj.field("ID").objValue();
                if (ID_i.longValue()<ID_j.longValue()){
                    count++;
                }
            }
            ar3[count] = ri;
        }    
        for (int i=0;i<ar3.length;i++){
            rsResult.addRow((MobRow)ar3[i]);
        }
        return rsResult;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.extensions;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.table.TableLayout;
import com.iw.iwmobile.Brain;
import com.iw.iwmobile.comm.*;
import com.iw.iwmobile.components.IwButton;
import com.iw.iwmobile.forms.IwFormBase;
import com.iw.iwmobile.forms.IwFormInputDate1;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.iw.iwmobile.IwConstantsInterface.TT_CANCEL;
import static com.iw.iwmobile.IwConstantsInterface.TT_OK;

/**
 *
 * @author Gustavo
 */
public class IwDialogEditDiagnostic extends IwFormBase {
    
    
    public final int USER_ACTION_DONOTHING = -1;
    private int userAction = USER_ACTION_DONOTHING; // Default Action
    private final Button btnCancel;
    private final Button btnOk;
    private final Button btnEditSinceDate;
    private final Button btnEraseSinceDate;
    private final Button btnEditUntilDate;
    private final Button btnEraseUntilDate;
    
    private TextField txtSeq;
    private TextField txtSinceDate;
    private TextField txtUntilDate;
    private Picker pckScDiagnostic;
    private Container pnlCenter;
    private int cBlack = 0x000000;
    private Long ID = null;
    private HashMap hmScDiagnostics;
    private MobRecordset rsScDiagnosticList;
    private ArrayList arDiagnostics;
    IwExtContextInterface xCTx;
    
    public IwDialogEditDiagnostic(IwExtContextInterface xCTx, String title) {    
        super();
        setTitle(title);
        this.xCTx = xCTx;
        
        getDiagnosticLists();
        
        txtSeq = createTextFieldSeq();
        pckScDiagnostic = createPckScDiagnostic();
        txtSinceDate = createTextFieldSinceDate();
        txtUntilDate = createTextFieldUntilDate();
        
        btnCancel = createBtnCancel();
        btnOk = createBtnOk();
        btnEditSinceDate = createBtnEditSinceDate();
        btnEraseSinceDate = createBtnEraseSinceDate();
        btnEditUntilDate = createBtnEditUntilDate();
        btnEraseUntilDate = createBtnEraseUntilDate();
        
        pnlCenter = new Container(new TableLayout(9,1));

        Container cSeq = new Container(new BorderLayout());
        cSeq.getStyle().setBorder(Border.createLineBorder(1));
        cSeq.getStyle().setMargin(5, 5, 5, 5);
        cSeq.addComponent(BorderLayout.WEST,new Label("Seq "));
        cSeq.addComponent(BorderLayout.CENTER,txtSeq);
        cSeq.setScrollableY(false);
        
        Container cScDiagnostic = new Container(new BorderLayout());
        cScDiagnostic.getStyle().setBorder(Border.createLineBorder(1));
        cScDiagnostic.getStyle().setMargin(5, 5, 5, 5);
        cScDiagnostic.addComponent(BorderLayout.WEST,new Label("Nome "));
        cScDiagnostic.addComponent(BorderLayout.CENTER,pckScDiagnostic);
        cScDiagnostic.setScrollableY(false);

        Container cSinceDate = new Container(new BorderLayout());
        cSinceDate.getStyle().setBorder(Border.createLineBorder(1));
        cSinceDate.getStyle().setMargin(5, 5, 5, 5);
        cSinceDate.addComponent(BorderLayout.WEST,new Label("Data Início: "));
        cSinceDate.addComponent(BorderLayout.CENTER,txtSinceDate);
        Container cButtonsSinceDate = new Container(new BorderLayout());
        cButtonsSinceDate.addComponent(BorderLayout.WEST,btnEditSinceDate);
        cButtonsSinceDate.addComponent(BorderLayout.EAST,btnEraseSinceDate);
        btnEditSinceDate.setSize(new Dimension(2,2));
        btnEraseSinceDate.setSize(new Dimension(2,2));
        cButtonsSinceDate.setSize(new Dimension(cButtonsSinceDate.getWidth(),txtSinceDate.getHeight()));
        cSinceDate.addComponent(BorderLayout.EAST,cButtonsSinceDate);
        cSinceDate.setScrollableY(false);
        
        Container cUntilDate = new Container(new BorderLayout());
        cUntilDate.getStyle().setBorder(Border.createLineBorder(1));
        cUntilDate.getStyle().setMargin(5, 5, 5, 5);
        cUntilDate.addComponent(BorderLayout.WEST,new Label("Data Resolução: "));
        cUntilDate.addComponent(BorderLayout.CENTER,txtUntilDate);
        Container cButtonsUntilDate = new Container(new BorderLayout());
        cButtonsUntilDate.addComponent(BorderLayout.WEST,btnEditUntilDate);
        cButtonsUntilDate.addComponent(BorderLayout.EAST,btnEraseUntilDate);
        btnEditUntilDate.setSize(new Dimension(2,2));
        btnEraseUntilDate.setSize(new Dimension(2,2));
        cButtonsUntilDate.setSize(new Dimension(cButtonsUntilDate.getWidth(),txtUntilDate.getHeight()));
        cUntilDate.addComponent(BorderLayout.EAST,cButtonsUntilDate);
        cUntilDate.setScrollableY(false);

        pnlCenter.addComponent(cSeq);
        pnlCenter.addComponent(cScDiagnostic);
        pnlCenter.addComponent(cSinceDate);
        pnlCenter.addComponent(cUntilDate);
        
        btnOk.setEnabled(true);
        btnCancel.setEnabled(true);
        
        Container pnlSouth = new Container(new GridLayout(1,2));
        pnlSouth.addComponent(btnOk);
        pnlSouth.addComponent(btnCancel);

        setLayout(new BorderLayout());
        addComponent(BorderLayout.CENTER, pnlCenter);
        addComponent(BorderLayout.SOUTH, pnlSouth);  
        
    }    

    private TextField createTextFieldSeq() {
        TextField txt = new TextField();
        txt.getAllStyles().setBorder(Border.createLineBorder(1, 0));
        txt.getAllStyles().setFont(
            Font.createSystemFont(
                Font.FACE_PROPORTIONAL,
                Font.STYLE_PLAIN,
                Font.SIZE_MEDIUM
            )
        );
        txt.setEditable(false);
        return txt;
    }

    private Picker createPckScDiagnostic() {
        Picker pck = new Picker();
        pck.getStyle().setFont(
            Font.createSystemFont(
                Font.FACE_PROPORTIONAL,
                Font.STYLE_PLAIN,
                Font.SIZE_MEDIUM
            ));
        
        ArrayList<String> arlistscdiagnostic = new ArrayList<String>();
        for (MobRow r : rsScDiagnosticList.rows) {
            arlistscdiagnostic.add(r.field("Name").getValue());
        }
        if (!arlistscdiagnostic.isEmpty()){
            String[] ardiagnostics = arlistscdiagnostic.toArray(new String[arlistscdiagnostic.size()]);
            pck.setStrings(ardiagnostics);
        }
        else{
            pck.setStrings(new String[]{" "});
            pck.setSelectedStringIndex(0);
        }
        pck.addActionListener(ee -> {
            String ScDiagnosticName = pck.getSelectedString();
            HashMap hm = (HashMap)hmScDiagnostics.get(ScDiagnosticName);
        });            
        
        return pck;
    }
    
    private TextField createTextFieldSinceDate() {
        TextField txt = new TextField();
        txt.getAllStyles().setBorder(Border.createLineBorder(1, 0));
        txt.getAllStyles().setFont(
            Font.createSystemFont(
                Font.FACE_PROPORTIONAL,
                Font.STYLE_PLAIN,
                Font.SIZE_MEDIUM
            )
        );
        txt.setEditable(false);
        return txt;
    }

    private TextField createTextFieldUntilDate() {
        TextField txt = new TextField();
        txt.getAllStyles().setBorder(Border.createLineBorder(1, 0));
        txt.getAllStyles().setFont(
            Font.createSystemFont(
                Font.FACE_PROPORTIONAL,
                Font.STYLE_PLAIN,
                Font.SIZE_MEDIUM
            )
        );
        txt.setEditable(false);
        return txt;
    }
    
    private Button createBtnOk() {
        Button btn = new Button(getIwTranslation(TT_OK));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (pckScDiagnostic.getSelectedStringIndex()==-1){
                    Dialog.show("Erro","Campo ' Nome ' deve ser preenchido.","OK",null);
                }
                else{
                    userAction = 1; 
                    Display.getInstance().getCurrent().getBackCommand().actionPerformed(null);
                }
            }
        });
        return btn;
    }

    private Button createBtnEditSinceDate() {
        
        Image searchImg = Brain.getInstance().getImage(Brain.IMAGE_EDIT_ENABLED);
        Form parenForm = this;
        Button btn = new IwButton();
        btn.setIcon(searchImg);
        btn.setVisible(true); // Initial state
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                final IwFormInputDate1 f = new IwFormInputDate1(" Início", txtSinceDate.getText());
                Command backCommand = new Command("Voltar") {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        txtSinceDate.setText(f.getStrEditedDate());
                        parenForm.showBack();
                    } 
                 }; 
                 f.setBackCommand(backCommand);               
                 f.show();
            }
        });
        return btn;
    }

    private Button createBtnEditUntilDate() {
        
        Image searchImg = Brain.getInstance().getImage(Brain.IMAGE_EDIT_ENABLED);
        Form parenForm = this;
        Button btn = new IwButton();
        btn.setIcon(searchImg);
        btn.setVisible(true); // Initial state
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                final IwFormInputDate1 f = new IwFormInputDate1(" Término", txtUntilDate.getText());
                Command backCommand = new Command("Voltar") {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        txtUntilDate.setText(f.getStrEditedDate());
                        parenForm.showBack();
                    } 
                 }; 
                 f.setBackCommand(backCommand);               
                 f.show();
            }
        });
        return btn;
    }
    
    private Button createBtnEraseSinceDate() {
        
        Image searchImg = Brain.getInstance().getImage(Brain.IMAGE_REMOVE_ENABLED);
        
        Button btn = new IwButton();
        btn.setIcon(searchImg);
        btn.setVisible(true); // Initial state
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                txtSinceDate.setText(null);
            }
        });
        return btn;
    }

    private Button createBtnEraseUntilDate() {
        
        Image searchImg = Brain.getInstance().getImage(Brain.IMAGE_REMOVE_ENABLED);
        
        Button btn = new IwButton();
        btn.setIcon(searchImg);
        btn.setVisible(true); // Initial state
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                txtUntilDate.setText(null);
            }
        });
        return btn;
    }
    
    private Button createBtnCancel() {
        Button btn = new Button(getIwTranslation(TT_CANCEL));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                userAction = USER_ACTION_DONOTHING;
                Display.getInstance().getCurrent().getBackCommand().actionPerformed(null);            
            }
        });
        return btn;
    }

    public int getUserAction() {
        return this.userAction;
    }
    
    public void setNextSeq(int seq){
        txtSeq.setText((new Integer(seq)).toString());
    }
    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");    
    public void setDiagnostic(HashMap diagnostic){
        if (diagnostic.get("ID")!=null){
            ID = (Long)diagnostic.get("ID");
        }
        if (diagnostic.get("SEQ")!=null){
            txtSeq.setText(((Integer)diagnostic.get("SEQ")).toString());
        }
        if (diagnostic.get("SCDIAGNOSTICNAME")!=null){
            String scDiagnosticName = (String)diagnostic.get("SCDIAGNOSTICNAME");
            pckScDiagnostic.setSelectedStringIndex(arDiagnostics.indexOf(scDiagnosticName));
        }
        if (diagnostic.get("SINCEDATE")!=null){
            txtSinceDate.setText(sdf.format((Date)diagnostic.get("SINCEDATE")));
        }
        if (diagnostic.get("UNTILDATE")!=null){
            txtUntilDate.setText(sdf.format((Date)diagnostic.get("UNTILDATE")));
        }
        
    }    

    public HashMap getDiagnostic(){
        HashMap hmResult = new HashMap();
        
        hmResult.put("ID", ID);
        
        hmResult.put("SEQ", Integer.parseInt(txtSeq.getText()));
        //hmResult.put("SEQ", 0);
        
        if (ID==null&&pckScDiagnostic.getText().trim().length()>0){
            hmResult.put("SCDIAGNOSTICNAME", pckScDiagnostic.getText());
            HashMap hm = (HashMap)hmScDiagnostics.get(pckScDiagnostic.getText());
            hmResult.put("SCDIAGNOSTIC", (Long)hm.get(0));
        }
        else if (ID!=null&&pckScDiagnostic.getText().trim().length()>0){
            hmResult.put("SCDIAGNOSTICNAME", pckScDiagnostic.getText());
            HashMap hm = (HashMap)hmScDiagnostics.get(pckScDiagnostic.getText());
            hmResult.put("SCDIAGNOSTIC", (Long)hm.get(0));
        }
        else{
            hmResult.put("SCDIAGNOSTICNAME", null);
        }

        
        try{
            hmResult.put("SINCEDATE", (Date)sdf.parse(txtSinceDate.getText()));  
        } 
        catch (Exception ex) {
            hmResult.put("SINCEDATE", null);
        }

        try{
            hmResult.put("UNTILDATE", (Date)sdf.parse(txtUntilDate.getText()));  
        } 
        catch (Exception ex) {
            hmResult.put("UNTILDATE", null);
        }

        return hmResult;
    }

    private void getDiagnosticLists(){
        try{
            MobRecordset rsFilter = new MobRecordset();
            MobField fIDSynonym = new MobField("IDSYNONYM",MobField.LONG,new Long(33));
            MobRow rFilter = new MobRow();
            rFilter.addField(fIDSynonym);
            rsFilter.addRow(rFilter);

            Map<String,Object> pMap = new HashMap<String,Object>();
            pMap.put("rsFilter", rsFilter);
            pMap.put("rsInput", new MobRecordset());
            
            Map<String,MobRecordset> resultMap = xCTx.execIwService( "BOGetSccExec","MtsGetExeStructureCode","GetCodesFrom", pMap);                    
            rsScDiagnosticList = resultMap.get("rsResult");
            
            hmScDiagnostics = new HashMap();
            arDiagnostics = new ArrayList();
            for (MobRow r : rsScDiagnosticList.rows) {
                HashMap hmScDiagnosticsItem = new HashMap();
                hmScDiagnosticsItem.put(0,(Long)r.field("ID").objValue());
                hmScDiagnosticsItem.put(1,r.field("Name").getValue());
                hmScDiagnostics.put(r.field("Name").getValue(),hmScDiagnosticsItem);
                arDiagnostics.add(r.field("Name").getValue());
            }

        } 
        catch(IwCommException ex){
            Dialog.show("Alert",ex.getRsError().getTranslation(),"OK",null);
        }
        catch(Exception ezx){
            Dialog.show("Erro", ezx.getMessage(), "OK", null);
        }
    }
    
}

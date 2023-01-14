/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.extensions;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.iw.iwmobile.Brain;
import com.iw.iwmobile.comm.*;
import com.iw.iwmobile.components.IwButton;
import com.iw.iwmobile.components.IwHtmlTagInterface;
import com.iw.iwmobile.components.IwSettingsButton;
import com.iw.iwmobile.forms.IwFormBase;
//import com.pmovil.toast.Toast;
import com.iw.iwmobile._fakelibs.Toast;
import java.util.HashMap;
import java.util.Map;

import static com.iw.iwmobile.IwConstantsInterface.TT_BACK;
import static com.iw.iwmobile.extensions.IwFormDiagnostics.TT_EXECUTION_OK;
import static com.iw.iwmobile.extensions.IwFormDiagnostics.TT_MSG_NO_RECORDS_FOUND;

/**
 *
 * @author Gustavo
 */
public class IwFormSCDiagnostics extends IwFormBase implements IwHtmlTagInterface{
    
    IwExtContextInterface xCTx;
    public final int USER_ACTION_DONOTHING = -1;
    private int userAction = USER_ACTION_DONOTHING; // Default Action
    private final Button btnSave;
    private final IwSettingsButton btnSettings;
    private final Button btnSearchDiagn1;
    private final Button btnSearchDiagn2;
    private final Button btnSearchDiagn3;
    private final Button btnSearchDiagn4;
    private final Button btnEditPatientNotes;
    
    private TextArea txtCause;
    private TextArea txtScDiagnostic1Name;
    private TextArea txtScDiagnostic2Name;
    private TextArea txtScDiagnostic3Name;
    private TextArea txtScDiagnostic4Name;
    private TextArea txtPatientNotes;
    private Long SccDiagnostic1;
    private Long SccDiagnostic2;
    private Long SccDiagnostic3;
    private Long SccDiagnostic4;
    private Map<String,MobRecordset> my_resultMap;
    private Container pnlCenter;
    private int cBlack = 0x000000;
    final static public String TT_SELECT_SCCODE = "Selecionar código";
    final static public String TT_ALLERGY = "Alergias";
    
    public IwFormSCDiagnostics(IwExtContextInterface xCTx, String title) {    
        super();
        setTitle(title);
        this.xCTx = xCTx;

        btnSave = createBtnSave();
        btnSettings = new IwSettingsButton();
        btnSearchDiagn1 = createSearchDiagn1();
        btnSearchDiagn2 = createSearchDiagn2();
        btnSearchDiagn3 = createSearchDiagn3();
        btnSearchDiagn4 = createSearchDiagn4();
        btnEditPatientNotes = createBtnEditPatientNotes();
        
        init();
        postInit();
    }
    
    
    private void init(){    
        txtCause = createTextAreaCause();
        txtScDiagnostic1Name = createTextAreaScDiagnostic1Name();
        txtScDiagnostic2Name = createTextAreaScDiagnostic2Name();
        txtScDiagnostic3Name = createTextAreaScDiagnostic3Name();
        txtScDiagnostic4Name = createTextAreaScDiagnostic4Name();
        txtPatientNotes = createTextAreaPatientNotes();
        
        pnlCenter = new Container(BoxLayout.y());

        Label lbCause = new Label("Motivo da Internação");
        lbCause.getStyle().setMargin(5, 5, 5, 5);
        pnlCenter.addComponent(lbCause);
        
        Container cCause = new Container(new BorderLayout());
        cCause.getStyle().setMargin(5, 5, 5, 5);
        cCause.addComponent(BorderLayout.CENTER,txtCause);
        cCause.setScrollableY(false);
        
        pnlCenter.addComponent(cCause);

        Label lb1 = new Label("Cód. Diagnóstico Internação");
        lb1.getStyle().setMargin(5,5,5,5);
        pnlCenter.addComponent(lb1);
        
        Container cScDiagnostic1 = new Container(new BorderLayout());
        cScDiagnostic1.getStyle().setMargin(5, 5, 5, 5);
        cScDiagnostic1.addComponent(BorderLayout.CENTER,txtScDiagnostic1Name);
        cScDiagnostic1.addComponent(BorderLayout.EAST,btnSearchDiagn1);
        btnSearchDiagn1.setSize(new Dimension(2,2));
        cScDiagnostic1.setScrollableY(false);
        pnlCenter.addComponent(cScDiagnostic1);
        
        Label lb2 = new Label("Cód. Diagnóstico Base (1)");
        lb2.getStyle().setMargin(5,5,5,5);
        pnlCenter.addComponent(lb2);
        
        Container cScDiagnostic2 = new Container(new BorderLayout());
        cScDiagnostic2.addComponent(BorderLayout.CENTER,txtScDiagnostic2Name);
        cScDiagnostic2.addComponent(BorderLayout.EAST,btnSearchDiagn2);
        btnSearchDiagn2.setSize(new Dimension(2,2));
        cScDiagnostic2.setScrollableY(false);
        pnlCenter.addComponent(cScDiagnostic2);

        Label lb3 = new Label("Cód. Diagnóstico Base (2)");
        lb3.getStyle().setMargin(5,5,5,5);
        pnlCenter.addComponent(lb3);
        
        Container cScDiagnostic3 = new Container(new BorderLayout());
        cScDiagnostic3.getStyle().setMargin(5, 5, 5, 5);
        cScDiagnostic3.addComponent(BorderLayout.CENTER,txtScDiagnostic3Name);
        cScDiagnostic3.addComponent(BorderLayout.EAST,btnSearchDiagn3);
        btnSearchDiagn3.setSize(new Dimension(2,2));
        cScDiagnostic3.setScrollableY(false);
        pnlCenter.addComponent(cScDiagnostic3);

        Label lb4 = new Label("Cód. Diagnóstico Base (3)");
        lb4.getStyle().setMargin(5,5,5,5);
        pnlCenter.addComponent(lb4);
        
        Container cScDiagnostic4 = new Container(new BorderLayout());
        cScDiagnostic4.getStyle().setMargin(5, 5, 5, 5);
        cScDiagnostic4.addComponent(BorderLayout.CENTER,txtScDiagnostic4Name);
        cScDiagnostic4.addComponent(BorderLayout.EAST,btnSearchDiagn4);
        btnSearchDiagn4.setSize(new Dimension(2,2));
        cScDiagnostic4.setScrollableY(false);
        pnlCenter.addComponent(cScDiagnostic4);

        Label lbpn = new Label("Alergias");
        lbpn.getStyle().setMargin(5,5,5,5);
        pnlCenter.addComponent(lbpn);
        
        Container cPatientNotes = new Container(new BorderLayout());
        cPatientNotes.getStyle().setMargin(5, 5, 5, 5);
        cPatientNotes.addComponent(BorderLayout.CENTER,txtPatientNotes);
        cPatientNotes.addComponent(BorderLayout.EAST,btnEditPatientNotes);
        btnEditPatientNotes.setSize(new Dimension(2,2));
        cPatientNotes.setScrollableY(false);
        pnlCenter.addComponent(cPatientNotes);
        
        pnlCenter.setScrollableY(true);
        
        btnSave.setEnabled(true);
        btnSettings.setEnabled(true);

        Container southContainer = new Container(new BorderLayout());
        Container southButtons = new Container(new GridLayout(1,4));
        southButtons.addComponent(btnSave);
        southContainer.addComponent(BorderLayout.CENTER,southButtons);
        southContainer.addComponent(BorderLayout.EAST,btnSettings);
        
        setLayout(new BorderLayout());
        addComponent(BorderLayout.CENTER, pnlCenter);
        addComponent(BorderLayout.SOUTH, southContainer);  
    }    

    private void postInit(){    
        try{
            searchSCDiagnostics();
        }    
        catch (Exception ex) {
            Dialog.show("Alert",ex.getMessage(),"OK",null);
        }
    }    
    
    private TextArea createTextAreaCause() {
        TextArea txt = new TextArea();
        txt.getAllStyles().setBorder(Border.createLineBorder(1, 0));
        txt.getAllStyles().setFont(
            Font.createSystemFont(
                Font.FACE_PROPORTIONAL,
                Font.STYLE_PLAIN,
                Font.SIZE_MEDIUM
            )
        );
        txt.setGrowByContent(true);
        txt.getAllStyles().setPadding(1, 1, 1, 1);
        return txt;
    }
    
    private TextArea createTextAreaScDiagnostic1Name() {
        TextArea txt = new TextArea();
        txt.getAllStyles().setBorder(Border.createLineBorder(1, 0));
        txt.getAllStyles().setFont(
            Font.createSystemFont(
                Font.FACE_PROPORTIONAL,
                Font.STYLE_PLAIN,
                Font.SIZE_MEDIUM
            )
        );
        txt.setGrowByContent(true);
        txt.getAllStyles().setPadding(1, 1, 1, 1);
        return txt;
    }
    
    private TextArea createTextAreaScDiagnostic2Name() {
        TextArea txt = new TextArea();
        txt.getAllStyles().setBorder(Border.createLineBorder(1, 0));
        txt.getAllStyles().setFont(
            Font.createSystemFont(
                Font.FACE_PROPORTIONAL,
                Font.STYLE_PLAIN,
                Font.SIZE_MEDIUM
            )
        );
        txt.setGrowByContent(true);
        txt.getAllStyles().setPadding(1, 1, 1, 1);
        return txt;
    }

    private TextArea createTextAreaScDiagnostic3Name() {
        TextArea txt = new TextArea();
        txt.getAllStyles().setBorder(Border.createLineBorder(1, 0));
        txt.getAllStyles().setFont(
            Font.createSystemFont(
                Font.FACE_PROPORTIONAL,
                Font.STYLE_PLAIN,
                Font.SIZE_MEDIUM
            )
        );
        txt.setGrowByContent(true);
        txt.getAllStyles().setPadding(1, 1, 1, 1);
        return txt;
    }

    private TextArea createTextAreaScDiagnostic4Name() {
        TextArea txt = new TextArea();
        txt.getAllStyles().setBorder(Border.createLineBorder(1, 0));
        txt.getAllStyles().setFont(
            Font.createSystemFont(
                Font.FACE_PROPORTIONAL,
                Font.STYLE_PLAIN,
                Font.SIZE_MEDIUM
            )
        );
        txt.setGrowByContent(true);
        txt.getAllStyles().setPadding(1, 1, 1, 1);
        return txt;
    }
    
    private TextArea createTextAreaPatientNotes() {
        TextArea txt = new TextArea();
        txt.getAllStyles().setBorder(Border.createLineBorder(1, 0));
        txt.getAllStyles().setFont(
            Font.createSystemFont(
                Font.FACE_PROPORTIONAL,
                Font.STYLE_PLAIN,
                Font.SIZE_MEDIUM
            )
        );
        txt.getAllStyles().setBgColor(0xcccccc,true);
        txt.setGrowByContent(true);
        txt.setEditable(false);
        txt.getAllStyles().setPadding(1, 1, 1, 1);
        return txt;
    }
    
    private Button createBtnSave() {
        Image addImg = Brain.getInstance().getImage(Brain.IMAGE_SEND_ENABLED);            
        Button btn = new IwButton(); 
        btn.setIcon(addImg);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                saveSCDiagnostics();
            }
        });
        return btn;
    }

    private Button createSearchDiagn1() {
        
        Image searchImg = Brain.getInstance().getImage(Brain.IMAGE_SEARCH_ENABLED);
        
        Button btn = new IwButton();
        btn.setIcon(searchImg);
        btn.setVisible(true); // Initial state
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                openDialogScc(1, txtScDiagnostic1Name);
            }
        });
        return btn;
    }

    private Button createSearchDiagn2() {
        
        Image searchImg = Brain.getInstance().getImage(Brain.IMAGE_SEARCH_ENABLED);
        
        Button btn = new IwButton();
        btn.setIcon(searchImg);
        btn.setVisible(true); // Initial state
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                openDialogScc(2, txtScDiagnostic2Name);
            }
        });
        return btn;
    }
    
    private Button createSearchDiagn3() {
        
        Image searchImg = Brain.getInstance().getImage(Brain.IMAGE_SEARCH_ENABLED);
        
        Button btn = new IwButton();
        btn.setIcon(searchImg);
        btn.setVisible(true); // Initial state
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                openDialogScc(3, txtScDiagnostic3Name);
            }
        });
        return btn;
    }

    private Button createSearchDiagn4() {
        
        Image searchImg = Brain.getInstance().getImage(Brain.IMAGE_SEARCH_ENABLED);
        
        Button btn = new IwButton();
        btn.setIcon(searchImg);
        btn.setVisible(true); // Initial state
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                openDialogScc(4, txtScDiagnostic4Name);
            }
        });
        return btn;
    }
    
    private Button createBtnEditPatientNotes() {
        
        Image searchImg = Brain.getInstance().getImage(Brain.IMAGE_EDIT_ENABLED);
        
        Button btn = new IwButton();
        btn.setIcon(searchImg);
        btn.setVisible(true); // Initial state
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                editPatientNotes();
            }
        });
        return btn;
    }
    
    public int getUserAction() {
        return this.userAction;
    }
    
    private void searchSCDiagnostics(){

        MobRow rFilter = new MobRow();
        MobField fID = new MobField("ID",MobField.LONG,xCTx.getCurrentAdmissionId());
        rFilter.addField(fID);
        MobField fFull = new MobField("FULL",MobField.INTEGER,1);
        rFilter.addField(fFull);
        MobRecordset rsFilter = new MobRecordset();
        rsFilter.addRow(rFilter);
        
        Map<String,Object> pMap = new HashMap<String,Object>();
        pMap.put("rsFilter", rsFilter);
        pMap.put("rsInput", new MobRecordset());

        IwHttpRequesterCallBack<Map<String,MobRecordset>> callback =
                new IwHttpRequesterCallBack<Map<String,MobRecordset>>() {

            @Override
            public void onFailure(MobRecordsetError rsError) {
                Dialog.show("Alert", rsError.getTranslation(), "OK", null);
                my_resultMap = null;
            }
            @Override
            public void onSuccess(Map<String,MobRecordset> resultMap) {
                
                my_resultMap = resultMap;
                if (my_resultMap!=null){

                    MobRecordset rsResult = my_resultMap.get("rsResult");
                    
                    if (rsResult.rows.size()>0){
                            
                        MobRow r = rsResult.rows.get(0);

                        String cause = r.field("CAUSE").getValue();
                        Long ScDiagnostic1 = (Long)r.field("SCDIAGNOSTIC1").objValue();
                        String ScDiagnostic1Name = r.field("SCDIAGNOSTIC1NAME").getValue();
                        Long ScDiagnostic2 = (Long)r.field("SCDIAGNOSTIC2").objValue();
                        String ScDiagnostic2Name = r.field("SCDIAGNOSTIC2NAME").getValue();
                        Long ScDiagnostic3 = (Long)r.field("SCDIAGNOSTIC3").objValue();
                        String ScDiagnostic3Name = r.field("SCDIAGNOSTIC3NAME").getValue();
                        Long ScDiagnostic4 = (Long)r.field("SCDIAGNOSTIC4").objValue();
                        String ScDiagnostic4Name = r.field("SCDIAGNOSTIC4NAME").getValue();
                        String patientNotes = r.field("PATIENTNOTES").getValue();
                        
                        if (cause!=null) txtCause.setText(cause);
                        else txtCause.setText("");

                        if (ScDiagnostic1!=null) {
                            txtScDiagnostic1Name.setText(ScDiagnostic1Name);
                            SccDiagnostic1 = ScDiagnostic1;
                        }
                        else txtScDiagnostic1Name.setText("");

                        if (ScDiagnostic2!=null) {
                            txtScDiagnostic2Name.setText(ScDiagnostic2Name);
                            SccDiagnostic2 = ScDiagnostic2;
                        }
                        else txtScDiagnostic2Name.setText("");

                        if (ScDiagnostic3!=null) {
                            txtScDiagnostic3Name.setText(ScDiagnostic3Name.toString());
                            SccDiagnostic3 = ScDiagnostic3;
                        }
                        else txtScDiagnostic3Name.setText("");

                        if (ScDiagnostic4!=null) {
                            txtScDiagnostic4Name.setText(ScDiagnostic4Name);
                            SccDiagnostic4 = ScDiagnostic4;
                        }
                        else txtScDiagnostic4Name.setText("");

                        if (patientNotes!=null) txtPatientNotes.setText(patientNotes);
                        else txtPatientNotes.setText("");
                        
                        getContentPane().forceRevalidate();
                    }  
                }
                else{
                    try{
                        Toast.makeText(
                                Brain.getInstance().getContext(),
                                TT_MSG_NO_RECORDS_FOUND,
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    catch (Exception ex) {
                        //Line Bellow will show message in debug: Toast is not supported in this platform
                        //Dialog.show("Alert",ex.getMessage(),"OK",null);
                    }
                }
            }
        };

        if (xCTx.isOnlineMode()) {
            try{
                Toast.makeText(
                    Brain.getInstance().getContext(),
                    "Buscando Dados Servidor ...",
                    Toast.LENGTH_LONG).show();
            }
           catch (Exception e) {/** do nothing **/}
        }
        else {
            try{
                Toast.makeText(
                    Brain.getInstance().getContext(),
                    "Dados buscados do Cache ...",
                    Toast.LENGTH_LONG).show();
            }
           catch (Exception e) {/** do nothing **/}

        }

        xCTx.execIwService( "BOGetCapExec","MtsGetExeCareProgram","GetAdmission", pMap, callback);
        
    }
    
    private void setScc(int index, Long scc){
        switch (index) {
            case 1: SccDiagnostic1 = scc;
            break;
            case 2: SccDiagnostic2 = scc;
            break;
            case 3: SccDiagnostic3 = scc;
            break;
            case 4: SccDiagnostic4 = scc;
            break;
        }
        
    }
    
    private HashMap hmSccSelected = null;

    private void openDialogScc(int index, TextArea txtName){
        String sccCodeText = txtName.getText();
        if (sccCodeText.length()==0) return;
        
        boolean byCode = false;
        long sccCode = 0;
        try{
            sccCode = Long.parseLong(sccCodeText);
            byCode = true;
        }
        catch(Exception ex){}
        
        try{
            if (byCode){
                //Numeric.. use as TAB
                MobRecordset rsFilter = new MobRecordset();
                MobField fID = new MobField("ID",MobField.LONG,sccCode);
                MobRow rFilter = new MobRow();
                rFilter.addField(fID);
                rsFilter.addRow(rFilter);
                Map<String,Object> pMap = new HashMap<String,Object>();
                pMap.put("rsFilter", rsFilter);

                Map<String,MobRecordset> resultMap = xCTx.execIwService( "BOGetSccExec","MtsGetExeStructureCode","GetCode", pMap);                    
                MobRecordset rsResult = resultMap.get("rsResult");
                if (rsResult!=null&&!rsResult.rows.isEmpty()){
                    String codeName = rsResult.rows.get(0).field("CODENAME").getValue();
                    txtName.setText(codeName);
                    setScc(index,(Long)rsResult.rows.get(0).field("ID").objValue());
                    hmSccSelected = new HashMap();
                    hmSccSelected.put("ID", new Long(sccCode));
                    hmSccSelected.put("SCCODENAME", codeName);
                }
                else{
                    Dialog.show("Erro","Código não encontrado","OK",null);
                    hmSccSelected = null;
                    setScc(index,null);
                    txtName.setText(null);
                }
            }
            else{
                //Codename.. use as F3
                MobRecordset rsResult =null;
                MobRecordset rsFilter = new MobRecordset();
                MobField fTypeName = new MobField("TYPENAME",MobField.STRING,"CID_10");
                MobField fCodeName = new MobField("CODENAME",MobField.STRING,sccCodeText);
                MobRow rFilter = new MobRow();
                rFilter.addField(fTypeName);
                rFilter.addField(fCodeName);
                rsFilter.addRow(rFilter);
                Map<String,Object> pMap = new HashMap<String,Object>();
                pMap.put("rsFilter", rsFilter);
                Map<String,MobRecordset> resultMap = xCTx.execIwService( "BOGetSccExec","MtsGetExeStructureCode","GetCode", pMap);                    
                MobRecordset rsTemp = resultMap.get("rsResult");
                if (rsTemp!=null&&!rsTemp.rows.isEmpty()){
                    rsResult = new MobRecordset();
                    for (int i=0;i<rsTemp.rows.size();i++){
                        rsResult.addRow(rsTemp.rows.get(i));
                    }
                    if (rsResult.rows.size()>1){
                        
                        final Form previousForm = Display.getInstance().getCurrent();
                        
                        IwDialogChooseSccCode formChooseScc = new IwDialogChooseSccCode(xCTx, TT_SELECT_SCCODE);
                        formChooseScc.setSourceRecordset(rsResult);
                        formChooseScc.show();
                        
                        String backTitle = getIwTranslation(TT_BACK);
                        Command backCommand = new Command(backTitle) {
                            @Override
                            public void actionPerformed(ActionEvent ev) {
                                int userAction = formChooseScc.getUserAction();
                                if (userAction != formChooseScc.USER_ACTION_DONOTHING) {
                                    hmSccSelected = formChooseScc.getSelectedItem();
                                    if (hmSccSelected!=null){
                                        setScc(index,(Long)hmSccSelected.get("ID"));
                                        txtName.setText((String)hmSccSelected.get("SCCODENAME")); 
                                    }
                                    else{
                                        hmSccSelected = null;
                                        setScc(index,null);
                                        txtName.setText(null);
                                    }
                                }
                                else{
                                    hmSccSelected = null;
                                    setScc(index,null);
                                    txtName.setText(null);
                                }
                                previousForm.showBack();
                            } 
                        };
                        formChooseScc.setBackCommand(backCommand);
                        
                    }
                    else if (rsResult.rows.size()==1){
                        setScc(index,(Long)rsResult.rows.get(0).field("ID").objValue());
                        txtName.setText(rsResult.rows.get(0).field("CODENAME").getValue());
                        hmSccSelected = new HashMap();
                        hmSccSelected.put("ID", (Long)rsResult.rows.get(0).field("ID").objValue());
                        hmSccSelected.put("SCCODENAME", rsResult.rows.get(0).field("CODENAME").getValue());
                    }
                    else{
                        Dialog.show("Erro","Código não encontrado","OK",null);
                        hmSccSelected = null;
                        setScc(index,null);
                        txtName.setText(null);
                    }
                }
                else{
                    Dialog.show("Erro","Código não encontrado","OK",null);
                    hmSccSelected = null;
                    setScc(index,null);
                    txtName.setText(null);
                }
            }
        }
        catch(Exception ex){
            Dialog.show("Erro","Código não encontrado","OK",null);
            hmSccSelected = null;
            setScc(index,null);
            txtName.setText(null);
        }
    }
    
    private void editPatientNotes(){}
    
    private void saveSCDiagnostics(){
        try{
            MobRecordset rsInput = new MobRecordset();
            MobRow r = new MobRow();

            MobField fID = new MobField("ID",MobField.LONG,xCTx.getCurrentAdmissionId());
            r.addField(fID);

            String cause = null;
            if (txtCause.getText().length()>0) cause = txtCause.getText();

            MobField fCause = new MobField("CAUSE",MobField.STRING,cause);
            r.addField(fCause);

            Long ScDiagnostic1 = null;
            if (txtScDiagnostic1Name.getText().length()>0) ScDiagnostic1 = SccDiagnostic1;

            MobField fScDiagnostic1 = new MobField("SCDIAGNOSTIC1",MobField.LONG,ScDiagnostic1);
            r.addField(fScDiagnostic1);

            Long ScDiagnostic2 = null;
            if (txtScDiagnostic2Name.getText().length()>0) ScDiagnostic2 = SccDiagnostic2;

            MobField fScDiagnostic2 = new MobField("SCDIAGNOSTIC2",MobField.LONG,ScDiagnostic2);
            r.addField(fScDiagnostic2);

            Long ScDiagnostic3 = null;
            if (txtScDiagnostic3Name.getText().length()>0) ScDiagnostic3 = SccDiagnostic3;

            MobField fScDiagnostic3 = new MobField("SCDIAGNOSTIC3",MobField.LONG,ScDiagnostic3);
            r.addField(fScDiagnostic3);

            Long ScDiagnostic4 = null;
            if (txtScDiagnostic4Name.getText().length()>0) ScDiagnostic4 = SccDiagnostic4;

            MobField fScDiagnostic4 = new MobField("SCDIAGNOSTIC4",MobField.LONG,ScDiagnostic4);
            r.addField(fScDiagnostic4);

            String setnull = "";
            String separator = "|";
            if (cause==null){
                setnull = "|1|";
                separator = "";
            }
            if (ScDiagnostic1==null){
                setnull += separator+"2|";
            }
            if (ScDiagnostic2==null){
                setnull += separator+"3|";
            }
            if (ScDiagnostic3==null){
                setnull += separator+"4|";
            }
            if (ScDiagnostic4==null){
                setnull += separator+"5|";
            }

            if (setnull.length()>0){
                MobField fSetNull = new MobField("_SETNULL",MobField.STRING,setnull);
                r.addField(fSetNull);
            }

            MobField fNewRow = new MobField("_NEWROW",MobField.INTEGER,0);
            r.addField(fNewRow);

            MobField fKeyName = new MobField("_KEYNAME",MobField.STRING,"ID");
            r.addField(fKeyName);

            rsInput.addRow(r);
            Map<String,Object> pMap = new HashMap<String,Object>();
            pMap.put("rsInput", rsInput);
            pMap.put("TableName", "CapAdmission");
            my_resultMap = xCTx.execIwService("BOSetDpcExec","MtsSetExeDpcTable","UpdRecord", pMap);
            Dialog.show("Mensagem", TT_EXECUTION_OK, "OK", null);
        }
        catch(Exception ex){
            Dialog.show("Alert",ex.getMessage(),"OK",null);
        }
        
    }
    
    @Override
    public String getHtmlContent() {
        return performTagsSCDiagnostics();
    }

    private String performTagsSCDiagnostics(){
        try{
            
            //Colocar tags no texto abaixo 
            String cause = txtCause.getText();
            String scdiagnostic1name = txtScDiagnostic1Name.getText();
            String scdiagnostic2name = txtScDiagnostic2Name.getText();
            String scdiagnostic3name = txtScDiagnostic3Name.getText();
            String scdiagnostic4name = txtScDiagnostic4Name.getText();
            String patientNotes = txtPatientNotes.getText();

            String htmlSCDiagnostic1 = "";
            String htmlSCDiagnostic2 = "";
            String htmlSCDiagnostic3 = "";
            String htmlSCDiagnostic4 = "";
            String htmlPatientNotes = "";
            
            String htmlCause = "<table  cellpadding=\"0\" cellspacing=\"3\" bgcolor=\"#E4E4E4\" border=\"0\" width=\"100%\">  "+
                "<tr bgcolor=\"#F5F5F5\">"+
                "<td width=\"200\" valign=\"top\"><div align=\"right\"><strong>Motivo da Internação:&#160;</strong></div></td>"+
                "<td width=\"600\" valign=\"top\">"+cause+"</td></tr>";

            if (txtScDiagnostic1Name.getText().length()>0){
                htmlSCDiagnostic1 = "<tr bgcolor=\"#F5F5F5\">"+
                    "<td valign=\"top\"><div align=\"right\"><strong>Diagnóstico da internação:&#160;</strong></div></td>"+
                    "<td valign=\"top\">"+scdiagnostic1name+"</td></tr>";
            }
                    
            if (txtScDiagnostic2Name.getText().length()>0){
                htmlSCDiagnostic2 = "<tr bgcolor=\"#F5F5F5\">"+
                    "<td valign=\"top\"><div align=\"right\"><strong>Diagnóstico de base (1):&#160;</strong></div></td>"+
                    "<td valign=\"top\">"+scdiagnostic2name+"</td></tr>";
            }
            
            if (txtScDiagnostic3Name.getText().length()>0){
                htmlSCDiagnostic3 = "<tr bgcolor=\"#F5F5F5\">"+
                    "<td valign=\"top\"><div align=\"right\"><strong>Diagnóstico de base (2):&#160;</strong></div></td>"+
                    "<td valign=\"top\">"+scdiagnostic3name+"</td></tr>";
            }
            
            if (txtScDiagnostic4Name.getText().length()>0){
                htmlSCDiagnostic4 = "<tr bgcolor=\"#F5F5F5\">"+
                    "<td valign=\"top\"><div align=\"right\"><strong>Diagnóstico de base (3):&#160;</strong></div></td>"+
                    "<td valign=\"top\">"+scdiagnostic4name+"</td></tr>";
            }

            if (txtPatientNotes.getText().length()>0){
                htmlPatientNotes = "<tr bgcolor=\"#F5F5F5\">"+
                    "<td valign=\"top\"><div align=\"right\"><font color=\"#FF0000\"><strong>Alergias:&#160;</strong></font></div></td>"+
                    "<td valign=\"top\">"+patientNotes+"</td></tr>";
            }
            
            String htmlClose = "</table>";
            
            String htmlResult = htmlCause+htmlSCDiagnostic1+htmlSCDiagnostic2+htmlSCDiagnostic3+
                                htmlSCDiagnostic4+htmlPatientNotes+htmlClose;                
                
            return htmlResult;
        }
        catch(Exception ex){
            return "Error performing tags scdiagnostics";
        }
    }
    
}

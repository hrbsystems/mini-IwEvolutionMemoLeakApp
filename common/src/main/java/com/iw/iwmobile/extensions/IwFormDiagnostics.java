/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.extensions;

import com.codename1.components.ScaleImageLabel;
import com.codename1.gif.GifImage;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.iw.iwmobile.Brain;
import com.iw.iwmobile.comm.*;
import com.iw.iwmobile.components.IwButton;
import com.iw.iwmobile.components.IwHtmlTagInterface;
import com.iw.iwmobile.forms.IwFormBase;
//import com.pmovil.toast.Toast;
import com.iw.iwmobile._fakelibs.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 *
 * @author hrugani
 * This form is instantiated by IwWebBrowser1 Component
 * It is used to edit Diagnostics for specific patient
 * Used inside IW Templates. 
 * The goal is offer the same behavior present 
 * in Desktop Java Client Application
 */
public class IwFormDiagnostics extends IwFormBase implements IwHtmlTagInterface {
    
    IwExtContextInterface xCTx;
    private Button btReSeq;
    private Button btAdd;
    private Button btEdit;
    private Button btDelete;
    private Container pnlCenter;
    private Map<String,MobRecordset> my_resultMap;
    private Container diagnosticList;
    private LinkedHashMap hmDiagnosticList = new LinkedHashMap();
    private HashMap hmDiagnosticItem = new HashMap();
    private MobRecordset rsDiagnostic = null;
    private Long IDPatient;
    final static public String TT_MSG_NO_RECORDS_FOUND = "Nenhum registro retornado";
    final static public String TT_EXECUTION_OK = "Operação concluída";
    final static public String TT_SELECT_ONEROW = "Selecione uma linha";
    final static public String TT_EDIT = "Editar";
    final static public String TT_EDITING = "Edição"; 
    final static public String TT_NEW = "Novo";
    final static public String TT_RESEQ = "Mudar Sequência";
    
    
    public IwFormDiagnostics(IwExtContextInterface xCtx, String title) {   
        setTitle(title);
        this.xCTx = xCtx;
        Brain.getInstance().setFormMenu(IwFormDiagnostics.this,Brain.getInstance().MENU_STANDARD);        

        setScrollableY(false);
        preInit();
        init();
        postInit();
    }

    @Override
    public String getHtmlContent() {
        return performTagsDiagnostics();
    }


    private void preInit(){
        btAdd = create_btAdd();
        btEdit = create_btEdit();
        btDelete = create_btDelete();
        btReSeq = create_btReSeq();
    }
    
    private void init(){
        
        pnlCenter = new Container(BoxLayout.y());
        pnlCenter.setScrollableY(true);
        pnlCenter.setScrollableX(true);
        
        Container southContainer = new Container(new BorderLayout());
        Container southButtons = new Container(new GridLayout(1,4));
        southButtons.addComponent(btAdd);
        southButtons.addComponent(btEdit);
        southButtons.addComponent(btDelete);
        southButtons.addComponent(btReSeq);
        
        southContainer.addComponent(BorderLayout.CENTER,southButtons);
        
        setLayout(new BorderLayout());
        addComponent(BorderLayout.CENTER, pnlCenter);
        addComponent(BorderLayout.SOUTH, southContainer);  
        
    }
    
    private void postInit(){
        try{
            IDPatient = getIDPatient(xCTx.getCurrentAdmissionId());
            searchDiagnostics();
        }    
        catch (Exception ex) {
            Dialog.show("Alert",ex.getMessage(),"OK",null);
        }
    }
    
    private Button create_btReSeq() {
        
        Image searchImg = Brain.getInstance().getImage(Brain.IMAGE_ARROW_REORDER_123);
        
        Button btn = new IwButton();
        btn.setIcon(searchImg);
        btn.setVisible(true); // Initial state
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                reSeqDiagnostics();
            }
        });
        return btn;
    } 
    
    private Button create_btAdd() {
        
        Image addImg =
            Brain.getInstance().getImage(Brain.IMAGE_ADD_ENABLED);
        
        Button btn = new IwButton(); 
        btn.setIcon(addImg);
        btn.setVisible(true); 
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                addDiagnostic();
            }
        });
        return btn;
    }
    
    private Button create_btEdit() {
        Image editImg = Brain.getInstance().getImage(Brain.IMAGE_EDIT_ENABLED);
        Button btn = new IwButton(); 
        btn.setIcon(editImg);
        btn.setVisible(true); 
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                editDiagnostic(false);
            }
        });
        return btn;
    }

    private Button create_btDelete() {
        
        Image addImg =
            Brain.getInstance().getImage(Brain.IMAGE_REMOVE_ENABLED);
        
        Button btn = new IwButton(); 
        btn.setIcon(addImg);
        btn.setVisible(true); 
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                delDiagnostic();
            }
        });
        return btn;
    }
    
    private Long getIDPatient(long IDAdmission) throws Exception{
        MobRow rFilter = new MobRow();
        MobField fID = new MobField("ID",MobField.LONG,IDAdmission);
        rFilter.addField(fID);
        MobRecordset rsFilter = new MobRecordset();
        rsFilter.addRow(rFilter);

        MobRow rInput = new MobRow();
        MobField fName = new MobField("NAME",MobField.STRING,"IDPATIENT");
        rInput.addField(fName);
        MobRecordset rsInput = new MobRecordset();
        rsInput.addRow(rInput);
        
        Map<String,Object> pMap = new HashMap<String,Object>();
        pMap.put("TableName", "CapAdmission");
        pMap.put("rsFilter", rsFilter);
        pMap.put("rsInput", rsInput);
        pMap.put("OrderBy", "ID");
        pMap.put("Security", 0);
        Map<String,MobRecordset> resp =
            this.xCTx.execIwService(
                  "BOGetDpcExec",
                  "MtsGetExeDpcTable",
                  "GetRecord",
                  pMap 
            );

        MobRecordset rs = resp.get("rsResult");
        return (Long)rs.rows.get(0).field("IDPATIENT").objValue();
    }
            
    private void searchDiagnostics(){
        pnlCenter.removeAll();
        pnlCenter.repaint();

        MobRow rFilter = new MobRow();
        MobField fWhere = new MobField("IDPATIENT",MobField.LONG,IDPatient);
        rFilter.addField(fWhere);
        MobRecordset rsFilter = new MobRecordset();
        rsFilter.addRow(rFilter);
        
        MobRecordset rsInput = new MobRecordset();
        MobField fNAME1 = new MobField("NAME",MobField.STRING,"A.*");
        MobField fNAME2 = new MobField("NAME",MobField.STRING,"SC.CODENAME SCDIAGNOSTICNAME");
        MobField fNAME3 = new MobField("NAME",MobField.STRING,"SC.ALTERNATENAME");
        MobRow rInput1 = new MobRow();
        rInput1.addField(fNAME1);
        MobRow rInput2 = new MobRow();
        rInput2.addField(fNAME2);
        MobRow rInput3 = new MobRow();
        rInput3.addField(fNAME3);
        rsInput.addRow(rInput1);
        rsInput.addRow(rInput2);
        rsInput.addRow(rInput3);
        
        Map<String,Object> pMap = new HashMap<String,Object>();
        pMap.put("rsFilter", rsFilter);
        pMap.put("rsInput", rsInput);
        pMap.put("rsSccCode", new MobRecordset());
        pMap.put("rsSccColumn", new MobRecordset());
        pMap.put("SccColumn", "ScDiagnostic");
        pMap.put("TableName", "CapDiagnostic");
        pMap.put("SCAlias", "ScDiagnosticName");
        pMap.put("OrderBy", "Seq");
        pMap.put("Security", 0);

        IwHttpRequesterCallBack<Map<String,MobRecordset>> callback =
                new IwHttpRequesterCallBack<Map<String,MobRecordset>>() {

            @Override
            public void onFailure(MobRecordsetError rsError) {
                Dialog.show("Alert", rsError.getTranslation(), "OK", null);
                my_resultMap = null;
            }
            @Override
            public void onSuccess(Map<String,MobRecordset> resultMap) {
                
                pnlCenter.removeAll();
                
                my_resultMap = resultMap;
                if (my_resultMap!=null){
                    pnlCenter.removeAll();
                    pnlCenter.repaint();

                    rsDiagnostic = my_resultMap.get("rsResult");
                    
                    if (rsDiagnostic.rows.size()>0){
                        
                        diagnosticList = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                        hmDiagnosticList = new LinkedHashMap();
                        int i = -1;
                        for(MobRow r : rsDiagnostic.rows) {
                            Long ID = (Long)r.field("ID").objValue();
                            String ScDiagnostic = r.field("SCDIAGNOSTIC").getValue();
                            String ScDiagnosticName = r.field("SCDIAGNOSTICNAME").getValue();
                            String AlternateName = r.field("ALTERNATENAME").getValue();
                            Integer Seq = (Integer)r.field("SEQ").objValue();
                            Object sinceDate = r.field("SINCEDATE").objValue();
                            String strSinceDate = "";
                            if (sinceDate!=null){
                                strSinceDate = Brain.getInstance().fmtDateTime((Date)sinceDate);
                                strSinceDate = strSinceDate.substring(0,strSinceDate.indexOf(" "));
                            }
                            Object untilDate = r.field("UNTILDATE").objValue();
                            String strUntilDate = "";
                            if (untilDate!=null){
                                strUntilDate = Brain.getInstance().fmtDateTime((Date)untilDate);
                                strUntilDate = strUntilDate.substring(0,strUntilDate.indexOf(" "));
                            }
                            
                            Container cItem = new Container(new GridLayout(5,1));
                            
                            CheckBox line1 = new CheckBox(" "+Seq.toString());
                            line1.setOppositeSide(false);
                            
                            line1.getStyle().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL,
                                    Font.STYLE_BOLD,
                                    Font.SIZE_LARGE));
                            
                            //line1.getStyle().setFgColor(0xff0000);
                            line1.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent evt) {
                                    Container cCurrent = line1.getParent();
                                    Label lbCurrent = (Label)cCurrent.getComponentAt(4);
                                    for(int i =0;i<diagnosticList.getComponentCount();i++) {
                                        Container c = (Container)diagnosticList.getComponentAt(i);
                                        CheckBox cx = (CheckBox)c.getComponentAt(0);
                                        Label lb = (Label)c.getComponentAt(4);
                                        if (cx.isSelected()&&!lbCurrent.getText().equals(lb.getText())){
                                            cx.setSelected(false);
                                        }
                                    }
                                }
                            });
                            
                            if (AlternateName==null) AlternateName = "";
                            
                            Label line2 = new Label(ScDiagnosticName+"["+ScDiagnostic+"]"+"["+AlternateName+"]");
                            
                            String strline3 = "Data Início: "+strSinceDate;
                            Label line3 = new Label(strline3);

                            String strline4 = "Data Resolução: "+strUntilDate;
                            Label line4 = new Label(strline4);
                            
                            Label line5 = new Label(ID.toString());
                            line5.getStyle().setFgColor(line5.getStyle().getBgColor());
                            
                            cItem.addComponent(0,line1);
                            cItem.addComponent(1,line2);
                            cItem.addComponent(2,line3);
                            cItem.addComponent(3,line4);
                            cItem.addComponent(4,line5);
                            
                            cItem.getStyle().setBorder(Border.createLineBorder(1));
                            diagnosticList.add(cItem);
                            i++;
                            hmDiagnosticItem = new HashMap();
                            hmDiagnosticItem.put("ID",ID);
                            hmDiagnosticItem.put("SEQ",Seq);
                            hmDiagnosticItem.put("SCDIAGNOSTIC",ScDiagnostic);
                            hmDiagnosticItem.put("SCDIAGNOSTICNAME",ScDiagnosticName);
                            hmDiagnosticItem.put("ALTERNATENAME",AlternateName);
                            hmDiagnosticItem.put("SINCEDATE",(Date)sinceDate);
                            hmDiagnosticItem.put("UNTILDATE",(Date)untilDate);
                            hmDiagnosticList.put(ID.longValue(),hmDiagnosticItem);
                            
                        }  
                        diagnosticList.setScrollableX(true);
                        pnlCenter.add(diagnosticList);
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
                getContentPane().forceRevalidate();
            }
        };

        showProcessingAnimatedImage();
        
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

        xCTx.execIwService( "BOGetDpcExec","MtsGetExeDpcTable","GetRecordScc", pMap, callback);
        
    }

    private void showProcessingAnimatedImage() {
        try {
            pnlCenter.removeAll();
            
            InputStream imgIs = Display.getInstance().getResourceAsStream(Form.class, "/Running.gif");
            
            pnlCenter.addComponent(
                BoxLayout.encloseY(
                    new Label(" "),
                    new ScaleImageLabel(
                        GifImage.decode(
                            imgIs,
                            1177720
                        )
                    )
                )
            );
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        this.repaint();
        
    } 

    private void addDiagnostic(){
        editDiagnostic(true);
    }

    private void editDiagnostic(boolean newItem){
        try{
            String dlgTitle = TT_EDITING;
            long IDSelected = -1;
            if (!newItem){
                boolean hasRowSelected = false;
                for (int i=0;i<diagnosticList.getComponentCount();i++){
                    Container cItem = (Container)diagnosticList.getComponentAt(i);
                    CheckBox chk = (CheckBox)cItem.getComponentAt(0);
                    if (chk.isSelected()){
                        hasRowSelected = true;
                        Label lb = (Label)cItem.getComponentAt(4);
                        IDSelected = Long.parseLong(lb.getText());
                        break;
                    }
                }       
                if (!hasRowSelected){
                    Dialog.show("Alerta",TT_SELECT_ONEROW,"OK",null);
                    return;
                }
            }
            else{
                dlgTitle = TT_NEW;
            }
            
            final Form previousForm = Display.getInstance().getCurrent();
            
            IwDialogEditDiagnostic formEditDiagnostic = new IwDialogEditDiagnostic(xCTx,dlgTitle);
            if (!newItem){
                HashMap hmItemForm = (HashMap)hmDiagnosticList.get(IDSelected);
                formEditDiagnostic.setDiagnostic(hmItemForm);
            }
            else{
                int nextSeq = hmDiagnosticList.size()+1;
                formEditDiagnostic.setNextSeq(nextSeq);
            }
            formEditDiagnostic.show();
            
            String backTitle = getIwTranslation(TT_BACK);
            Command backCommand = new Command(backTitle) {
                @Override
                public void actionPerformed(ActionEvent ev) {
                    int userAction = formEditDiagnostic.getUserAction();
                    if (userAction != formEditDiagnostic.USER_ACTION_DONOTHING) {

                        HashMap hmItemDialog = formEditDiagnostic.getDiagnostic();

                        MobRecordset rsInput = new MobRecordset();
                        MobRow r = new MobRow();
                        
                        MobField fID = new MobField("ID",MobField.LONG,hmItemDialog.get("ID"));
                        r.addField(fID);
                        
                        MobField fSeq = new MobField("SEQ",MobField.INTEGER,hmItemDialog.get("SEQ"));
                        r.addField(fSeq);

                        MobField fSinceDate = new MobField("SINCEDATE",MobField.DATE,(Date)hmItemDialog.get("SINCEDATE"));
                        r.addField(fSinceDate);
                        
                        MobField fUntilDate = new MobField("UNTILDATE",MobField.DATE,(Date)hmItemDialog.get("UNTILDATE"));
                        r.addField(fUntilDate);

                        if (hmItemDialog.containsKey("SCDIAGNOSTIC")){
                            MobField fScDiagnostic = new MobField("SCDIAGNOSTIC",MobField.LONG,(Long)hmItemDialog.get("SCDIAGNOSTIC"));
                            r.addField(fScDiagnostic);
                        }
                        if (hmItemDialog.containsKey("IDALTERNATE")){
                            MobField fIDAlternate = new MobField("IDALTERNATE",MobField.LONG,(Long)hmItemDialog.get("IDALTERNATE"));
                            r.addField(fIDAlternate);
                        }
                        MobField fIDPatient = new MobField("IDPATIENT",MobField.LONG,IDPatient);
                        r.addField(fIDPatient);

                        int newrow = 0;
                        if (newItem){
                            newrow = 1;
                        }
                        else{
                            String setnull = "";
                            String separator = "|";
                            if (fSinceDate.objValue()==null){
                                setnull = "|2|";
                                separator = "";
                            }
                            if (fUntilDate.objValue()==null){
                                setnull += separator+"3|";
                            }
                            
                            if (setnull.length()>0){
                                MobField fSetNull = new MobField("_SETNULL",MobField.STRING,setnull);
                                r.addField(fSetNull);
                            }
                        }                        
                        MobField fNewRow = new MobField("_NEWROW",MobField.INTEGER,newrow);
                        r.addField(fNewRow);

                        MobField fKeyName = new MobField("_KEYNAME",MobField.STRING,"ID");
                        r.addField(fKeyName);

                        rsInput.addRow(r);
                        try{
                            Map<String,Object> pMap = new HashMap<String,Object>();
                            pMap.put("rsInput", rsInput);
                            my_resultMap = xCTx.execIwService("BOSetCapExec","MtsSetExeCareProgram","UpdDiagnostic", pMap);
                            Dialog.show("Mensagem", TT_EXECUTION_OK, "OK", null);
                            searchDiagnostics();
                        }
                        catch(IwCommException cex){
                            Dialog.show("Error", cex.getMessage(), "OK", null);
                        }
                    }
                    previousForm.showBack();
                }
            };
            formEditDiagnostic.setBackCommand(backCommand);
        }
        catch(Exception ex){
            Dialog.show("Alert",ex.getMessage(),"OK",null);
        }
    }

    private void delDiagnostic(){
        try{
            boolean hasRowSelected = false;
            for (int i=0;i<diagnosticList.getComponentCount();i++){
                Container cItem = (Container)diagnosticList.getComponentAt(i);
                CheckBox chk = (CheckBox)cItem.getComponentAt(0);
                if (chk.isSelected()){
                    hasRowSelected = true;
                    break;
                }
            }       
            if (!hasRowSelected){
                Dialog.show("Alerta",TT_SELECT_ONEROW,"OK",null);
                return;
            }
            boolean userResp = Dialog.show("Confirmar","Deseja remover o item selecionado ?",
                    getIwTranslation(TT_YES),getIwTranslation(TT_NO));

            if (userResp) {
                
                MobRecordset rsInput = new MobRecordset();
                for (int i=0;i<diagnosticList.getComponentCount();i++){
                    Container cItem = (Container)diagnosticList.getComponentAt(i);
                    CheckBox chk = (CheckBox)cItem.getComponentAt(0);
                    if (chk.isSelected()){
                        Label lb = (Label)cItem.getComponentAt(4);
                        long IDSelected = Long.parseLong(lb.getText());
                        HashMap hmItem = (HashMap)hmDiagnosticList.get(IDSelected); 
                        
                        MobField fID = new MobField("ID",MobField.LONG,hmItem.get("ID"));
                        MobField fDelete = new MobField("DELETE",MobField.INTEGER,1);
                        MobField fNewRow = new MobField("_NEWROW",MobField.INTEGER,0);
                        MobField fKeyName = new MobField("_KEYNAME",MobField.STRING,"ID");

                        MobRow r = new MobRow();
                        r.addField(fID);
                        r.addField(fDelete);
                        r.addField(fNewRow);
                        r.addField(fKeyName);
                        rsInput.addRow(r);
                    }
                }       
                
                try{
                    Map<String,Object> pMap = new HashMap<String,Object>();
                    pMap.put("rsInput", rsInput);
                    my_resultMap = xCTx.execIwService("BOSetCapExec","MtsSetExeCareProgram","UpdDiagnostic", pMap);

                    Dialog.show("Mensagem", TT_EXECUTION_OK, "OK", null);
                    searchDiagnostics();
                }
                catch(IwCommException cex){
                    Dialog.show("Error", cex.getMessage(), "OK", null);
                }
                
            }
        }
        catch(Exception ex){
            Dialog.show("Alert",ex.getMessage(),"OK",null);
        }
        
    }
    
    private void reSeqDiagnostics(){
        try{
            
            final Form previousForm = Display.getInstance().getCurrent();
            
            IwDialogReSeqDiagnostic formReSeq = new IwDialogReSeqDiagnostic(xCTx, TT_RESEQ);
            formReSeq.setSourceRecordset(rsDiagnostic);  
            
            formReSeq.show();

            String backTitle = getIwTranslation(TT_BACK);
            Command backCommand = new Command(backTitle) {
                @Override
                public void actionPerformed(ActionEvent ev) {
                    int userAction = formReSeq.getUserAction();
                    if (userAction != formReSeq.USER_ACTION_DONOTHING) {
                        MobRecordset rsInput = formReSeq.getReSequenciedRecordset();

                        try{
                            Map<String,Object> pMap = new HashMap<String,Object>();
                            pMap.put("rsInput", rsInput);
                            pMap.put("TableName", "CapDiagnostic");
                            my_resultMap = xCTx.execIwService("BOSetDpcExec","MtsSetExeDpcTable","UpdRecord", pMap);
                            Dialog.show("Mensagem", TT_EXECUTION_OK, "OK", null);
                            searchDiagnostics();
                        }
                        catch(IwCommException cex){
                            Dialog.show("Error", cex.getMessage(), "OK", null);
                        }
                    }
                    previousForm.showBack();
                } 
            };
            formReSeq.setBackCommand(backCommand);
            
        }
        catch(Exception ex){
            Dialog.show("Alert",ex.getMessage(),"OK",null);
        }
        
    }

    private String performTagsDiagnostics(){
        try{
            
            //Colocar tags no texto abaixo e depois fazer um loop para replace nessas tags pelos valores da lista

            String htmlResult = 
                "<table  cellpadding=\"0\" cellspacing=\"3\" bgcolor=\"#E4E4E4\" border=\"0\" width=\"100%\">  "+
                "<thead>     <tr>         <td bgcolor=\"#9CE2B3\" align=\"left\"><b>N°</b></td>         "+
                "<td bgcolor=\"#9CE2B3\" align=\"left\"><b>Diagnóstico</b></td>         "+
                "<td bgcolor=\"#9CE2B3\" align=\"left\"><b>Data de Início</b></td>         "+
                "<td bgcolor=\"#9CE2B3\" align=\"left\"><b>Data de resolução</b></td>     </tr>     "+
                "<tbody>     ";
                
                
            //loop
            String tableValues = "";
            Set<Long> keys = hmDiagnosticList.keySet();
            for (Long key : keys){                
                
                HashMap hmItem = (HashMap)hmDiagnosticList.get(key.longValue());
                String seq = ((Integer)hmItem.get("SEQ")).toString();
                String scdiagnosticname = (String)hmItem.get("SCDIAGNOSTICNAME");
                if (scdiagnosticname==null) {
                    scdiagnosticname = "";
                }
                Date sinceDate = (Date)hmItem.get("SINCEDATE");
                String strSinceDate = "";
                if (sinceDate!=null){
                    strSinceDate = Brain.getInstance().fmtDateTime((Date)sinceDate);
                    strSinceDate = strSinceDate.substring(0,strSinceDate.indexOf(" "));
                }
                        
                Date untilDate = (Date)hmItem.get("UNTILDATE");
                String strUntilDate = "";
                if (untilDate!=null){
                    strUntilDate = Brain.getInstance().fmtDateTime((Date)untilDate);
                    strUntilDate = strUntilDate.substring(0,strUntilDate.indexOf(" "));
                }
                tableValues += "<tr bgcolor=\"#F5F5F5\"  iwcolor1=\"#F5F5F5\" iwcolor2=\"#F5F5F5\">          "+
                              "<td valign=\"top\"> "+seq+" </td>         "+
                              "<td valign=\"top\"> "+scdiagnosticname+"         </td>         "+
                              "<td valign=\"top\"> "+strSinceDate+"         </td>         "+
                              "<td valign=\"top\"> "+strUntilDate+"         </td>         "+
                              "<td valign=\"top\">  </td>   </tr>";                
                
            }
            
            return htmlResult+tableValues+"</table>";
        }
        catch(Exception ex){
            return "Error performing tags diagnostics";
        }
    }
    
    private MobRecordset OrderBySeq(MobRecordset rsInput){
        if (rsInput==null||rsInput.rows.isEmpty()) return rsInput;

        MobRecordset rsInputAux = new MobRecordset();
        for(MobRow r : rsInput.rows) {
            rsInputAux.addRow(r);
        }        
        
        MobRecordset rsOutput = new MobRecordset();

        int lastSeq = ((Integer)rsInputAux.rows.get(0).field("SEQ").objValue()).intValue();
        for(MobRow raux : rsInputAux.rows) {
            int seqaux = ((Integer)raux.field("SEQ").objValue()).intValue();
            if (seqaux<lastSeq){
                lastSeq = seqaux;
            }
        }        
        int minSeq = lastSeq;
        
        boolean mrsfull = false;
        while (!mrsfull){
            for(MobRow raux : rsInputAux.rows) {
                int seqAux = ((Integer)raux.field("SEQ").objValue()).intValue();
                if (minSeq==seqAux){
                    rsOutput.addRow(raux);
                }
            }   
            minSeq++;
            if (rsOutput.rows.size()==rsInput.rows.size()){
                mrsfull = true;
            }
        }
        return rsOutput;
    }
    
}
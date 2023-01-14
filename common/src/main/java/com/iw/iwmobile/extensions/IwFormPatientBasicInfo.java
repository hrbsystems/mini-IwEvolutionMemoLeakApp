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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.iw.iwmobile.extensions.IwFormDiagnostics.TT_EXECUTION_OK;

/**
 *
 * @author Gustavo
 */
public class IwFormPatientBasicInfo extends IwFormBase implements IwHtmlTagInterface{
    
    IwExtContextInterface xCTx;
    public final int USER_ACTION_DONOTHING = -1;
    private int userAction = USER_ACTION_DONOTHING; // Default Action
    private final Button btnSave;
    private final IwSettingsButton btnSettings;
    private final Button btnSearchZipCode;
    private final Button btnEditTelephone;
    
    private TextArea txtBirthday;
    private TextArea txtZipCode;
    private TextArea txtAddress;
    private TextArea txtComplement;
    private TextArea txtDistrict;
    private TextArea txtCity;
    private TextArea txtTelephone;
    private Map<String,MobRecordset> my_resultMap;
    private Container pnlCenter;
    private Long IDPatient;
    private Long IDPerson;
    private String PatientAge = "";
    final static public String TT_SELECT_SCCODE = "Selecionar CEP";
    final static public String TT_TELEPHONE = "Telefone";
    
    public IwFormPatientBasicInfo(IwExtContextInterface xCTx, String title) {    
        super();
        setTitle(title);
        this.xCTx = xCTx;

        btnSave = createBtnSave();
        btnSettings = new IwSettingsButton();
        btnSearchZipCode = createSearchZipCode();
        btnEditTelephone = createEditTelephone();
        init();
        postInit();
    }
    
    
    private void init(){    
        txtBirthday = createTextAreaBirthday();
        txtZipCode = createTextAreaZipCode();
        txtAddress = createTextAreaAddress();
        txtComplement = createTextAreaComplement();
        txtDistrict = createTextAreaDistrict();
        txtCity = createTextAreaCity();
        txtTelephone = createTextAreaTelephone();
        
        pnlCenter = new Container(BoxLayout.y());

        Label lbBirthday = new Label("Data Nascimento");
        lbBirthday.getStyle().setMargin(5, 5, 5, 5);
        pnlCenter.addComponent(lbBirthday);
        
        Container cBirthday = new Container(new BorderLayout());
        cBirthday.getStyle().setMargin(5, 5, 5, 5);
        cBirthday.addComponent(BorderLayout.CENTER,txtBirthday);
        cBirthday.setScrollableY(false);
        
        pnlCenter.addComponent(cBirthday);

        Label lbZipCode = new Label("CEP");
        lbZipCode.getStyle().setMargin(5,5,5,5);
        pnlCenter.addComponent(lbZipCode);
        
        Container cZipCode = new Container(new BorderLayout());
        cZipCode.getStyle().setMargin(5, 5, 5, 5);
        cZipCode.addComponent(BorderLayout.CENTER,txtZipCode);
        cZipCode.addComponent(BorderLayout.EAST,btnSearchZipCode);
        cZipCode.setSize(new Dimension(2,2));
        cZipCode.setScrollableY(false);
        pnlCenter.addComponent(cZipCode);
        
        Label lbAddress = new Label("Endereço");
        lbAddress.getStyle().setMargin(5,5,5,5);
        pnlCenter.addComponent(lbAddress);
        
        Container cAddress = new Container(new BorderLayout());
        cAddress.getStyle().setMargin(5, 5, 5, 5);
        cAddress.addComponent(BorderLayout.CENTER,txtAddress);
        cAddress.setScrollableY(false);
        pnlCenter.addComponent(cAddress);
        
        Label lbComplement = new Label("Nro / Complemento");
        lbComplement.getStyle().setMargin(5,5,5,5);
        pnlCenter.addComponent(lbComplement);
        
        Container cComplement = new Container(new BorderLayout());
        cComplement.getStyle().setMargin(5, 5, 5, 5);
        cComplement.addComponent(BorderLayout.CENTER,txtComplement);
        cComplement.setScrollableY(false);
        pnlCenter.addComponent(cComplement);
        
        Label lbDistrict = new Label("Bairro");
        lbDistrict.getStyle().setMargin(5,5,5,5);
        pnlCenter.addComponent(lbDistrict);
        
        Container cDistrict = new Container(new BorderLayout());
        cDistrict.getStyle().setMargin(5, 5, 5, 5);
        cDistrict.addComponent(BorderLayout.CENTER,txtDistrict);
        cDistrict.setScrollableY(false);
        pnlCenter.addComponent(cDistrict);
        
        Label lbCity = new Label("Cidade");
        lbCity.getStyle().setMargin(5,5,5,5);
        pnlCenter.addComponent(lbCity);
        
        Container cCity = new Container(new BorderLayout());
        cCity.getStyle().setMargin(5, 5, 5, 5);
        cCity.addComponent(BorderLayout.CENTER,txtCity);
        cCity.setScrollableY(false);
        pnlCenter.addComponent(cCity);
        
        Label lbTelephone = new Label("Telefones");
        lbTelephone.getStyle().setMargin(5,5,5,5);
        pnlCenter.addComponent(lbTelephone);
        
        Container cTelephone = new Container(new BorderLayout());
        cTelephone.getStyle().setMargin(5, 5, 5, 5);
        cTelephone.addComponent(BorderLayout.CENTER,txtTelephone);
        cTelephone.addComponent(BorderLayout.EAST,btnEditTelephone);
        btnEditTelephone.setSize(new Dimension(2,2));
        cTelephone.setScrollableY(false);
        pnlCenter.addComponent(cTelephone);
        
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
            IDPatient = getIDPatient(xCTx.getCurrentAdmissionId());
            searchPatientBasicInfo();
        }    
        catch (Exception ex) {
            Dialog.show("Alert",ex.getMessage(),"OK",null);
        }
    }    
    
    private TextArea createTextAreaBirthday() {
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
    
    private TextArea createTextAreaZipCode() {
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
    
    private TextArea createTextAreaAddress() {
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

    private TextArea createTextAreaComplement() {
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

    private TextArea createTextAreaDistrict() {
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

    private TextArea createTextAreaCity() {
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
    
    private TextArea createTextAreaTelephone() {
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
                savePatientBasicInfo();
            }
        });
        return btn;
    }

    private Button createSearchZipCode() {
        
        Image searchImg = Brain.getInstance().getImage(Brain.IMAGE_SEARCH_ENABLED);
        
        Button btn = new IwButton();
        btn.setIcon(searchImg);
        btn.setVisible(true); // Initial state
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                searchZipCode();
            }
        });
        return btn;
    }

    private Button createEditTelephone() {
        
        Image searchImg = Brain.getInstance().getImage(Brain.IMAGE_EDIT_ENABLED);
        
        Button btn = new IwButton();
        btn.setIcon(searchImg);
        btn.setVisible(true); // Initial state
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                editTelephone();
            }
        });
        return btn;
    }
    
    public int getUserAction() {
        return this.userAction;
    }
    
    private void searchPatientBasicInfo(){
        try{
            MobRow rFilter = new MobRow();
            MobField fID = new MobField("B.ID",MobField.LONG,IDPatient);
            rFilter.addField(fID);
            MobRecordset rsFilter = new MobRecordset();
            rsFilter.addRow(rFilter);

            MobRecordset rsInput = new MobRecordset();
            MobField fName = new MobField("NAME",MobField.STRING,"A.*");
            MobRow rInput = new MobRow();
            rInput.addField(fName);
            rsInput.addRow(rInput);

            Map<String,Object> pMap = new HashMap<String,Object>();
            pMap.put("rsInput", rsInput);
            pMap.put("rsFilter", rsFilter);
            pMap.put("TableName", "GlbPerson");
            pMap.put("JoinTableName", "GlbPatient");
            pMap.put("Join", "B.IDPerson = A.ID");
            pMap.put("OrderBy", "A.ID");
            pMap.put("Security", 0);

            Map<String,MobRecordset> resultMap = xCTx.execIwService( "BOGetDpcExec","MtsGetExeDpcTable","GetRecordJoin", pMap);
            MobRecordset rsResult = resultMap.get("rsResult");
            if (rsResult.rows.size()>0){

                MobRow r = rsResult.rows.get(0);

                IDPerson = (Long)r.field("ID").objValue();

                String Birthday = null;
                if (r.field("BIRTHDAY").objValue()!=null){
                    Birthday = Brain.getInstance().fmtDateTime((Date) r.field("BIRTHDAY").objValue());
                    Birthday = Birthday.substring(0,Birthday.indexOf(" "));
                }
                String ZipCode = r.field("ZIPCODE").getValue();
                String Address = r.field("ADDRESS").getValue();
                String Complement = r.field("COMPLEMENT").getValue();
                String District = r.field("DISTRICT").getValue();
                String City = r.field("CITY").getValue();
                String Telephone = r.field("TELEPHONE").getValue();

                if (Birthday!=null) txtBirthday.setText(Birthday);
                else txtBirthday.setText("");

                if (ZipCode!=null) txtZipCode.setText(ZipCode);
                else txtZipCode.setText("");

                if (Address!=null) txtAddress.setText(Address);
                else txtAddress.setText("");

                if (Complement!=null) txtComplement.setText(Complement);
                else txtComplement.setText("");

                if (District!=null) txtDistrict.setText(District);
                else txtDistrict.setText("");

                if (City!=null) txtCity.setText(City);
                else txtCity.setText("");

                if (Telephone!=null) txtTelephone.setText(Telephone);
                else txtTelephone.setText("");

                getContentPane().forceRevalidate();
            }  
            
        }
        catch(IwCommException ex){
            Dialog.show("Alert",ex.getRsError().getTranslation(),"OK",null);
        }
        catch(Exception ezx){
            Dialog.show("Erro", ezx.getMessage(), "OK", null);
        }

        
    }
    
    private void savePatientBasicInfo(){
        try{
            MobRecordset rsInput = new MobRecordset();
            MobRow r = new MobRow();

            MobField fID = new MobField("ID",MobField.LONG,IDPerson);
            r.addField(fID);

            String Birthday = null;
            if (txtBirthday.getText().length()>0) Birthday = txtBirthday.getText();
            MobField fBirthday = new MobField("Birthday",MobField.STRING,Birthday);
            r.addField(fBirthday);
            
            String ZipCode = null;
            if (txtZipCode.getText().length()>0) ZipCode = txtZipCode.getText();
            MobField fZipCode = new MobField("ZipCode",MobField.STRING,ZipCode);
            r.addField(fZipCode);

            String Address = null;
            if (txtAddress.getText().length()>0) Address = txtAddress.getText();
            MobField fAddress = new MobField("Address",MobField.STRING,Address);
            r.addField(fAddress);

            String Complement = null;
            if (txtComplement.getText().length()>0) Complement = txtComplement.getText();
            MobField fComplement = new MobField("Complement",MobField.STRING,Complement);
            r.addField(fComplement);

            String District = null;
            if (txtDistrict.getText().length()>0) District = txtDistrict.getText();
            MobField fDistrict = new MobField("District",MobField.STRING,District);
            r.addField(fDistrict);

            String City = null;
            if (txtCity.getText().length()>0) City = txtCity.getText();
            MobField fCity = new MobField("City",MobField.STRING,City);
            r.addField(fCity);

            String Telephone = null;
            if (txtTelephone.getText().length()>0) Telephone = txtTelephone.getText();
            MobField fTelephone = new MobField("Telephone",MobField.STRING,Telephone);
            r.addField(fTelephone);

            
            String setnull = "";
            String separator = "|";
            if (Birthday==null){
                setnull = "|1|";
                separator = "";
            }
            if (ZipCode==null){
                setnull += separator+"2|";
            }
            if (Address==null){
                setnull += separator+"3|";
            }
            if (Complement==null){
                setnull += separator+"4|";
            }
            if (District==null){
                setnull += separator+"5|";
            }
            if (City==null){
                setnull += separator+"6|";
            }
            if (Telephone==null){
                setnull += separator+"7|";
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
            pMap.put("TableName", "GlbPerson");
            my_resultMap = xCTx.execIwService("BOSetDpcExec","MtsSetExeDpcTable","UpdRecord", pMap);
            Dialog.show("Mensagem", TT_EXECUTION_OK, "OK", null);
        }
        catch(Exception ex){
            Dialog.show("Alert",ex.getMessage(),"OK",null);
        }
        
    }
    
    @Override
    public String getHtmlContent() {
        return performTagsPatientBasicInfo();
    }

    private String performTagsPatientBasicInfo(){
        try{
            
            //Colocar tags no texto abaixo 
            String birthday = txtBirthday.getText();
            String zipcode = txtZipCode.getText();
            String address = txtAddress.getText();
            String complement = txtComplement.getText();
            String district = txtDistrict.getText();
            String city = txtCity.getText();
            String telephone = txtTelephone.getText();
            
            String htmlBirthday = "";
            String htmlZipCode = "";
            String htmlAddress = "";
            String htmlComplement = "";
            String htmlDistrict = "";
            String htmlCity = "";
            String htmlTelephone = "";
            
            
            String htmlOpen =  "<table bgcolor=\"#F5F5F5\" cellspacing=\"1\" border=\"0\" cellpadding=\"0\" width=\"100%\">";
            
            htmlBirthday = "<tr><td width=\"2%\" bgcolor=\"#ADE2C2\"><div align=\"right\">1</div></td>"+
                "<td width=\"20%\"><div align=\"right\"><strong>Data de nascimento:&#160;</strong></div></td>"+
                "<td colspan=\"7\">"+birthday+"&#160;&#160;&#160;&#160;&#160;<strong>Idade:&#160;</strong>"+PatientAge+"</td></tr>";    

            String htmlAddressTitle = "<tr bgcolor=\"#B9BCCA\"><td bgcolor=\"#ADE2C2\"><div align=\"right\">2</div></td>"+
                "<td colspan=\"8\"><strong>&#160;Endereço Residencial</strong></td></tr>";
            
            htmlAddress = "<tr><td bgcolor=\"#ADE2C2\"><div align=\"right\">3</div></td>"+
                "<td bgcolor=\"#D7D7D7\"><div align=\"right\"><strong>Logradouro:&#160;</strong></div></td>"+
                "<td colspan=\"7\" bgcolor=\"#D7D7D7\">"+address+"</td></tr>";
                    
            htmlComplement = "<tr><td bgcolor=\"#ADE2C2\"><div align=\"right\">4</div></td>"+
                "<td><div align=\"right\"><strong>N° / Complemento:&#160;</strong></div></td>"+
                "<td colspan=\"7\">"+complement+"</td></tr>";
            
            htmlDistrict = "<tr><td bgcolor=\"#ADE2C2\"><div align=\"right\">5</div></td>"+
                "<td bgcolor=\"#D7D7D7\"><div align=\"right\"><strong>Bairro:&#160;</strong></div></td>"+
                "<td colspan=\"7\" bgcolor=\"#D7D7D7\">"+district+"</td></tr>";
            
            htmlZipCode = "<tr><td bgcolor=\"#ADE2C2\"><div align=\"right\">6</div></td>"+
                "<td><div align=\"right\"><strong>CEP:&#160;</strong></div></td>"+
                "<td colspan=\"7\">"+zipcode+"</td></tr>";

            htmlCity = "<tr><td bgcolor=\"#ADE2C2\"><div align=\"right\">7</div></td>"+
                "<td bgcolor=\"#D7D7D7\"><div align=\"right\"><strong>Cidade:&#160;</strong></div></td>"+
                "<td colspan=\"7\" bgcolor=\"#D7D7D7\">"+city+"</td></tr>";

            htmlTelephone = "<tr><td bgcolor=\"#ADE2C2\"><div align=\"right\">8</div></td>"+
                "<td><div align=\"right\"><strong>Telefones:&#160;</strong></div></td>"+
                "<td colspan=\"7\">"+telephone+"</td></tr>";
            
            String htmlClose = "<tr><td colspan=\"9\">&#160;</td></tr></table>";
            
            String htmlResult = htmlOpen+htmlBirthday+htmlAddressTitle+htmlAddress+htmlComplement+htmlDistrict+
                                htmlZipCode+htmlCity+htmlTelephone+htmlClose;                
                
            return htmlResult;
        }
        catch(Exception ex){
            return "Error performing tags scdiagnostics";
        }
    }

    private Long getIDPatient(long IDAdmission) throws Exception{
        return 4321L;
    }

    private void searchZipCode(){
        Dialog.show("Alert","Search zip code not implemented in mini-version","Ok",null);
    }
    
    private void editTelephone(){
        Dialog.show("Alert","Edit Fone not implemented in mini-version","Ok",null);
    }
}

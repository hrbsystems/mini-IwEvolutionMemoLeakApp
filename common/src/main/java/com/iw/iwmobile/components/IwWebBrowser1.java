/*      
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.components;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Storage;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.iw.iwmobile.Brain;
import com.iw.iwmobile.IwConstantsInterface;
import com.iw.iwmobile.comm.*;
import com.iw.iwmobile.entities.TDaddEvolutionOffline;
import com.iw.iwmobile.extensions.IwFormDiagnostics;
//import com.iw.iwmobile.extensions.IwFormMedicamentUsed;
import com.iw.iwmobile.extensions.IwFormPatientBasicInfo;
import com.iw.iwmobile.extensions.IwFormSCDiagnostics;
import com.iw.iwmobile.forms.IwDlgInputFreeText;
import com.iw.iwmobile.forms.IwFormInputDate1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static com.iw.iwmobile.components.IwWebBrowserInterface.CTRL_VAR_DISPLAY_ERROR;

/**
 *
 * @author helio
 */
public class IwWebBrowser1
        extends BrowserComponent
        implements IwWebBrowserInterface, IwConstantsInterface {
        
    private final ArrayList<String> ctrlVariables;
    
    // this constants bellow are also defined in IwRestAPI Project
    private final String IW_BUTTON_TYPE_ATTR =
            "iwbuttontype"; 
    private final String IW_VAR_NAME_ATTR =
            "iwvarname";
    private final String IWBUTTON_RICH_EDITOR_LAUNCHER = 
            "iwricheditorlauncher";
    private final String IWBUTTON_CALENDAR_LAUNCHER =
            "iwcalendarlauncher";
    private final String IWBUTTON_PLUGGED_FUNCTION_CALLER =
            "plugfunctioncaller";
    private final String IWBUTTON_CAMERA_LAUNCHER = 
            "iwcameralauncher";
    private final String IWBUTTON_PDF_FILE_CHOOSE__LAUNCHER =
            "iwpdffilechooselauncher";
    private final String IWBUTTON_FORM_MEDICAMENT_USED_LAUNCHER =
         // Also defined in RESTServiceBase in IwRestAPI Project    
        "IwFormMedicamentUsedLauncher";
    private final String IWBUTTON_FORM_DIAGNOSTIC_LAUNCHER =
         // Also defined in RESTServiceBase in IwRestAPI Project  
        "IwFormDiagnosticLauncher";    
    private final String IWBUTTON_FORM_CAP_ADMISSION_LAUNCHER =
         // Also defined in RESTServiceBase - IwRestAPI Project    
        "IwFormCapAdmissionLauncher";
    private final String IWBUTTON_FORM_CAP_PERSON_LAUNCHER =
         // Also defined in RESTServiceBase - IwRestAPI Project    
        "IwFormCapPersonLauncher";
    private final String IWBUTTON_FORM_PLAN_TEXT_LAUNCHER =
         // Also defined in RESTServiceBase - IwRestAPI Project    
        "IwFormPlanTextLauncher";
    
    

    private JSProxy jsVariablesProxy;
    
    // hmFormFieldTypes is used to perform synchronization of Offline evolutions
    // The routine that adjusts edited values applies specific rules defined
    // (Tiago). This process  needs to know the type of HTML Field used to render each variable.
    // This information is saved inside offline evolutions and used when 
    // situation demands.
    HashMap<String,String> hmFormFieldTypes;
    @Override
    public HashMap<String,String> getFormFieldTypesMap() {
        return this.hmFormFieldTypes;
    } 
    
    // The varNamesList is used into getHamlVariableValues() method.
    // it allows getting all IwTemplate variables using JSObject.get(String key)
    // Always when a template is loaded into WebBrowser Component the list must be recreated.
    // The method setIwTemplate() makes all necessary work to set up IwWebBrowser1
    //  and makes it capable to handle IWTemplate properly.
    ArrayList<String> varNamesList;
    
    // It's necessary to know whether a specific variable is boolean because the IWCare
    // handles boolean values as numeric String,
    // where "1" means true and "0" means false
    // This infomation is used when getting and setting  values from and to Web content.
    // The WebBrowser and the Browsers generally handle checkboxes and radio buttons
    // as a boolean value
    ArrayList<String> booleanVarNamesList;
    
    ArrayList<String> imageVarNamesList;
    ArrayList<String> pdfVarNamesList;
    
    // This attribute stores the initial values of each variable
    // before user editing.
    // This map can be requested by visual logic on demand
    // by getInitialVariablesMap () method
    // It's important to note, this  attribute is valued by onLoad method.
    // onLoad()  method is executed asynchronously a bit of time after new content
    // to become available in web Component.
    // So, initalVariableMap can be null during onLoad() execution.
    // The visual logic must pay attention in this behavior.
    HashMap<String,String> initialVariablesMap;
    
    //HashMap<String,String> scaledImagesMap = new HashMap<String,String>();
    MyStorage myStorage = new MyStorage();
    
    
    // always a page is loaded on Web Component
    // the onLoad method is called.It does several initialization tasks.
    // this Attribute has a boolean value. it is set to true when
    // all initialization tasks were done.
    // At this moment, IwWebBrowser1's methods can be called of reliable way.
    boolean ready = false;
    
    String idTemplate;
    String idText;
    String dynamicTableName;
    String idPatient;
    String idAdmssion;
    String idRowOfDynTable;
    String hasConsistFunc = "0";
    String timeStampCtr;
    String html; // offline operation makes html atribute necessary.
        
    Form parentForm;
        
    MobRecordset rsTemplateMetaData;
    
    public IwWebBrowser1(Form parentForm) {
        super();
        
        // init ctrlVarables with displayError and ConsistResult
        // Two special types of variables that were returned by Plugged Functions processing.
        this.ctrlVariables = new ArrayList<String>();
        this.ctrlVariables.add(CTRL_VAR_CONSIST_RESULT);
        this.ctrlVariables.add(CTRL_VAR_DISPLAY_ERROR); 
        
        this.parentForm = parentForm;
        
        //Tiago(3)
        setPinchToZoomEnabled(true);
        
        addWebEventListener(BrowserComponent.onLoad, e -> {
            onLoad();
        });

    }
            
    @Override
    public String getTemplateIdText() {
        return this.idText;
    }
    
    @Override
    public String getIdAdmission() {
        return this.idAdmssion;
    }
    
    @Override
    public String getIdTemplate() {
        return this.idTemplate;
    }
    
    @Override
    public String getIdRowOfDynTable() {
        return this.idRowOfDynTable;
    }
    
    @Override
    public String getDynamicTableName() {
        return this.dynamicTableName;
    }
    
    @Override
    public HashMap<String,String> getInicialVariablesMap() {
        return this.initialVariablesMap;
    }
    
    @Override
    public HashMap<String,String> getScaledImagePathsMap() {

        HashMap<String,String> htmlVarMap = getHtmlVariableValues();

        // todo verify this in original code - it is wrong initialize resp = getHtmlVariableValues()
//        HashMap<String,String> resp = getHtmlVariableValues();
        HashMap<String,String> resp = new HashMap<String, String>();

        for (String imgVarName : getImageVarNames()) {
            String localImageUrl = htmlVarMap.get(imgVarName);
            resp.put(imgVarName, myStorage.getScaledImagePath(localImageUrl));
        }
        
        return resp;
        
    }

    private boolean isValidScaledImagePath(String sPath) {
        return sPath.contains(FileSystemStorage.getInstance().getAppHomePath());
    }

    private boolean isValidNotScaledImagePath(String sPath) {
        return  sPath != null
                && !"".equals(sPath) 
                && sPath.length() > 15; 
    }
    
    private String scaleImage(String sFilePath) {
        String scaledFilePath;
        try {
            scaledFilePath = 
                Brain
                .getInstance()
                .scaleDownImage1(sFilePath);
        }
        catch (Exception e) {
            scaledFilePath = "";
        }
        return scaledFilePath;
    }
    
    @Override
    public void setInitialVariablesMap(HashMap<String,String> map) {
        this.initialVariablesMap = map;
    }
    
    @Override
    public boolean templateHasConsistFunc() {
        return "1".equals(this.hasConsistFunc);
    }
    
    @Override
    public String getTimeStampCtr() {
        return this.timeStampCtr;
    }
    
    @Override
    public boolean isReady() {
        return this.ready;
    }
    
    @Override
    public ArrayList<String> getImageVarNames() {
        return this.imageVarNamesList;
    }

    @Override
    public ArrayList getPdfVarNames() {
        return this.pdfVarNamesList;
    }

    @Override
    public ArrayList<String> getBooleanVarNames() {
        return this.booleanVarNamesList;
    }
    
    @Override
    public ArrayList<String> getVarNamesList() {
        return this.varNamesList;
    }
    
    @Override
    public HashMap<String,String> getHtmlVariableValues() {

        HashMap<String,String> resp = null;

        if (this.jsVariablesProxy != null && varNamesList != null) {
            
            resp = new HashMap<String,String>();
            String strValue = "";

            for (String varName : varNamesList) {
                JSRef ref =  jsVariablesProxy.getAndWait(varName);
                JSType type = ref.getJSType();
                if (type == JSType.STRING) {
                    strValue = ref.getValue();
                }
                else {
                    if (type == JSType.BOOLEAN) {
                        if (ref.getBoolean()) {
                            strValue = "1";
                        }
                        else {
                            strValue = "0";
                        }
                    }
                }
                resp.put(varName, strValue);
            }
        }
        
        return resp;
        
    }
    
    @Override
    public void setHtmlVariablesValues(Map<String,String> varMap) {
    
        if (varMap.isEmpty()) {
            return;
        }

        java.util.Set<String> varNamesSet = varMap.keySet();
        for (String varName : varNamesSet) {
            if (booleanVarNamesList.contains(varName)) {
                
                String strValue = varMap.get(varName);
            
                // boolan conversion
                Boolean bValue = false;
                if (strValue == null || "1".equals(strValue)) {
                    bValue = true;
                } 
                
                execute("getAngularScope().variables['" + varName + "']= " + bValue + ";getAngularScope().$apply();");
            
            }
            else {
                
                String strValue = varMap.get(varName);
                execute("getAngularScope().variables['" + varName + "']= '" + strValue + "';getAngularScope().$apply();");
            
            }

        }
        
//        scaledImagesMap.clear();
        
    }
        
    @Override
    public void resetAngularJSCtrlFlag() {
        // So far, do nothing.
    }
        
    @Override
    public Map<String,String> execConsistFunction() throws IwCommException {
        
        IwServiceCallerInterface sCaller =
                Brain.getInstance().getIwServiceCaller();
        
        ArrayList<String> imageVars = getImageVarNames();
        ArrayList<String> pdfVars = getImageVarNames();
        HashMap<String,String> htmlVars = getHtmlVariableValues();
        for (String varName : imageVars) {
            htmlVars.remove(varName);
        }
        for (String varName : pdfVars) {
            htmlVars.remove(varName);
        }    

        Map<String,String> resultMap = null;
            resultMap = sCaller.execConsistPluggedFunctionSync(
                    idText,
                    idTemplate,
                    idPatient,
                    idAdmssion,
                    htmlVars
            );
         
        return resultMap;

    }
    
    @Override
    public void execConsistFunctionAsync(
            IwHttpRequesterCallBack<Map<String,MobRecordset>> callback) {

        IwServiceCallerInterface sCaller =
                Brain.getInstance().getIwServiceCaller();
        
        ArrayList<String> imageVars = getImageVarNames();
        ArrayList<String> pdfVars = getImageVarNames();
        HashMap<String,String> htmlVars = getHtmlVariableValues();
        for (String varName : imageVars) {
            htmlVars.remove(varName);
        }
        for (String varName : pdfVars) {
            htmlVars.remove(varName);
        }    

        sCaller.execConsistPluggedFunctionAsync(
                    idText,
                    idTemplate,
                    idPatient,
                    idAdmssion,
                    htmlVars,
                    callback
        );
         
    }
    
    
    private final String FIELD_VAR_NAMES_LIST = "VAR_NAMES_LIST";  
    private final String FIELD_BOOLEAN_VAR_NAMES_LIST = "BOOLEAN_VAR_NAMES_LIST";
    private final String FIELD_IMAGE_VAR_NAMES_LIST = "IMAGE_VAR_NAMES_LIST";
    private final String FIELD_PDF_VAR_NAMES_LIST = "PDF_VAR_NAMES_LIST";
    private final String FIELD_ID_ROW_OF_DYN_TABLE = "ID_ROW_OF_DYN_TABLE";
    private final String FIELD_IDTEMPLATE = "IDTEMPLATE";
    private final String FIELD_TABLENAME = "TABLENAME";
    private final String FIELD_IDTEXT = "IDTEXT";
    private final String FIELD_IDADMISSION = "IDADMISSION";
    private final String FIELD_IDPATIENT = "IDPATIENT";
    private final String FIELD_HAS_CONSIST_FUNC = "HAS_CONSIST_FUNC";
    private final String FIELD_TIMESTAMPCTR = "TIMESTAMPCTR";
    private final String FIELD_DISPLAY_ERROR = "DISPLAY_ERROR";
    private final String DISPLAY_ERROR_INIT_NOT_RUN = "INIT_NOT_RUN";
    private final String DISPLAY_ERROR_NO_ERRORS = "NO_ERRORS";
    @Override
    public void setIwTemplate(
            Map<String,MobRecordset> resultMap,
            String baseUrl) {
        
        this.ready = false;
        
        Dialog dlgLoop = new InfiniteProgress().showInfiniteBlocking();
        
        this.rsTemplateMetaData = resultMap.get("rsTemplateMetaData");
        
        MobRecordset rsFormFieldTypes = resultMap.get("rsFormFieldTypes");
        this.hmFormFieldTypes = new HashMap<String,String>();
        // rsFormFieldTypes is necessary only for Sincronization 
        // of offline evolutions
        if (rsFormFieldTypes != null) {
            for (MobRow rFormFieldType : rsFormFieldTypes.rows) {
                this.hmFormFieldTypes.put(
                        rFormFieldType.fields.get(0).getName(),
                        rFormFieldType.fields.get(0).getValue()
                );
            }            
        }
                
        MobRecordset rsTemplateInfo = resultMap.get("rsTemplateInfo");
        for (MobField f : rsTemplateInfo.rows.get(0).fields) {
            if (FIELD_VAR_NAMES_LIST.equals(f.getName())) {
                varNamesList = new ArrayList<String>();
                StringTokenizer st = new StringTokenizer(f.getValue(), ",");
                while (st.hasMoreTokens()){
                    varNamesList.add(st.nextToken());
                }
            }
            else if (FIELD_BOOLEAN_VAR_NAMES_LIST.equals(f.getName())) {
                booleanVarNamesList = new ArrayList<String>();
                StringTokenizer st = new StringTokenizer(f.getValue(), ",");
                while (st.hasMoreTokens()){
                    booleanVarNamesList.add(st.nextToken());
                }
            }
            else if (FIELD_IMAGE_VAR_NAMES_LIST.equals(f.getName())) {
                imageVarNamesList = new ArrayList<String>();
                StringTokenizer st = new StringTokenizer(f.getValue(), ",");
                while (st.hasMoreTokens()){
                    imageVarNamesList.add(st.nextToken());
                }
            }
            else if (FIELD_PDF_VAR_NAMES_LIST.equals(f.getName())) {
                pdfVarNamesList = new ArrayList<String>();
                StringTokenizer st = new StringTokenizer(f.getValue(), ",");
                while (st.hasMoreTokens()){
                    pdfVarNamesList.add(st.nextToken());
                }
                // IwRestAPI has a bug that returns a wrong imageVarNamesList
                // The returned list also includes (wrongly) pdf var names.
                // Then , this error is being corrected removing pdf var names
                // from imageVarNamesList
                // Obs: IwRestAPI returns pdf var names correctly
                for (String vName : pdfVarNamesList) {
                    imageVarNamesList.remove(vName);
                }

            }
            else if (FIELD_IDTEMPLATE.equals(f.getName())) {
                this.idTemplate = f.getValue();
            }
            else if (FIELD_TABLENAME.equals(f.getName())) {
                this.dynamicTableName = f.getValue();
            }
            else if (FIELD_IDTEXT.equals(f.getName())) {
                this.idText = f.getValue();
            }
            else if (FIELD_IDADMISSION.equals(f.getName())) {
                this.idAdmssion = f.getValue();
            }
            else if (FIELD_IDPATIENT.equals(f.getName())) {
                this.idPatient = f.getValue();
            }
            else if (FIELD_ID_ROW_OF_DYN_TABLE.equals(f.getName())) {
                this.idRowOfDynTable = f.getValue();
            }
            else if (FIELD_HAS_CONSIST_FUNC.equals(f.getName())) {
                this.hasConsistFunc = f.getValue();
            }
            else if (FIELD_TIMESTAMPCTR.equals(f.getName())) {
                this.timeStampCtr = f.getValue();
            }
            else if (FIELD_DISPLAY_ERROR.equals(f.getName())) {

                String initFuncResponse = f.getValue();
                
                if (DISPLAY_ERROR_INIT_NOT_RUN.equals(initFuncResponse)) {
                    Dialog.show(
                            "Alerta",
                            "Função Init não foi Excecutada",
                            "OK", null);
                }
                else if (!DISPLAY_ERROR_NO_ERRORS.equals(initFuncResponse)
                         && !"".equals(initFuncResponse)) {
                    Dialog.show(
                            "Alerta",
                            initFuncResponse,
                            "OK", null);
                }
            
            }
                        
        }
        
        this.initialVariablesMap = null;
        MobRecordset rsHtml = resultMap.get("rsHtml");
        html = rsHtml.rows.get(0).fields.get(0).getValue();
        html = adjustCompboxesUsingPlugFuncInit(html);
                              
        setPage(html, baseUrl);
        
        dlgLoop.dispose();
        
    }

    private String adjustCompboxesUsingPlugFuncInit(String htmlIn) {
        String htmlOut = new String(htmlIn);
        return htmlOut;
    }
    
    @Override
    public void setIwTemplate(
            TDaddEvolutionOffline td,
            String baseUrl) {
        
        this.ready = false;

        Dialog dlgLoop = new InfiniteProgress().showInfiniteBlocking();

        this.idTemplate = "" + td.getIdTemplate();
        this.dynamicTableName = td.getTableName();
        this.idText = "" + td.getIdText();
        this.idAdmssion = "" + td.getIdAdmission();
        
        this.varNamesList = td.getVarNamesList();
        this.booleanVarNamesList = td.getBooleanVarNamesList();
        
        this.initialVariablesMap = null;
        
        html = td.getHtml();
        setPage(html, baseUrl);
        
        dlgLoop.dispose();
        
    }
    
    public String getHtml() {
        return this.html;
    }
    
    private ActionListener<ActionEvent> loadCompletedListener; 
    @Override
    public void setLoadCompletedListener(ActionListener<ActionEvent> actionListner) {        
        this.loadCompletedListener = actionListner;
    }    
                
    
////////////////////////////////////////////////////////////////////////////
//    ON LOAD METHOD
////////////////////////////////////////////////////////////////////////////    
    
    private String getFuncName(String ngClick) {
           int ind1 = ngClick.indexOf("$evol_");
           if (ind1 == -1) {
               return "IW_Plugged_Function_Name_Error";
           }
           String s1 = ngClick.substring(ind1);
           String s2 = s1.substring(0, s1.indexOf("'"));
           return s2;
    }
    
    private void onLoad() {
                
        Dialog dlgLoop = 
            new InfiniteProgress().showInfiniteBlocking();
        
        this.ready = false;
                
        this.jsVariablesProxy = createJSProxy("getAngularScope().variables");
        //JSProxy variables = createJSProxy("getIwVariableValues()");
        
        if (this.rsTemplateMetaData != null) {
            for (MobRow r : rsTemplateMetaData.rows) {
                
                String id   = r.field("id").getValue();
                String varName = r.field("varName").getValue();
                String ngClick = r.field("ng-click").getValue();

                if (ngClick.contains(IWBUTTON_CALENDAR_LAUNCHER)) {
                    String jsExp = "document.getElementById('" + id + "').onclick = function(){callback.onSuccess('" + varName + "');};";
                    addJSCallback(
                       jsExp,
                       res -> launchCalendar(res.toString())
                    );                            
                }
                
                else if (ngClick.contains(IWBUTTON_RICH_EDITOR_LAUNCHER)) {
                    String jsExp = "document.getElementById('" + id + "').onclick = function(){callback.onSuccess('" + varName + "');};";
                    addJSCallback(
                       jsExp,
                       res -> launchRichEditor(res.toString())
                    );                            
                }

                else if (ngClick.contains(IWBUTTON_PLUGGED_FUNCTION_CALLER)) {
                    String funcName = getFuncName(ngClick);
                    String jsExp = "document.getElementById('" + id + "').onclick = function(){callback.onSuccess('" + funcName + "');};";
                    addJSCallback(
                       jsExp,
                       res -> callPluggedFunction(res.toString())
                    );                            
                }

                else if (ngClick.contains(IWBUTTON_CAMERA_LAUNCHER)) {
                    String jsExp = "document.getElementById('" + id + "').onclick = function(){callback.onSuccess('" + varName + "');};";
                    addJSCallback(
                       jsExp,
                       res -> launchSelPhoto_new(res.toString())
                    );                            
                }

                // removed PDF launch form

                else if (ngClick.contains(IWBUTTON_FORM_MEDICAMENT_USED_LAUNCHER)) {
                    String jsExp = "document.getElementById('" + id + "').onclick = function(){callback.onSuccess('" + varName + "');};";
                    addJSCallback(
                       jsExp,
                       res -> launchFormMedicamentUsed(res.toString())
                    );                            
                }

                else if (ngClick.contains(IWBUTTON_FORM_DIAGNOSTIC_LAUNCHER)) {
                    String jsExp = "document.getElementById('" + id + "').onclick = function(){callback.onSuccess('" + varName + "');};";
                    addJSCallback(
                       jsExp,
                       res -> launchFormDiagnostic(res.toString())
                    );                            
                }
                
                else if (ngClick.contains(IWBUTTON_FORM_CAP_ADMISSION_LAUNCHER)) {
                    String jsExp = "document.getElementById('" + id + "').onclick = function(){callback.onSuccess('" + varName + "');};";
                    addJSCallback(
                       jsExp,
                       res -> launchFormCapAdmission(res.toString())
                    );                            
                }

                else if (ngClick.contains(IWBUTTON_FORM_CAP_PERSON_LAUNCHER)) {
                    String jsExp = "document.getElementById('" + id + "').onclick = function(){callback.onSuccess('" + varName + "');};";
                    addJSCallback(
                       jsExp,
                       res -> launchFormCapPerson(res.toString())
                    );                            
                }

                else if (ngClick.contains(IWBUTTON_FORM_PLAN_TEXT_LAUNCHER)) {
                    String jsExp = "document.getElementById('" + id + "').onclick = function(){callback.onSuccess('" + varName + "');};";
                    addJSCallback(
                       jsExp,
                       res -> launchFormPlainText(res.toString())
                    );                            
                }
                
            }
        }
                        
        this.initialVariablesMap = getHtmlVariableValues();
        
//        this.scaledImagesMap = new HashMap<String,String>();
//        for (String imgVarName : getImageVarNames()) {
//            this.scaledImagesMap.put(imgVarName, "");
//        }
        
        if (loadCompletedListener != null) {
            loadCompletedListener.actionPerformed(null);
        }

        this.ready = true;
       
        dlgLoop.dispose();
        
    }
    
    private void launchCalendar(final String varName) {
    
        System.out.println("Launch Calendar : " + varName);
        
        String varValue = jsVariablesProxy.getAndWait(varName).getValue();

        final IwFormInputDate1 f = new IwFormInputDate1(varName, varValue);
        Command backCommand = new Command("Voltar") {
            @Override
            public void actionPerformed(ActionEvent ev) {
                HashMap<String,String> map = new HashMap<String,String>();
                map.put(varName, f.getStrEditedDate());
                setHtmlVariablesValues(map);                    
                parentForm.showBack();
            } 
         }; 
         f.setBackCommand(backCommand);               
         f.show();
        
    }

    private void launchRichEditor(final String varName) {

        System.out.println("LaunchRichTextEditor : " + varName);

        String varValue =  jsVariablesProxy.getAndWait(varName).getValue();

//        IwDlgInputHtmlRichText f = new IwDlgInputHtmlRichText(varName,varValue);
        IwDlgInputFreeText f = new IwDlgInputFreeText(varName,varValue);

        Command backCommand = new Command("Voltar") {
             @Override
             public void actionPerformed(ActionEvent ev) {
                HashMap<String,String> map = new HashMap<String,String>();
                map.put(varName, f.getEditedHtml());
                setHtmlVariablesValues(map);                    
                parentForm.show();
             } 
        }; 
        f.setBackCommand(backCommand);               
        f.show();
        
    }
    
    private void callPluggedFunction(final String funcName) {

        System.out.println("CallPluggedFunction : " + funcName);
        
        Brain brain = Brain.getInstance();
        if (brain.isOfflineMode()) {

            Dialog.show(
                "Mensagem",
                "Execução Função Plugada não disponível Offline",
                "Ok",
                null
            );

            return;

        }

        callPlugFuncAsync(
            idText,
            idTemplate,
            funcName,
            idPatient,
            idAdmssion
        );
        
    }
    
    private HashMap<String,String> getOnlyChangedValues (
        HashMap<String,String> previousMap,
        HashMap<String,String> afterFuncCallMap) {

        String varName;
        String previousValue;
        String afterValue;
        HashMap<String,String> resp = new HashMap<String,String>();
        for (Map.Entry<String, String> entry : previousMap.entrySet()) {
            varName = entry.getKey();
            previousValue = entry.getValue();
            afterValue = afterFuncCallMap.get(varName);
            if (!previousValue.equals(afterValue)) {
                resp.put(varName, afterValue);
            }
        }
        return resp;
    }
    
    private void callPlugFuncAsync(
            String idText,
            String idTemplate,
            String functionName,
            String idPatient,
            String idAdmssion) {

        InfiniteProgress prog = new InfiniteProgress();
        final Dialog dlgProgress = prog.showInfiniteBlocking();
        
        final HashMap<String,String> previousMap = getHtmlVariableValues();

        IwHttpRequesterCallBack<Map<String,MobRecordset>> callback =
            new IwHttpRequesterCallBack<Map<String,MobRecordset>>() {
                
           @Override
           public void onFailure(MobRecordsetError rsError) {
               dlgProgress.dispose();
               Dialog.show("Alert", rsError.getTranslation(), "OK", null);
           }
           
           @Override
           public void onSuccess(Map<String,MobRecordset> resultMap) {
                dlgProgress.dispose();
                MobRecordset mobResult = resultMap.get("rsVariables");
                HashMap<String,String> hmResp = new HashMap<String,String>();
                for(MobField f : mobResult.rows.get(0).fields) {
                    hmResp.put(f.getName(), f.getValue());
                }
                if (hmResp.containsKey(CTRL_VAR_DISPLAY_ERROR)) {
                    String displayError = hmResp.get("displayError");
                    Dialog.show("Error", displayError, "OK", null);
                    hmResp.remove(CTRL_VAR_DISPLAY_ERROR);
                }
                HashMap<String,String> changesOnlyMap =
                    getOnlyChangedValues(previousMap, hmResp);
                setHtmlVariablesValues(changesOnlyMap);
           }
        };

        IwServiceCallerInterface caller =
                Brain.getInstance().getIwServiceCaller();
        
        ArrayList<String> imageVars = getImageVarNames();
        ArrayList<String> pdfVars = getImageVarNames();
        HashMap<String,String> htmlVars = getHtmlVariableValues();
        for (String varName : imageVars) {
            htmlVars.remove(varName);
        }
        for (String varName : pdfVars) {
            htmlVars.remove(varName);
        }    

        caller.execPluggedFunctionAsync(
               idText,
               idTemplate,
               functionName,
               idPatient,
               idAdmssion,
               htmlVars,
               callback
        );
        
    }
    
    
    private void launchSelPhoto_new(final String varName) {
        
        System.out.println("LaunchSelPhoto_new : " + varName);
        
        String varValue = jsVariablesProxy.getAndWait(varName).getValue();
//        if (varValue.startsWith("http://localhost:8888/")) {
//            varValue = 
//                Brain.getInstance().getWebServeRootPath()
//                + "/"
//                + varValue.substring("http://localhost:8888/".length());
//        }
        varValue = myStorage.getScaledImagePath(varValue);

        System.gc();
        
        final IwFormSelectPhoto_new f =
            new IwFormSelectPhoto_new(
                varName,
                varValue
            );

        Command backCommand = new Command("Voltar") {
             @Override
             public void actionPerformed(ActionEvent ev) {
                 
                if (f.getUserAction() == f.USER_ACTION_CONFIRM) {
                    HashMap<String,String> map = new HashMap<String,String>();
                    String browserImgPath = f.getImageLocalhostUrl();  

                    map.put(varName, browserImgPath);
                    setHtmlVariablesValues(map);
                    
                    myStorage.saveScaledImagePath(
                        browserImgPath,
                        f.getScaledImagePath()
                    );
                    System.gc();
                }
                                 
                parentForm.showBack();
                
             } 
        }; 
        f.setBackCommand(backCommand);               
        f.show();
     
    }

    private void launchFormMedicamentUsed(final String varName) {
        System.out.println("launchFormMedicamentUsed : " + varName);
        Dialog.show("Alert","From for Medicament in Used not available in mini-version","Ok",null);
    }

    private void launchFormDiagnostic(String varName) {
        System.out.println("launchFormDiagnostic : " + varName);
        Dialog.show("Message", "IwFormDiagnostics not available in mini-app", "Ok", null);
    }

    private void launchFormCapAdmission(String varName) {
        System.out.println("launchFormCapAdmission : " + varName);
        Dialog.show("Message", "Form for CapAdmission INfo not available in mini-app", "Ok", null);
    }

    private void launchFormCapPerson(String varName) {
        System.out.println("launchFormCapPerson : " + varName);
        Dialog.show("Message", "Form IwFormPatientBasicInfo not available in mini-app", "ok", null);
    }

    private void launchFormPlainText(String param) {
        System.out.println("launchFormPlainText : " + param);
        Dialog.show(
                "Message",
                "launchFormPlainText not implemented correctly - see launching rich text",
                "OK",
                null
        );
    }

    @Override
    public String getIwTranslation(String token) {
        return Brain.getInstance().getIwTranslation(token);
    }
    
    
    public void removeLocalImageUrl(String localImageUrl) {
        this.myStorage.removeLocalImageUrl(localImageUrl);
    }

    public void removeLocalImageFilePath(String localImageUrl) {
        this.myStorage.removeLocalImageFilePath(localImageUrl);
    }
    
    
    class MyStorage {
        
        String IWBROWSER_STORAGE_IMAGES_MAP_KEY = "IwBrowserStorageImagesMap";
        Storage sto = Storage.getInstance();

        public MyStorage() {
            this.sto = Storage.getInstance();
        }
        
        public String getScaledImagePath(String localImageUrl) {
            
            if (localImageUrl == null) {
                return ""; 
            }
            
            HashMap<String,String> imagesMap = 
                (HashMap) sto.readObject(IWBROWSER_STORAGE_IMAGES_MAP_KEY);
            
            if (imagesMap == null) {
                return "";
            }
            
            String resp = imagesMap.get(localImageUrl);
            if (resp == null) {
                return "";
            }
            
            return resp;
            
        }
        
        public void saveScaledImagePath(
            String localImageUrl,
            String scaledImagePath) {
            
            HashMap<String,String> imagesMap = 
                (HashMap) sto.readObject(IWBROWSER_STORAGE_IMAGES_MAP_KEY);
            
            if (imagesMap == null) {
                imagesMap = new HashMap<String,String>();
                imagesMap.put(localImageUrl, scaledImagePath);
            }
            else {
                imagesMap.put(localImageUrl, scaledImagePath);
            }
            
            sto.writeObject(IWBROWSER_STORAGE_IMAGES_MAP_KEY, imagesMap);
            
        }
        
        
        public void removeLocalImageUrl(String localImageUrl) {
            
            if (localImageUrl == null) {
                return;
            }
            
            HashMap<String,String> imagesMap = 
                (HashMap) sto.readObject(IWBROWSER_STORAGE_IMAGES_MAP_KEY);
            
            if (imagesMap == null) {
                return;
            }
            
            imagesMap.remove(localImageUrl);
            
            sto.writeObject(IWBROWSER_STORAGE_IMAGES_MAP_KEY, imagesMap);
            
        }

        public void removeLocalImageFilePath(String localImageFilePath) {
            
            HashMap<String,String> imagesMap = 
                (HashMap) sto.readObject(IWBROWSER_STORAGE_IMAGES_MAP_KEY);
            
            if (imagesMap == null) {
                return;
            }
            
            String imgUrl = "No_Url";
            for (Map.Entry<String,String> entry : imagesMap.entrySet()) {
                if (localImageFilePath.equals(entry.getValue())) {
                    imgUrl = entry.getKey();
                    break;
                }
            }
           
            imagesMap.remove(imgUrl);
            
            sto.writeObject(IWBROWSER_STORAGE_IMAGES_MAP_KEY, imagesMap);
            
        }
        
        
        
    }
    
}



    


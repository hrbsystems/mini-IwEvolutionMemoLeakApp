/*      
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.components.mockedComponents;

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
import com.iw.iwmobile.extensions.IwFormPatientBasicInfo;
import com.iw.iwmobile.extensions.IwFormSCDiagnostics;
import com.iw.iwmobile.forms.IwDlgInputFreeText;
import com.iw.iwmobile.forms.IwFormInputDate1;
import com.iw.iwmobile.components.IwWebBrowserInterface;
import com.iw.iwmobile.IwConstantsInterface;

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
    extends com.codename1.ui.Container
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

    // hmFormFieldTypes is used to perform synchronization of Offline evolutions
    // The routine that adjusts edited values applies specific rules defined
    // (Tiago). This process  needs to know the type of HTML Field used to render each variable.
    // This information is saved inside offline evolutions and used when 
    // situation demands.
    HashMap<String,String> hmFormFieldTypes;
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
    
    
    boolean ready = true;
    
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

    }

    @Override
    public void setPage(String a, String b) {

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
        return new HashMap<String,String>();
    }

    private boolean isValidScaledImagePath(String sPath) {
        return false;
    }

    private boolean isValidNotScaledImagePath(String sPath) {
        return  sPath != null
                && !"".equals(sPath) 
                && sPath.length() > 15; 
    }
    
    private String scaleImage(String sFilePath) {
        return "";
    }
    
    @Override
    public void setInitialVariablesMap(HashMap<String,String> map) {
        this.initialVariablesMap = map;
    }
    
    @Override
    public boolean templateHasConsistFunc() {
        return false;
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
        return null;
    }
    
    @Override
    public void setHtmlVariablesValues(Map<String,String> varMap) {
    }
        
    @Override
    public void resetAngularJSCtrlFlag() {
    }
        
    @Override
    public Map<String,String> execConsistFunction() throws IwCommException {
        Map<String,String> resultMap = null;
        return resultMap;
    }
    
    @Override
    public void execConsistFunctionAsync(
            IwHttpRequesterCallBack<Map<String,MobRecordset>> callback) {
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
    }

    private String adjustCompboxesUsingPlugFuncInit(String htmlIn) {
        String htmlOut = new String(htmlIn);
        return htmlOut;
    }
    
    @Override
    public void setIwTemplate(
            TDaddEvolutionOffline td,
            String baseUrl) {
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
        return "IW_Plugged_Function_Name_Error";
    }
    
    private void onLoad() {
        this.ready = true;
    }
    
    private void launchCalendar(final String varName) {
    }

    private void launchRichEditor(final String varName) {
    }
    
    private void callPluggedFunction(final String funcName) {
    }
    
    private HashMap<String,String> getOnlyChangedValues (
        HashMap<String,String> previousMap,
        HashMap<String,String> afterFuncCallMap) {
        return new HashMap<String,String>();
    }
    
    private void callPlugFuncAsync(
            String idText,
            String idTemplate,
            String functionName,
            String idPatient,
            String idAdmssion) {
    }
    
    
    private void launchSelPhoto_new(final String varName) {
    }

    private void launchFormMedicamentUsed(final String varName) {
    }

    private void launchFormDiagnostic(String varName) {
    }

    private void launchFormCapAdmission(String varName) {
    }

    private void launchFormCapPerson(String varName) {
    }

    private void launchFormPlainText(String param) {
    }

    @Override
    public String getIwTranslation(String token) {
        return token;
    }
    
    
    public void removeLocalImageUrl(String localImageUrl) {
    }

    public void removeLocalImageFilePath(String localImageUrl) {
    }
    
    
    class MyStorage {
        
        String IWMOCKEDBROWSER_STORAGE_IMAGES_MAP_KEY = "IwMockedBrowserStorageImagesMap";
        Storage sto = Storage.getInstance();

        public MyStorage() {
            this.sto = Storage.getInstance();
        }
        
        public String getScaledImagePath(String localImageUrl) {
            
            if (localImageUrl == null) {
                return ""; 
            }
            
            HashMap<String,String> imagesMap = 
                (HashMap) sto.readObject(IWMOCKEDBROWSER_STORAGE_IMAGES_MAP_KEY);
            
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
        }
        
        
        public void removeLocalImageUrl(String localImageUrl) {
        }

        public void removeLocalImageFilePath(String localImageFilePath) {
        }
    }
    
}

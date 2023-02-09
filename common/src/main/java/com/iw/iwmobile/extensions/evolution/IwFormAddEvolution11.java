/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.extensions.evolution;

import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.iw.iwmobile.Brain;
import com.iw.iwmobile.comm.*;
import com.iw.iwmobile.components.mockedComponents.IwWebBrowser1;
import com.iw.iwmobile.components.IwDateTimePicker;
import com.iw.iwmobile.components.IwSelectionButton;
import com.iw.iwmobile.components.IwAccrComboBox;
import com.iw.iwmobile.components.IwAccrMultiSelection;
import com.iw.iwmobile.components.IwButton;
import com.iw.iwmobile.entities.TDaddEvolutionOffline;
import com.iw.iwmobile.forms.IwDlgGetPin;
import com.iw.iwmobile.forms.IwDlgSignature;
import com.iw.iwmobile.forms.IwFormBase;
import com.iw.iwmobile._fakelibs.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author helio
 */
public class IwFormAddEvolution11 extends IwFormBase {
    
    protected long idAdmission;
    protected String patientShortName;
    
    Label lblSTartDate;
    Label lblEndDate;
    IwDateTimePicker pckStartDate;
    IwDateTimePicker pckEndDate;
    
    //ComboBox cmbTemplate;
    protected IwSelectionButton cmbTemplate;

    IwAccrComboBox<AdditionalReason> accrVisitReason;
    IwAccrComboBox<Speciality> accrSpecialities;
    IwAccrComboBox<ConsultType> accrConsultType;
    CheckBox chkSkipGenConsult;    
    
    IwAccrMultiSelection<ProgrammedConsult> accrProgConsult;
    CheckBox  chkCanceledConsult1;
    
    IwAccrMultiSelection<PlannedConsult> accrPlanConsult;
    CheckBox  chkCanceledConsult2;
        
    protected Button btnSave;
//    protected Button btnCheckin;
    Label  lblFeedback;
    
    IwWebBrowser1 webBrowser;
    Button btnEditTemplate;
    IwFormAddEvolution21 formEditTemplate;
    
    //Container pnlVisitReason;
    
    // to be used in Initialization of EventTimeIni.
    Date startForm = new Date();
    
    public IwFormAddEvolution11(long idAdmission, String patientShortName) {
        
        super();
        
        this.idAdmission = idAdmission;
        this.patientShortName = patientShortName;
        
        String title
                = getIwTranslation(TT_ADD_EVOLUTION)
                + " " + patientShortName
                + "["
                + idAdmission
                + "]";
        setTitle(title);
        
        Brain.getInstance()
            .setFormMenu(
                IwFormAddEvolution11.this,
                Brain.getInstance().MENU_STANDARD
            );        
        
        setScrollableY(true);
        
        preInit();
        init();
        postInit();
        
        System.gc();
    }
    
    private void preInit() {
        
        lblSTartDate = createLblStartDate();
        lblEndDate = createLblEndDate();
        pckStartDate = createPckStartDate();
        pckEndDate   = createPckEndDate();

        cmbTemplate = createCmbTemplate1();
        accrConsultType = createAccrConsultType();
        chkSkipGenConsult = createChkSkipGenConsult();
        accrSpecialities = createAccrSpecialities();
        accrVisitReason = createAccrVisitReason();
        
        chkCanceledConsult1 = createChkCanceledConsult1();
        
        chkCanceledConsult2 = createChkCanceledConsult2();
        
        btnEditTemplate = createBtnEditTemplate();
        btnSave = createBtnSave_Sync();
        webBrowser = createWebBrowser();

        lblFeedback = createLblFeedback();
        
        // Panels to populate bottom (only one at time) 
        // Which one of them depends  of  Medical Appointment type selected
        accrProgConsult = createAccrProgConsult();
        accrPlanConsult = createAccrPlanConsult();
                
        ctnConsultType = new MyContainerContentType();
        ctnPeriod = new MyContainerPeriod();
        ctnSpeciality = new MyContainerSpeciality();
        ctnPlanConsults = new MyContainerPlanConsults();
        ctnProgConsults = new MyContainerProgConsults();
        ctnSkipConsult = new MyContainerSkipConsult();
        ctnVisitReason = new MyContainerVisitReason();
        ctnTemplate = new MyContainerTemplate();

    }
    
    protected MyContainerContentType ctnConsultType;
    protected MyContainerTemplate ctnTemplate;
    protected MyContainerPeriod ctnPeriod;
    protected MyContainerSkipConsult ctnSkipConsult;
    protected MyContainerSpeciality ctnSpeciality;
    protected MyContainerProgConsults ctnProgConsults;
    protected MyContainerPlanConsults ctnPlanConsults;
    protected MyContainerVisitReason ctnVisitReason;
    
    public class MyContainerContentType extends Container {
        public MyContainerContentType() {
            setLayout(new GridLayout(1,1));
            getStyle().setBorder(Border.createLineBorder(1));
            getStyle().setMargin(5, 5, 5, 5);
            addComponent(accrConsultType);
        }
    }
    
    public class MyContainerTemplate extends Container {
        public MyContainerTemplate() {
            setLayout(new BorderLayout());
            getStyle().setBorder(Border.createLineBorder(1));
            getStyle().setMargin(5, 5, 5, 5);
            addComponent(BorderLayout.CENTER, cmbTemplate);
            addComponent(BorderLayout.EAST, btnEditTemplate);            
        }
    }
    
    public class MyContainerPeriod extends Container {
        public MyContainerPeriod() {
            setLayout(new GridLayout(2,1));

            getStyle().setBorder(Border.createLineBorder(1));
            getStyle().setMargin(5, 5, 5, 5);
            
            Container cStart = new Container(new BorderLayout());
            cStart.addComponent(BorderLayout.WEST,lblSTartDate);
            cStart.addComponent(BorderLayout.CENTER,pckStartDate);
            
            Container cEnd = new Container(new BorderLayout());
            cEnd.addComponent(BorderLayout.WEST,lblEndDate);
            cEnd.addComponent(BorderLayout.CENTER,pckEndDate);
            
            //cPeriod.addComponent(pckStartTime);
            addComponent(cStart);
            addComponent(cEnd);
            //cPeriod.addComponent(pckEndTime);    
            
        }
    }
    
    public class MyContainerSkipConsult extends Container {
        public MyContainerSkipConsult() {
            setLayout(new GridLayout(1,1));
            getStyle().setBorder(Border.createLineBorder(1));
            getStyle().setMargin(5, 5, 5, 5);
            addComponent(chkSkipGenConsult);
        }
    }
    public class MyContainerSpeciality extends Container {
        public MyContainerSpeciality() {
            setLayout(new GridLayout(1,1));
            getStyle().setBorder(Border.createLineBorder(1));
            getStyle().setMargin(5, 5, 5, 5);
            addComponent(accrSpecialities);
        }
    }
    
    public class MyContainerProgConsults extends Container {
        public MyContainerProgConsults() {
            setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            getStyle().setBorder(Border.createLineBorder(1));
            getStyle().setMargin(5, 5, 5, 5);
            addComponent(accrProgConsult);
            addComponent(chkCanceledConsult1);       
        }
    }
    
    public class MyContainerPlanConsults extends Container {
        public MyContainerPlanConsults() {
            setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            getStyle().setBorder(Border.createLineBorder(1));
            getStyle().setMargin(5, 5, 5, 5);
            addComponent(accrPlanConsult);
            addComponent(chkCanceledConsult2);                        
        }
    }
    
    public class MyContainerVisitReason extends Container {
        public MyContainerVisitReason() {
            setLayout(new GridLayout(1,1));
            getStyle().setBorder(Border.createLineBorder(1));
            getStyle().setMargin(5, 5, 5, 5);
            addComponent(accrVisitReason);
        }
    }
    
    protected Container center;
    protected Container south;
    protected Container southWest;
    protected Container southEast;

    private void init() {

        setLayout(new BorderLayout());
        
        center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        // Buttons
        southWest = new Container(new FlowLayout(LEFT));
        southWest.addComponent(new Label(" "));
        southWest.addComponent(btnSave);

        south = new Container(new BorderLayout());
        south.addComponent(BorderLayout.WEST, southWest);
        south.addComponent(BorderLayout.CENTER, lblFeedback);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.addComponent(BorderLayout.CENTER, center);
        c.addComponent(BorderLayout.SOUTH, south);
        
    }
        
    
    double geoLocationAccuracy;
    double geoCheckinTolerance;
    String geoLocationPatient;
    long idCapAdmProfCheckin;
    private void postInit() {
        
       IwServiceCallerInterface caller = Brain.getInstance().getIwServiceCaller();
        
        IwHttpRequesterCallBack<Map<String,MobRecordset>> callback =
                new IwHttpRequesterCallBack<Map<String,MobRecordset>>() {

            @Override
            public void onFailure(MobRecordsetError rsError) {
                Dialog.show("Alert", rsError.getTranslation(), "OK", null);
            }

            @Override
            public void onSuccess(Map<String,MobRecordset> resultMap) {

                // resultMap must contain this 4 mobRecordsets:
                // rsTemplates
                // rsProgrammedConsults
                // rsPlannedConsults
                // rsAdditionalReasons
                // rsSpecialities
                
                geoLocationAccuracy = getGeoLocationAccuracy(resultMap);
                geoCheckinTolerance = getGeoCheckinToterance(resultMap);
                geoLocationPatient = getGeoLocationPatient(resultMap);
                idCapAdmProfCheckin = getIdCapAdmProfCheckin(resultMap);
                
                updateBtnCheckinState(resultMap);
                updateFormComponents(resultMap);
                
            }
        };

        caller.getData4FormAddEvolution(this.idAdmission, callback);
        
    }
        
    private double getGeoLocationAccuracy(Map<String,MobRecordset> resultMap) {

        MobRecordset rsProfCheckInInfo = resultMap.get("rsProfCheckInInfo");
        
        for (MobRow r : rsProfCheckInInfo.rows) {
            for (MobField f : r.fields) {
                if ("GEO_LOCATION_ACCURACY".equals(f.getName())) {
                     return (Double) f.objValue();
                }
            }
        }
        
        return -1D;
        
    }
    private double getGeoCheckinToterance(Map<String,MobRecordset> resultMap) {
        
        MobRecordset rsProfCheckInInfo = resultMap.get("rsProfCheckInInfo");
        
        for (MobRow r : rsProfCheckInInfo.rows) {
            for (MobField f : r.fields) {
                if ("GEO_CHECKIN_TOLERANCE".equals(f.getName())) {
                     return (Double) f.objValue();
                }
            }
        }
        
        return -1D;
        
    }
  
    private String getGeoLocationPatient(Map<String,MobRecordset> resultMap) {
        
        MobRecordset rsProfCheckInInfo = resultMap.get("rsProfCheckInInfo");
        
        for (MobRow r : rsProfCheckInInfo.rows) {
            for (MobField f : r.fields) {
                if ("GEO_LOCATION_PATIENT".equals(f.getName())) {
                    return f.getValue();
                }
            }
        }
        
        return "";
        
    }
    
    private long getIdCapAdmProfCheckin(Map<String,MobRecordset> resultMap) {
        
        MobRecordset rsProfCheckInInfo = resultMap.get("rsProfCheckInInfo");
        
        for (MobRow r : rsProfCheckInInfo.rows) {
            for (MobField f : r.fields) {
                if ("ID_CAPADMPROFCHECKIN".equals(f.getName())) {
                     return (Long) f.objValue();
                }
            }
        }
        
        return -1L;
    }
        
    
    class Template {
        long id;
        String name;
        long idText;
        String tableName;
        int persistType;
        
        // This new tamplate atribute (FormType) is a bad practice
        // but it was required to be coded.
        // this attribute points that is necessary other approach
        // to save this template.
        // Templates with this attribute is equals 1 changes 
        // the saving process behavior:
        // 1) shows before Dialog for user signature.
        // 2) If use signature is ok run plugged function of consistence
        //    and finish. It doesn't execute anything more.
        // Sounds weird, but it is that.
        // Attribute name (FormType) doesn't meaning nothing too.
        int formType;

        public Template(
                long id,
                String name,
                long idText,
                String tableName,
                int persistType,
                int formType) {
            this.id = id;
            this.name = name;
            this.idText = idText;
            this.tableName = tableName;
            this.persistType = persistType;
            this.formType = formType;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    

    class ProgrammedConsult {

        public long id;
        public String description;
        public Date programmedStart;
        private long idCampaignItem;

        public ProgrammedConsult(
                long id,
                String description,
                Date programmedStart,
                long idCampaignItem) {
            
            this.id = id;
            this.description = description;
            this.programmedStart = programmedStart;
            this.idCampaignItem = idCampaignItem;
        }

        @Override
        public String toString() {
            return description;
        }

        public long getId() {
            return this.id;
        }
        
        public String getDescription() {
            return this.description;
        }

    }
    
    class PlannedConsult {
        private long id;
        private String description;
        private long idCampaignItem;
        
        public PlannedConsult(
                long id,
                String description,
                long idCampaignItem) {
            
            this.id = id;
            this.description = description;
            this.idCampaignItem = idCampaignItem;
        }

        @Override
        public String toString() {
            return description;
        }
        
        public long getId() {
            return this.id;
        }
        
        public String getDescription() {
            return this.description;
        }

        /**
         * @return the idCampaignItem
         */
        public long getIdCampaignItem() {
            return idCampaignItem;
        }

        /**
         * @param idCampaignItem the idCampaignItem to set
         */
        public void setIdCampaignItem(long idCampaignItem) {
            this.idCampaignItem = idCampaignItem;
        }
        
    }
    
    class AdditionalReason {
        int id;
        String description;

        public AdditionalReason(int id, String description) {
            this.id = id;
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }
    
    class Speciality {
        long id;
        String name;

        public Speciality(long id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    
    private void updateBtnCheckinState(Map<String,MobRecordset> resultMap) {
    }
    
    private void updateFormComponents(Map<String,MobRecordset> resultMap) {
        
        ArrayList<Template> templates =  getTemplates(resultMap);
        Template emptyTemplate = new Template(
                -1, // id
                "Template ...", // template name
                -1, // idText
                "", // tableName
                -1, // persistenceType
                -1  // formType
        );
        cmbTemplate.addItem(emptyTemplate);
        for (Template template : templates) {
            cmbTemplate.addItem(template);
        }
        cmbTemplate.setSelectedItem(emptyTemplate);
        cmbTemplate.setEnabled(true);
        
        accrSpecialities.setItems(getSpecialities(resultMap));
        accrSpecialities.setSelectedIndex(0);
        accrVisitReason.setItems(getAdditionalReasons(resultMap));
        
        accrProgConsult.setItems(getProgrammedConsults(resultMap));
        accrProgConsult.setSelectedItems(
            getInitProgConsultSelected(resultMap, accrProgConsult.getItems())
        );
        accrProgConsult.setMaxOneSelection(
            isMaxOneConsultSelection(resultMap)
        );
        
        
        accrPlanConsult.setItems(getPlannedConsults(resultMap));
        accrPlanConsult.setSelectedItems(
            getInitPlanConsultSelected(resultMap, accrPlanConsult.getItems())
        );
        accrPlanConsult.setMaxOneSelection(
            isMaxOneConsultSelection(resultMap)
        );

        // Updates ConsultType ComboBox options.
        // Only options that makes sence and is alowed 
        // will be available.
        
        if (isConsultEvaluationAllowed(resultMap)) {
            accrConsultType.addItem(consult_Evaluation);
        }    
        if (isConsultInfoRegAllowed(resultMap)) {
            accrConsultType.addItem(consult_InfoReg);
        }
        if (isConsultStandAloneAllowed(resultMap)) {
            accrConsultType.addItem(consult_StandAlone);
        }
        if (isProgConsultAllowed(resultMap)
            && !accrProgConsult.getItems().isEmpty()) {
            accrConsultType.addItem(consult_Programmed);
        }
        if (isPlanConsultAllowed(resultMap)
            && !accrPlanConsult.getItems().isEmpty()) {
            accrConsultType.addItem(consult_NotProgrammed);
        }
        
        // define initial consult type only if it has consult type options
        if (accrConsultType.getItems() != null
            && !accrConsultType.getItems().isEmpty()) {

            accrConsultType.setSelectedItem(
                getInitialConsultType(
                    resultMap,
                    accrConsultType.getItems()
                )
            );
            
        }


        // Mini-app specific initializations:
        // this avoids codename one team to do actions over the IwFormAddEvolution11.
        // For simplify the tests executions.

        // Mini-app init 1:
        // initialise the programmed consult visual component selector = the first programmed consult
        ArrayList<PlannedConsult> plnConsults = getPlannedConsults(resultMap);
        if (!plnConsults.isEmpty()) {
            accrPlanConsult.setSelectedItemIndex(0);
        }

        // Mini-app init 2:
        // initialize templates combo-box = template (id = 424 - the bigger one)
        // the fake data the Mini-app uses will always provide the template 424 inside the templates list
        for (Template template : templates) {
            if (template.id == 424) {
                cmbTemplate.setSelectedItem(template);
                cmbTemplate.getPostSelectionAction().run();
            }
        }

    }
    
    private boolean isConsultEvaluationAllowed(Map<String, MobRecordset> map) {
        
        MobRecordset rsInitInfo = map.get("rsAddEvolutionInitInfo");

        String isEvaluationAllowed = "1";
         
        if (rsInitInfo != null) {
            for (MobRow r : rsInitInfo.rows) {
                MobField f = r.field("IS_EVOL_EVALUATION_ALLOWED");
                if (f != null) {
                    isEvaluationAllowed =
                        r.field(
                          "IS_EVOL_EVALUATION_ALLOWED"
                        ).getValue();
                }
                else {
                    isEvaluationAllowed = "1"; // default.
                }
            }
        }
        
        return "1".equals(isEvaluationAllowed);
        
    }    
    
    private boolean isConsultInfoRegAllowed(Map<String, MobRecordset> map) {
        
        MobRecordset rsInitInfo = map.get("rsAddEvolutionInitInfo");

        String isInfoRegAllowed = "1";
         
        if (rsInitInfo != null) {
            for (MobRow r : rsInitInfo.rows) {
                MobField f = r.field("IS_EVOL_INFO_REG_ALLOWED");
                if (f != null) {
                    isInfoRegAllowed =
                        r.field(
                          "IS_EVOL_INFO_REG_ALLOWED"
                        ).getValue();
                }
                else {
                    isInfoRegAllowed = "1"; // default.
                }
            }
        }
        
        return "1".equals(isInfoRegAllowed);
        
    }
    
    private boolean isConsultStandAloneAllowed(Map<String, MobRecordset> map) {

        MobRecordset rsInitInfo = map.get("rsAddEvolutionInitInfo");

        String isStandaloneAllowed = "1";
         
        if (rsInitInfo != null) {
            for (MobRow r : rsInitInfo.rows) {
                MobField f = r.field("IS_EVOL_STANDALONE_ALLOWED");
                if (f != null) {
                    isStandaloneAllowed =
                        r.field(
                          "IS_EVOL_STANDALONE_ALLOWED"
                        ).getValue();
                }
                else {
                    isStandaloneAllowed = "1"; // default.
                }
            }
        }
        
        return "1".equals(isStandaloneAllowed);

    }
    
    private boolean isProgConsultAllowed(Map<String, MobRecordset> map) {
        
        MobRecordset rsInitInfo = map.get("rsAddEvolutionInitInfo");

        String isProgConsultAllowed = "1";
         
        if (rsInitInfo != null) {
            for (MobRow r : rsInitInfo.rows) {
                MobField f = r.field("IS_EVOL_PROGRAMMED_CONSULT_ALLOWED");
                if (f != null) {
                    isProgConsultAllowed =
                        r.field(
                          "IS_EVOL_PROGRAMMED_CONSULT_ALLOWED"
                        ).getValue();
                }
                else {
                    isProgConsultAllowed = "1"; // default.
                }
            }
        }
        
        return "1".equals(isProgConsultAllowed);
        
    }    

    private boolean isPlanConsultAllowed(Map<String, MobRecordset> map) {
        
        MobRecordset rsInitInfo = map.get("rsAddEvolutionInitInfo");

        String isPlanConsultAllowed = "1";
         
        if (rsInitInfo != null) {
            for (MobRow r : rsInitInfo.rows) {
                MobField f = r.field("IS_EVOL_PLANNED_CONSULT_ALLOWED");
                if (f != null) {
                    isPlanConsultAllowed =
                        r.field(
                          "IS_EVOL_PLANNED_CONSULT_ALLOWED"
                        ).getValue();
                }
                else {
                    isPlanConsultAllowed = "1"; // default.
                }
            }
        }
        
        return "1".equals(isPlanConsultAllowed);
        
    }    

    private boolean isMaxOneConsultSelection(Map<String, MobRecordset> map) {
        
        MobRecordset rsInitInfo = map.get("rsAddEvolutionInitInfo");

        String isEnableMultipleSelec = "1";
         
        if (rsInitInfo != null) {
            for (MobRow r : rsInitInfo.rows) {
                MobField f = r.field("IS_ENABLE_MULTIPLE_CONSULT_SELECTION");
                if (f != null) {
                    isEnableMultipleSelec =
                        r.field(
                          "IS_ENABLE_MULTIPLE_CONSULT_SELECTION"
                        ).getValue();
                }
                else {
                    isEnableMultipleSelec = "1"; // default.
                }
            }
        }
        
        // Becareful - here must be negative logic
        // value must be compared to zero.
        return "0".equals(isEnableMultipleSelec);
            
    }
    
    private ConsultType getInitialConsultType(
            Map<String, MobRecordset> map,
            ArrayList<ConsultType> allowedConsultTypes) {
        
//        ConsultType[] types
//                = new ConsultType[]{
//                consult_Evaluation,
//                consult_InfoReg,
//                consult_NotProgrammed,
//                consult_Programmed,
//                consult_StandAlone
//        };
//        
        ConsultType resp = null;
        
        MobRecordset rsInitInfo = map.get("rsAddEvolutionInitInfo");
        
        if (rsInitInfo != null) {
            
            int initialEvolType = -1;
            
            for (MobRow r : rsInitInfo.rows) {
                initialEvolType = (int) r.field("INIT_EVOL_TYPE").objValue();
            }
            
            for (ConsultType t : allowedConsultTypes) {
                if (t.id == initialEvolType) {
                    resp = t;
                    break;
                }
            }
            
        }
        

        if (resp == null && allowedConsultTypes.contains(consult_InfoReg)) {
            resp = consult_InfoReg;
        }
        
        if (resp == null && !allowedConsultTypes.isEmpty()) {
            resp = allowedConsultTypes.get(0);
        }
        
        return resp;
        
    }
    
    private ArrayList<ProgrammedConsult> getInitProgConsultSelected(
            Map<String,MobRecordset> map,
            ArrayList<ProgrammedConsult> allProgConsults ) {
        
        MobRecordset rsInitInfo = map.get("rsAddEvolutionInitInfo");

        ArrayList<ProgrammedConsult> resp = new ArrayList<ProgrammedConsult>();
        
        long initialProgConsultId = -1L;

        for (MobRow r : rsInitInfo.rows) {
            initialProgConsultId = 
                (long) r.field("INIT_PROG_CONSULT_ID").objValue();
        }

        for (ProgrammedConsult consult : allProgConsults) {
            if (consult.id == initialProgConsultId) {
                resp.add(consult);
                break;
            }
        }
                
        return resp;
        
    }

    private ArrayList<PlannedConsult> getInitPlanConsultSelected(
            Map<String,MobRecordset> map,
            ArrayList<PlannedConsult> allPlanConsults ) {
        
        MobRecordset rsInitInfo = map.get("rsAddEvolutionInitInfo");

        ArrayList<PlannedConsult> resp = new ArrayList<PlannedConsult>();
        
        long initialPlanConsultId = -1L;

        for (MobRow r : rsInitInfo.rows) {
            initialPlanConsultId =
                (long) r.field("INIT_PLAN_CONSULT_ID").objValue();
        }

        for (PlannedConsult consult : allPlanConsults) {
            if (consult.id == initialPlanConsultId) {
                resp.add(consult);
                break;
            }
        }
                
        return resp;
        
    }
    
    private ArrayList<Speciality> getSpecialities(
            Map<String,MobRecordset> resultMap) {
        
        ArrayList<Speciality> list = new ArrayList<Speciality>();
        long id;
        String name;
        MobRecordset rsTemplates = resultMap.get("rsSpecialities");
        for (MobRow r : rsTemplates.rows) {
            id = (Long) r.field("ID").objValue();
            name = r.field("SCSPECIALITYNAME").getValue();
            list.add(new Speciality(id,name));
        }
        return list;
        
    }
    
    private ArrayList<Template> getTemplates(
            Map<String,MobRecordset> resultMap) {
        
        //Example of Fields returned from Server
        //        IDTEMPLATE(java.lang.Long):   501
        //        ID(java.lang.Long):   524
        //        IDTEXT(java.lang.Long):   635549
        //        IDTEXTINSTRUCT(java.lang.Long):	
        //        TEMPLATETYPE(java.lang.Integer):  0
        //        TEMPLATENAME(java.lang.String): HC - Escore ABEMID - Adulto
        //        TABLENAME(java.lang.String):	*TDAVAD_SCORE
        //        XMLPERSISTENCETYPE(java.lang.Integer):    2
        //        IDENTERPRISE(java.lang.Long):
        //        FORMTYPE

        ArrayList<Template> list = new ArrayList<Template>();
        long id;
        String name;
        long idText;
        String tableName;
        Integer persistType;
        Integer formType;
        MobRecordset rsTemplates = resultMap.get("rsTemplates");
        for (MobRow r : rsTemplates.rows) {
            id = (Long) r.field("IDTEMPLATE").objValue();
            name = r.field("TEMPLATENAME").getValue();
            idText = (Long) r.field("IDTEXT").objValue();
            tableName = r.field("TABLENAME").getValue();
            tableName = (tableName == null)? "" : tableName;
            persistType = (Integer) r.field("XMLPERSISTENCETYPE").objValue();
            persistType = (persistType == null)? -1 : persistType;
            MobField fFormType = r.field("FORMTYPE");
            formType = -1;
            if (fFormType != null) {
                formType = (Integer) r.field("FORMTYPE").objValue();
                formType = (formType == null)? -1 : formType;
            }
            list.add(
                new Template(
                    id,
                    name,
                    idText,
                    tableName,
                    persistType,
                    formType
                )
            );
        }
        return list;
    }
    
    private ArrayList<ProgrammedConsult> getProgrammedConsults(
            Map<String,MobRecordset> resultMap) {
        
        ArrayList<ProgrammedConsult> list = new ArrayList<ProgrammedConsult>();
        long id;
        String desc;
        Date programmedStart;
        Long idCampaignItem;
        MobRecordset rsProgrammedConsults = resultMap.get("rsProgrammedConsults");
        for (MobRow r : rsProgrammedConsults.rows) {
            id = (Long) r.field("ID").objValue();
            desc = r.field("PROGRAMMEDSTARTSTRING").getValue();
            programmedStart = (Date) r.field("PROGRAMMEDSTART").objValue();
            idCampaignItem = (Long) r.field("IDCAMPAIGNITEM").objValue();
            list.add(
                new ProgrammedConsult(
                        id,
                        desc,
                        programmedStart,
                        (idCampaignItem == null)? -1L : idCampaignItem));
        }
        return list;
        
//// TESTE TESTE
//        ArrayList<ProgrammedConsult> fakeList =
//            new ArrayList<ProgrammedConsult>();
//        for (int i = 0; i < 20; i++) {
//            fakeList.add(
//                new ProgrammedConsult(
//                    i,
//                    "Teste de consulta programada" + i,
//                    new Date(),
//                    -1L
//                )
//            );
//                
//        }
//        return fakeList;
        
    }
    private ArrayList<ProgrammedConsult> getProgrammedConsults_mock(
            Map<String,MobRecordset> resultMap) {
        
        ArrayList<ProgrammedConsult> list = new ArrayList<ProgrammedConsult>();
        for (int i = 0; i < 10; i++) {
            list.add(new ProgrammedConsult(i, "Consulta Programada " + i, new Date(), -1L));
        }
        return list;
    }
    
    private ArrayList<PlannedConsult> getPlannedConsults(
            Map<String,MobRecordset> resultMap) {
        
        ArrayList<PlannedConsult> list = new ArrayList<PlannedConsult>();
        long id;
        String desc;
        Long idCampaignItem;
        MobRecordset rsPlannedConsults = resultMap.get("rsPlannedConsults");
        for (MobRow r : rsPlannedConsults.rows) {
            id = (Long) r.field("ID").objValue();
            desc = r.field("AGENDASTARTDATESTRING").getValue();
            idCampaignItem = (Long) r.field("idCampaignItem").objValue();
            list.add(
                new PlannedConsult(
                    id,
                    desc,
                    (idCampaignItem == null)? -1L : idCampaignItem));
        }
        return list;
    }
    private ArrayList<PlannedConsult> getPlannedConsults_mock(
            Map<String,MobRecordset> resultMap) {
        
        ArrayList<PlannedConsult> list = new ArrayList<PlannedConsult>();
        for (int i = 0; i < 10; i++) {
            list.add(new PlannedConsult(i, "Consulta Planejada " + i, -1L));
        }
        return list;
        
    }
    
    private ArrayList<AdditionalReason> getAdditionalReasons(
            Map<String,MobRecordset> resultMap) {
        
        ArrayList<AdditionalReason> list = new ArrayList<AdditionalReason>();
        int id;
        String desc;
        MobRecordset rsTemplates = resultMap.get("rsAdditionalReasons");
        for (MobRow r : rsTemplates.rows) {
            id = (Integer) r.field("ID").objValue();
            desc = r.field("DESCRIPTION").getValue();
            list.add(new AdditionalReason(id, desc));
        }
        return list;
    }

////////////////////////////////////////////////////////////////////////////////    
//   BEGIN - Creates all aggregate visual components (containers)
////////////////////////////////////////////////////////////////////////////////    
    
    class MyContainer extends Container {

        Container current;
        FlowLayout flowLayout = new FlowLayout();
        
        
        public MyContainer() {
            FlowLayout layout = new FlowLayout();
            layout.setValign(TOP);
        }
        
        public Container setCurrent(Container c) {
            Container old = current;
            if (current != null) {
                removeComponent(current);
            }
            addComponent(c);
            current = c;
            return old;
        }
    }

////////////////////////////////////////////////////////////////////////////////    
//   END - Creates all aggregate visual components (containers)
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////    
//   END - Creates all elementary visual components
////////////////////////////////////////////////////////////////////////////////
    
    private Button createBtnEditTemplate() {

        Button btn = 
            new Button(Brain.getInstance().getImage(Brain.IMAGE_EDIT_ENABLED));
        
        btn.setDisabledIcon(
                Brain.getInstance().getImage(Brain.IMAGE_EDIT_DISABLED));
        
        btn.setUIID("IwButtonStyle");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new IwFormAddEvolution21(idAdmission, null).show();
            }
        });
        return btn;
    }
    
    
    private Label createLblFeedback() {
        Label lbl = new Label("");
        lbl.getStyle().setAlignment(CENTER);
        return lbl;
    }

    private String adjustEventDate(String sDate) {
        
        if (sDate == null || "".equals(sDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(new Date());
        }
        else {
            return sDate;
        }
    }

    private String adjustEventTimeIni(String sTime) {
        
        if (sTime == null || "".equals(sTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String resp = sdf.format(this.startForm);
            return ("00:00".equals(resp))? "01:00" : resp; // avoid daylight saving
        }
        else {
            return sTime;
        }
        
    }

    private String adjustEventTimeEnd(String sTime) {
        
        if (sTime == null || "".equals(sTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String resp = sdf.format(new Date());
            return ("00:00".equals(resp))? "01:00" : resp; // avoid daylight saving
        }
        else {
            return sTime;
        } 

    }
        
    
    class MySaveActionListener implements ActionListener {
        
        boolean successful = false; 
        
        public boolean isSuccessful() {
            return this.successful;
        } 
        
        @Override
        public void actionPerformed(ActionEvent evt) {
            saveOnline();
        }
        
        
        private void saveOnline() {

            Button btn = btnSave;

            lblFeedback.setText("");
            btn.setEnabled(false);

            ////////////////////////////////////////////////////////////////
            //// Step1: Ensure Template selected
            ////////////////////////////////////////////////////////////////
            if (((Template)cmbTemplate.getSelectedItem()).id == -1L) {
                String msg = "Nenhum Template selecionado";
                Dialog.show("Erro", msg, "OK", null);
                btn.setEnabled(true);
                return;
            }

            // Changed Order  of some consistences checks
            // performance issues.
            // Tiago requested.
            // Step 4 was changed for beginning position.
            ////////////////////////////////////////////////////////////////
            //// Step 4: Only for Programmed and Planned Appointments
            //// Ensures at least one appointment selected
            ////////////////////////////////////////////////////////////////
            if (((ConsultType) accrConsultType.getSelectedItem()).id == consult_Programmed.id) {
                if (accrProgConsult.getSelectedItems().isEmpty()) {
                    String msg = "Selecione ao menos uma consulta programada";
                    Dialog.show("Erro", msg, "OK", null);
                    btn.setEnabled(true);
                    return; 
                } 
            }
            if (((ConsultType) accrConsultType.getSelectedItem()).id == consult_NotProgrammed.id) {
                if (accrPlanConsult.getSelectedItems().isEmpty()) {
                    String msg = "Selecione ao menos uma consulta planejada";
                    Dialog.show("Erro", msg, "OK", null);
                    btn.setEnabled(true);
                    return; 
                } 
            }
            if (((ConsultType) accrConsultType.getSelectedItem()).id == consult_StandAlone.id) {
                if (accrVisitReason.getSelectedItem() == null) {
                    String msg = "Selecione motivo da Consulta avulsa";
                    Dialog.show("Erro", msg, "OK", null);
                    btn.setEnabled(true);
                    return; 
                } 
            }

            // Tiago resquests change order of this signature check
            ////////////////////////////////////////////////////////////////
            //// OLD Step 5: Check Eletronic signature
            ////////////////////////////////////////////////////////////////
//            IwDlgSignature dlgSignature = new IwDlgSignature();
//            dlgSignature.show();
//            int userAction = dlgSignature.getUserAction();
//            if (userAction == IwDlgSignature.USER_ACTION_CANCEL) {
//                btn.setEnabled(true);
//                return;
//            }
//            if (userAction == IwDlgSignature.USER_ACTION_CONFIRM) {
//                if (dlgSignature.isPasswordEmpty()) {
//                    btn.setEnabled(true);
//                    return;
//                }
//                if (!dlgSignature.isPasswordChecked()) {
//                    Dialog.show("Erro", dlgSignature.getInfo(), "OK", null);
//                    btn.setEnabled(true);
//                    return;
//                }
//            }

            ////////////////////////////////////////////////////////////////
            //// Step2: Run Consistence Function if it exists.
            ////////////////////////////////////////////////////////////////
//            Map<String,String> htmlVariables;
//            boolean hasConsistFunction =
//                ((IwWebBrowser1)webBrowser).templateHasConsistFunc();
//
//            // Now always force execution of Consistence
//            //  functions execution service now automatically
//            // execute attribute types verification.
//            // So, it must be executed, even if template doesn't have
//            // Consist function assigned to it
//            if (false) {  //(!hasConsistFunction) {
//                htmlVariables =
//                    ((IwWebBrowser1)webBrowser).getHtmlVariableValues();
//            }
//            else {
//
//                try{
//
//                    htmlVariables =
//                        ((IwWebBrowser1)webBrowser).execConsistFunction();
//
//                    // Here was coded equivalent logic present in
//                    //IwHtmlEditorPaneNative class (Client Java)
//                    boolean bResp;
//                    if (htmlVariables != null) {
//
//                        String strConsistResultValue =
//                            htmlVariables
//                                .get(IwWebBrowser1.CTRL_VAR_CONSIST_RESULT);
//
//                        if (strConsistResultValue != null) {
//                            if ("true".equals(strConsistResultValue)) {
//                                bResp = true;
//                            }
//                            else {
//                                bResp = false;
//                            }
//                        }
//                        else {
//                            bResp = true;
//                        }
//
//                        // Set possible changes made by Consist Function
//                        ((IwWebBrowser1)webBrowser)
//                            .setHtmlVariablesValues(htmlVariables);
//
//                    }
//                    else {
//                        bResp = false;
//                    }
//
//                    if (!bResp) {
//
//                        String msgError =
//                            htmlVariables
//                                .get(IwWebBrowser1.CTRL_VAR_DISPLAY_ERROR);
//                        if (msgError != null) {
//                            Dialog.show("Erro", msgError, "OK", null);
//                        }
//                        else {
//                            String msg = "Consistência acusou erro";
//                            Dialog.show("Erro", msg, "OK", null);
//                        }
//
//                        btn.setEnabled(true);
//                        return; // abbort - Consist function returns false.
//
//                    }
//
//                }
//                catch (Exception e) {
//                    String msg = "Falha Comunicação.Tente novamente";
//                    Dialog.show("Erro", msg, "OK", null);
//                    btn.setEnabled(true);
//                    return; // abbort save process here - Consistence function call failed.
//                }
//
//            }

            // Step 3: date Consistences
            // Consists whether StartDate is a future date.
            // Also consists whether endDate is before StartDate
//            Date start_Date;
//            Date end_Date;
//            long start_Time;
//            long end_Time;
//            try {
//                if (!pckStartDate.isVisible()) {
//                    // Obs: When pnlPeriod isn't present indicates that
//                    // template have eventdate variable.
//                    // So, the start date and end date data are defined
//                    // using eventdate variable value.
//                    // Unfortunatelly IWCare Template doesn't define End-Date Variable.
//                    // Instead it defines only one variable "evendate"  and two others variable
//                    // called eventtime_ini and eventtime_end.
//                    // So, this obligates the consult begin and finish in the same day.
//                    String sEventDate    = htmlVariables.get("iwvar_eventdate");
//                    String sEventTimeIni = htmlVariables.get("iwvar_eventtime_ini");
//                    String sEventTimeEnd = htmlVariables.get("iwvar_eventtime_end");
//                    if (sEventTimeEnd == null && sEventTimeIni != null) {
//                        sEventTimeEnd = sEventTimeIni;
//                    }
//                    else if (sEventTimeEnd == null && sEventTimeIni == null) {
//                        sEventTimeIni = sEventTimeEnd = "01:00";
//                    }
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//                    String strStartDate = sEventDate + " " + sEventTimeIni;
//                    String strEndDate   = sEventDate + " " + sEventTimeEnd;
//                    start_Date = sdf.parse(strStartDate);
//                    end_Date   = sdf.parse(strEndDate);
//                }
//                else {
//                    start_Date = pckStartDate.getDateTime();
//                    end_Date   = pckEndDate.getDateTime();
//                    //start_Time = pckStartTime.getTime() * 1000;
//                    //end_Time = pckEndTime.getTime() * 1000;
//
//                    //long start = start_Date.getTime() + start_Time;
//                    //long end = end_Date.getTime() + end_Time;
//
//                    //start_Date = new Date(start);
//                    //end_Date = new Date(end);
//                }
//
//
//                long ONE_HOUR = 1000 * 60 * 60; // One Hour (miliseconds)
//                long FIVE_MINUTES = 1000 * 60 * 5; // Five Minutes (Miliseconds)
//                Date now = new Date();
//                if (start_Date.getTime() > now.getTime() + ONE_HOUR + FIVE_MINUTES) {
//                    throw new Exception("Data futura");
//                }
//                if (start_Date.getTime() > end_Date.getTime()) {
//                    throw new Exception("Data Fim anterior ao inicio");
//                }
//
//            }
//            catch (ParseException pe) {
//                String msg = pe.getMessage();
//                Dialog.show("Erro", msg, "OK", null);
//                btn.setEnabled(true);
//                return;
//            }
//            catch (Exception ex) {
//                String msg = ex.getMessage();
//                Dialog.show("Erro", msg, "OK", null);
//                btn.setEnabled(true);
//                return;
//            }

            ////////////////////////////////////////////////////////////////
            //// Step:6 Call AddEvolution Service
            ////////////////////////////////////////////////////////////////
            IwServiceCallerInterface sCaller =
                    Brain.getInstance().getIwServiceCaller();
            try {

                // get data from visual components
                int consultType = ((ConsultType) accrConsultType.getSelectedItem()).id;
                Date startDate = pckStartDate.getDateTime();
                Date endDate = pckEndDate.getDateTime();
                long idTemplate = ((Template) cmbTemplate.getSelectedItem()).id;
                long idText = ((Template) cmbTemplate.getSelectedItem()).idText;
                String tableName = ((Template) cmbTemplate.getSelectedItem()).tableName;
                int persistType  = ((Template) cmbTemplate.getSelectedItem()).persistType;
                int skipGenConsult = (chkSkipGenConsult.isSelected())? 1 : 0;
                long idSpeciality = ((Speciality) accrSpecialities.getSelectedItem()).id;
                String specialityName = ((Speciality) accrSpecialities.getSelectedItem()).name;

                int additionalReason = -1;
                String additionalReasonTx = "";
                if (accrVisitReason.getSelectedItem() != null) {
                    additionalReason = ((AdditionalReason) accrVisitReason.getSelectedItem()).id;
                    additionalReasonTx = ((AdditionalReason) accrVisitReason.getSelectedItem()).description;
                }

                int canceledConsult1 = (chkCanceledConsult1.isSelected())? 1 : 0;
                int canceledConsult2 = (chkCanceledConsult2.isSelected())? 1 : 0;
                
                HashMap<String,String> hmVariables = ((IwWebBrowser1)webBrowser).getHtmlVariableValues();
                HashMap<String,String> scaledImagesMap = ((IwWebBrowser1)webBrowser).getScaledImagePathsMap();
                HashMap<String,String> hmImgVarUrls = new HashMap<String,String>();
                for (String imgVarName : ((IwWebBrowser1)webBrowser).getImageVarNames()) {
                    // save image local Urls. To be  used ahead to clean local references
                    hmImgVarUrls.put(imgVarName, hmVariables.get(imgVarName));
                    // substitutes img localUrls path using path to scaled images
                    hmVariables.put(imgVarName, scaledImagesMap.get(imgVarName));
                }

                long idCampaignItem = getIdCampaignItem(consultType);
                Date programmedStart = getProgrammedStart();
                ArrayList<Long> listOfProgConsultIds =
                        getListOfProgConsultsIds();
                ArrayList<Long> listOfPlanConsultIds =
                        getListOfPlanConsultsIds();

                ArrayList<String> imageVarNamesList = 
                    ((IwWebBrowser1) webBrowser).getImageVarNames();
                ArrayList<String> pdfVarNamesList = 
                    ((IwWebBrowser1) webBrowser).getPdfVarNames();


                Map<String, MobRecordset> resultMap =
                    sCaller.addEvolution(
                        consultType,    
                        startDate,
                        endDate,
                        idAdmission,
                        idTemplate,
                        idText,
                        tableName,
                        persistType,
                        skipGenConsult,
                        idSpeciality,
                        specialityName,
                        additionalReason,
                        additionalReasonTx,
                        canceledConsult1,
                        canceledConsult2,
                        listOfProgConsultIds, programmedStart,
                        listOfPlanConsultIds,
                        idCampaignItem,
                        idCapAdmProfCheckin,
                        hmVariables,
                        imageVarNamesList,
                        pdfVarNamesList);

                MobRecordset rsAddedEvolution = resultMap.get("rsAddedEvolution");
                String strIdEvol = rsAddedEvolution.rows.get(0).fields.get(0).getValue();
                final String msg = "Evol:" + strIdEvol + " inserida.";

                //Tiago requested change "Toast_Message" for a "Modal_ Dialog".
//                try {
//                Toast.makeText(
//                    Brain.getInstance().getContext(),
//                    msg,
//                    Toast.LENGTH_LONG).show();
//                }
//                catch (Exception e) {/** do nothing **/}
                Dialog.show("Info", msg, "OK", null);

                Display.getInstance().callSerially(new Runnable() {
                    @Override
                    public void run() {
                        lblFeedback.setText(msg);
                        btnSave.setEnabled(false);
                        btnSave.setVisible(false);
                    }
                });
                
                for (String imgLocalUrl : hmImgVarUrls.values()) {
                    ((IwWebBrowser1)webBrowser)
                        .removeLocalImageFilePath(imgLocalUrl);
                }

                getBackCommand().actionPerformed(new ActionEvent(btn));

            } 
            catch (IwCommException ex) {

                btn.setEnabled(true);

                String _msg = "";
                for (MobRow r : ex.getRsError().rows) {
                    if (!r.field("message").getValue().startsWith("HTTP")) {
                        _msg += r.field("message").getValue() + "\n";
                    }
                }
                String msg1 = _msg;
                
                String dlgErrTitle = "Error/Info";
                msg1 += "\n\nEvolução salva com sucesso,\n mas assinatura eletronica falhou.\nVocê poderá realizar a assinatura posteriormente.";
                
                final String dlgErrTitle1 = dlgErrTitle;
                final String msg2 = msg1;
                //Toast.makeText(self(), msg, 10);
                Display.getInstance().callSerially(new Runnable() {
                    @Override
                    public void run() {
                        lblFeedback.setText(msg2);
                        Dialog.show(dlgErrTitle1, msg2, "OK", null);
                    }
                });
                
            }
            catch (IOException ioe) {

                btn.setEnabled(true);

                final String msg = ioe.getMessage();

                Display.getInstance().callSerially(new Runnable() {
                    @Override
                    public void run() {
                        lblFeedback.setText(msg);
                        Dialog.show("Erro", msg, "OK", null);
                    }
                });
                
                return;

            }
            catch (Exception e) {

                btn.setEnabled(true);

                final String msg = e.getMessage();

                Display.getInstance().callSerially(new Runnable() {
                    @Override
                    public void run() {
                        lblFeedback.setText(msg);
                        Dialog.show("Erro", msg, "OK", null);
                    }
                });
                
                return;

            }
            
            this.successful = true;

        }        

    }

    private Button createBtnSave_Sync() {
        
        final Button btn = 
            new IwButton(Brain.getInstance().getImage(Brain.IMAGE_SEND_ENABLED));
        btn.setDisabledIcon(
                Brain.getInstance().getImage(Brain.IMAGE_SEND_DISABLED)
        );
        
        btn.addActionListener(new MySaveActionListener());
        
        btn.setEnabled(true);
        return btn;
        
    }
    
    protected ArrayList<Long> getListOfProgConsultsIds() {
        
        ArrayList<Long> resp = new ArrayList<Long>();

        ConsultType cType = accrConsultType.getSelectedItem(); 
        if (cType == null
            || cType.id == consult_Programmed.id) {

            for (ProgrammedConsult progCons : accrProgConsult.getSelectedItems()) {
                resp.add(progCons.getId());
            } 
            
        }
        
        return resp;
        
    }

    protected ArrayList<Long> getListOfPlanConsultsIds() {
        
        ArrayList<Long> resp = new ArrayList<Long>();
        
        ConsultType cType = accrConsultType.getSelectedItem(); 
        if (cType == null
            || cType.id == consult_NotProgrammed.id) {

            for (PlannedConsult planCons : accrPlanConsult.getSelectedItems()) {
                resp.add(planCons.getId());
            }
            
        }
        
        return resp;
        
    }
    
    
    protected long getIdCampaignItem(int consultType) {

        if (consultType == consult_Programmed.id) {
            if (!accrProgConsult.getSelectedItems().isEmpty()) {
                return
                    accrProgConsult
                        .getSelectedItems().get(0).idCampaignItem;
            }
        }
        else if (consultType == consult_NotProgrammed.id) {
            if (!accrPlanConsult.getSelectedItems().isEmpty()) {
                return
                    accrPlanConsult
                        .getSelectedItems().get(0).idCampaignItem;
            }
        }

        return -1L;

    }
    
    protected Date getProgrammedStart() {
        // This strange method exists to attend addEvolution Iw-Server service.
        // Only for first programmed consult
        // it's necessary to pass that information.
        
        if (accrConsultType.getSelectedItem() != consult_Programmed) {
            return null;
        }
        
        if (accrProgConsult.getSelectedItems().isEmpty()) {
            return null;
        }
        else {
            return accrProgConsult.getSelectedItems().get(0).programmedStart;
        }
    }
    
    
    private IwAccrComboBox<Speciality> createAccrSpecialities() {
        IwAccrComboBox accr = new IwAccrComboBox(
                getIwTranslation(TT_CHOOSE_SPECIALITY),
                null,
                new ArrayList<Speciality>()
        );
        return accr;
    }
    
    final String TT_NO_SELECTION_TEMPLATE_TEXT = "No Template Selected ...";
    private IwSelectionButton createCmbTemplate1() {

        final IwSelectionButton btn = 
            new IwSelectionButton(
                TT_NO_SELECTION_TEMPLATE_TEXT,
                getIwTranslation(TT_BACK),
                this
            );
        
        Runnable postSelectionThread = new Runnable() {
            
            @Override
            public void run() {
                
                try{
                    Toast.makeText(
                        Brain.getInstance().getContext(),
                        "wait til Template be initialized...",
                        Toast.LENGTH_LONG
                    ).show();        
                }
                catch (Exception e) {/** do nothing **/}                        
                        
                btnEditTemplate.setEnabled(false);
                Template template = (Template) btn.getSelectedItem();

                getTemplateByIdText(
                    template.id,
                    template.idText,
                    idAdmission
                );

            }
            private void getTemplateByIdText(
                    final long idTemplate,
                    final long idText,
                    final long idAdmission) {

                IwServiceCallerInterface caller =
                        Brain.getInstance().getIwServiceCaller();

                IwHttpRequesterCallBack<Map<String,MobRecordset>> callback =
                        new IwHttpRequesterCallBack<Map<String,MobRecordset>>() {

                    @Override
                    public void onFailure(MobRecordsetError rsError) {
                        Dialog.show("Alert", rsError.getTranslation(), "OK", null);
                        cmbTemplate.setSelectedItem(null);
                    }

                    @Override
                    public void onSuccess(Map<String,MobRecordset> resultMap) {
                        
                        //Dialog dlgLoop = new InfiniteProgress().showInifiniteBlocking();
                        
                        // Defines visibility of StartDate / EndDate components.
                        MobRecordset rsTemplateInfo = resultMap.get("rsTemplateInfo");
                        String varNamesList = rsTemplateInfo.rows.get(0).field("VAR_NAMES_LIST").getValue();
                        if (varNamesList.indexOf("iwvar_eventdate") == -1) {
                            
                            lblSTartDate.setVisible(true);
                            lblEndDate.setVisible(true);
                            
                            pckStartDate.setVisible(true);
                            pckEndDate.setVisible(true);
                
                            //pckStartTime.setVisible(true);
                            //pckEndTime.setVisible(true);
                            
                            // I wrote these 4 lines below because curiously
                            // the Picker components even not visible
                            // they continue responding touch events
                            pckStartDate.setEnabled(true);
                            pckEndDate.setEnabled(true);
                            //pckStartTime.setEnabled(true);
                            //pckEndTime.setEnabled(true);
                            
                            ctnPeriod.setHidden(false);
                            System.out.println("Set Period Hidden = false");
                            
                        }
                        else {
                            
                            lblSTartDate.setVisible(false);
                            lblEndDate.setVisible(false);
                            
                            pckStartDate.setVisible(false);
                            pckEndDate.setVisible(false);
                            
                            //pckStartTime.setVisible(false);
                            //pckEndTime.setVisible(false);
                            
                            // I wrote these 4 lines below because curiously
                            // the Picker components even not visible
                            // they continue responding touch events
                            pckStartDate.setEnabled(false);
                            pckEndDate.setEnabled(false);
                            //pckStartTime.setEnabled(false);
                            //pckEndTime.setEnabled(false);
                            
                            ctnPeriod.setHidden(true);
                            System.out.println("Set Period Hidden = true");
                            
                        }
                        
                        getContentPane().animate();                        
                        //getContentPane().revalidate();
                        
                        //dlgLoop.dispose();
                        
                        ((IwWebBrowser1) webBrowser)
                            .setIwTemplate(
                                resultMap,
                                Brain
                                    .getInstance()
                                        .getIwServiceCaller()
                                            .getBaseURL()
                            );
                        
                        btnEditTemplate.setEnabled(true);
                                                
                    }
                    
                };

                if (((Template)cmbTemplate.getSelectedItem()).id != -1) {
                    caller.getIwTemplateByIdText(
                            idTemplate,
                            idText,
                            idAdmission,
                            callback
                    );
                    try {
                        Toast.makeText(
                                Brain.getInstance().getContext(),
                                "Getting Template ...",
                                Toast.LENGTH_SHORT);
                    }
                    catch (Exception e) {}                    
                }
                else {
                    webBrowser.setPage("", null);
                }
                
            }            
        };
        btn.setPostSelectionAction(postSelectionThread);
        
        // Initially disabled until Templates list becomes ready
        btn.setEnabled(false); 
        
        return btn;
    }
    
        
    class ConsultType {
        
        int id;
        String name;
        
        public ConsultType(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        @Override
        public String toString() {
            return name;            
        }
    }
    
    private IwAccrMultiSelection<ProgrammedConsult> createAccrProgConsult() {
        IwAccrMultiSelection<ProgrammedConsult> accr =
                new IwAccrMultiSelection<ProgrammedConsult>(
                    getIwTranslation(TT_CHOOSE_CONSULT_PROG),
                    null,
                    new ProgrammedConsult[]{}    
                );
        return accr;
    }

    private IwAccrMultiSelection<PlannedConsult> createAccrPlanConsult() {
        IwAccrMultiSelection<PlannedConsult> accr =
                new IwAccrMultiSelection<PlannedConsult>(
                    getIwTranslation(TT_CHOOSE_CONSULT_PLAN),
                    null,
                    new PlannedConsult[]{}    
                );
        return accr;
    }
    
    // ConsultType - Hard coded entity
    ConsultType consult_Evaluation =
            new ConsultType(0, getIwTranslation(TT_EVALUATION));
    ConsultType consult_StandAlone =
            new ConsultType(1, getIwTranslation(TT_STANDALONE));
    ConsultType consult_Programmed =
            new ConsultType(2, getIwTranslation(TT_PROGRAMMED));
    ConsultType consult_InfoReg =
            new ConsultType(3, getIwTranslation(TT_INFO_REG));
    ConsultType consult_NotProgrammed =
            new ConsultType(4, getIwTranslation(TT_NOT_PROGRAMMED));
    
    private IwAccrComboBox<ConsultType> createAccrConsultType() {
        
        IwAccrComboBox accr =
            new IwAccrComboBox(
                    getIwTranslation(TT_CHOOSE_CONSULT_TYPE),
                    null,
                    new ConsultType[] {}
            );
        
        accr.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent evt) {
                updateVisibleComps(evt);
            }

            private void updateVisibleComps(ActionEvent evt) {
                
                // Process event only if it comes from the right component.
                Component comp = evt.getComponent();
                if (comp instanceof IwAccrComboBox
                    && ((IwAccrComboBox) comp).getSelectedItem() != null
                    && ((IwAccrComboBox) comp).getSelectedItem() instanceof ConsultType) {
                    updateVisibleCompsImpl(
                        (ConsultType) ((IwAccrComboBox) comp).getSelectedItem()
                    );
                }
                
            }
            
            private void updateVisibleCompsImpl(ConsultType type) {
                
                // center is IwFormAddEvolution11.this.center
                center.setLayout(new BoxLayout((BoxLayout.Y_AXIS)));
                center.removeAll();
                                    
                switch (type.id) {
                    case 0:
                        center.addComponent(ctnPeriod);
                        center.addComponent(ctnTemplate);
                        center.addComponent(ctnSkipConsult);
                        center.addComponent(ctnSpeciality);
                        center.addComponent(ctnConsultType);
                        break;
                    case 1:
                        center.addComponent(ctnPeriod);
                        center.addComponent(ctnTemplate);
                        center.addComponent(ctnSkipConsult);
                        center.addComponent(ctnSpeciality);
                        center.addComponent(ctnConsultType);
                        center.addComponent(ctnVisitReason);
                        break;
                    case 2:
                        center.addComponent(ctnPeriod);
                        center.addComponent(ctnTemplate);
                        center.addComponent(ctnSkipConsult);
                        center.addComponent(ctnSpeciality);
                        center.addComponent(ctnConsultType);
                        center.addComponent(ctnProgConsults);
                        break;
                    case 3:
                        center.addComponent(ctnPeriod);
                        center.addComponent(ctnTemplate);
                        center.addComponent(ctnSkipConsult);
                        center.addComponent(ctnSpeciality);
                        center.addComponent(ctnConsultType);
                        break;
                    case 4:
                        center.addComponent(ctnPeriod);
                        center.addComponent(ctnTemplate);
                        center.addComponent(ctnSkipConsult);
                        center.addComponent(ctnSpeciality);
                        center.addComponent(ctnConsultType);
                        center.addComponent(ctnPlanConsults);
                        break;
                    default:
                        center.addComponent(ctnPeriod);
                        center.addComponent(ctnTemplate);
                        center.addComponent(ctnSkipConsult);
                        center.addComponent(ctnSpeciality);
                        center.addComponent(ctnConsultType);
                        break;
                }
                
                getContentPane().revalidate();
                //getContentPane().repaint();
                //getContentPane().animateLayoutFade(300, 0);
                
            }
            
        });
        
        return accr;
        
    }
    
    
    private Label createLblStartDate() {
        Label lbl = new Label("Início:"); 
        lbl.getStyle().setBgTransparency(0);        
        return lbl;
    }

    private Label createLblEndDate() {
        Label lbl = new Label("Fim:   "); 
        lbl.getStyle().setBgTransparency(0);        
        return lbl;
    }

    private IwDateTimePicker createPckStartDate() {
        IwDateTimePicker picker = new IwDateTimePicker(Font.createSystemFont(
            Font.FACE_PROPORTIONAL,
            Font.STYLE_PLAIN,
            Font.SIZE_LARGE),
                IwDateTimePicker.DATATYPE_DATETIME);
        return picker;
    }

    private IwDateTimePicker createPckEndDate() {
        IwDateTimePicker picker = new IwDateTimePicker(Font.createSystemFont(
            Font.FACE_PROPORTIONAL,
            Font.STYLE_PLAIN,
            Font.SIZE_LARGE),
                IwDateTimePicker.DATATYPE_DATETIME);
        
        return picker;
    }

    private CheckBox createChkSkipGenConsult() {
        CheckBox chk = new CheckBox();
        chk.getStyle().setBgTransparency(0);
        chk.setText(getIwTranslation(TT_RETROACTIVE_REGISTRY));
        return chk;
    }
    
    private IwAccrComboBox<AdditionalReason> createAccrVisitReason() {
        IwAccrComboBox accr = new IwAccrComboBox(
                getIwTranslation(TT_VISIT_REASON),
                null,
                new ArrayList<AdditionalReason>()
        );
        return accr;
    }
    
    private CheckBox createChkCanceledConsult1() {
        CheckBox chk = new CheckBox();
        chk.setText(getIwTranslation(TT_CANCELED_CONSULT));
        return chk;
    }
        
        
    private CheckBox createChkCanceledConsult2() {
        CheckBox chk = new CheckBox();
        chk.setText(getIwTranslation(TT_CANCELED_CONSULT));
        return chk;
    }
    
    IwFormAddEvolution11 self() {
        return this;
    } 
    
    protected IwWebBrowser1 createWebBrowser() {
                
        this.formEditTemplate =
            new IwFormAddEvolution21(
                    idAdmission,
                    new MySaveActionListener()
            );
        
        String backTitle = getIwTranslation(TT_BACK);
        Command backCommand = new Command(backTitle) {
            @Override
            public void actionPerformed(ActionEvent ev) {
                self().show();
            } 
        }; 
        this.formEditTemplate.setBackCommand(backCommand);        
        return this.formEditTemplate.getWebBrowser();
    }
     
}

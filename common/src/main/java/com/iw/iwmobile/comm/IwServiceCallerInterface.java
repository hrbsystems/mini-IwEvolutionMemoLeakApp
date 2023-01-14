/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.comm;

import com.iw.iwmobile.entities.AccessConfig;
import com.iw.iwmobile.entities.AccessToken;
import com.iw.iwmobile.entities.ScheduledVisit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hrugani
 */
public interface IwServiceCallerInterface {
    
    
    Map<String, MobRecordset> addEvolution(
            int consultType,
            Date startDate,
            Date endDate,
            long idAdmission,
            long idTemplate,
            long idText,
            String tableName,
            int persistType,
            int skipGenConsult,
            long idSpeciality,
            String specialityName,
            int additionalReason,
            String additionalReasonTx,
            int canceledConsult1,
            int canceledConsult2,
            ArrayList<Long> consultSelectedList1,
            Date programmedStart,
            ArrayList<Long> consultSelectedList2,
            long idCampaignItem,
            long idCapAdmProfCheckin,
            HashMap<String, String> hmVariables,
            ArrayList<String> imageVarNameList,
            ArrayList<String> pdfVarNameList)
                        throws IwCommException, IOException;

    /**
     *
     * @param callback
     *
     * If the request is successful call success callback method
     * Builds a response with following structure into standard Response HashMap:
     * 1) A MobRecordset called rsAdmissions with data about respective Admissions
     *
     */
    void clearCache(IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);

    /**
     *
     * Synchronous version of the same method above
     *
     * @return 
     * @throws com.iw.iwmobile.comm.IwCommException
     */
    Map<String, MobRecordset> clearCache() throws IwCommException;

    void execConsistPluggedFunctionAsync(
            String idText,
            String idTemplate,
            String idPatient,
            String idAdmission,
            HashMap<String, String> variableMap,
            IwHttpRequesterCallBack<Map<String, MobRecordset>> callback
    );

    /**
     *
     * Executes synchronously the Consist's Plugged Function defined into IW-Template.
     * It is executed only if the Consistence function exist.
     *
     *
     * @param idText
     * idText is a ID into GlbText database table that store IW_Template in XML format
     * It improves performance in server routines.
     *
     * @param idTemplate
     * Template ID into GlbTemplate database table.
     *
     * @param idPatient
     * Patient ID assigned to IW-Template.
     * It is necessary for Plugged function executions and IW-Tags substitutions.
     *
     * @param idAdmission
     * Admission ID assigned to IW_template.
     * It is necessary for Plugged function executions and IW-Tags substitutions.
     * idPatient and idAdmission is used to construct its respective IwPatient and IwAdmssion
     * objects on server processing. They compound the environment for Plugged Functions executions
     * and IW-Tags substitutions. They have a relationship between themselves where a IwPatient object has
     * a current IwAdmission and a IwAdmission has a IwPatient. This bi-directional relationship must be
     * created to allow correct execution of plugged functions that make intensive use if them.
     *
     * @param variableMap
     *
     * @return Map<String,String> with standard executions response, or null
     * if the Initialization function doesn't exist.
     *
     * @throws com.iw.iwmobile.comm.IwCommException
     *
     *
     */
    Map<String, String> execConsistPluggedFunctionSync(
            String idText,
            String idTemplate,
            String idPatient,
            String idAdmission,
            HashMap<String, String> variableMap) throws IwCommException;

    /**
     * Asynchronous call.
     * @param projectName
     * @param className
     * @param serviceName
     * @param pMap
     * @param callback
     */
    void execIwService(
            String projectName,
            String className,
            String serviceName,
            Map<String, Object> pMap,
            IwHttpRequesterCallBack<Map<String, MobRecordset>> callback
    );

    /**
     * Synchronous call
     * @param projectName
     * @param pMap receive a map of parameters. The map can receive as value only the following
     * types of classes: Integer, Long, Float, Double, Date and MobRecordset.
     * Only those classes can be serialized into requests.
     * @param className
     * @param serviceName
     * @return List of MobRecordset.
     * @throws com.iw.iwmobile.comm.IwCommException
     */
    Map<String, MobRecordset> execIwService(
            String projectName,
            String className,
            String serviceName,
            Map<String, Object> pMap) throws IwCommException;

    Map<String, MobRecordset> execIwService(
            String projectName,
            String className,
            String serviceName,
            Map<String, Object> pMap, int timeout) throws IwCommException;
    
    void execPluggedFunction(
            long idTemplate,
            String funcName,
            IwHttpRequesterCallBack<Map<String, MobRecordset>> callback
    );

    void execPluggedFunctionAsync(
            String idText,
            String idTemplate,
            String funcName,
            String idPatient,
            String idAdmission,
            HashMap<String, String> variableMap,
            IwHttpRequesterCallBack<Map<String, MobRecordset>> callback
    );

    Map<String, String> execPluggedFunctionSync(
            String idText,
            String idTemplate,
            String funcName,
            String idPatient,
            String idAdmission,
            HashMap<String, String> variableMap) throws IwCommException;

    /**
     *
     * executeProfCheckin - Synchronous version
     *
     * @param strIdAdmission
     * @param strIdProfessional
     * @param patientAddressGeoLoc
     * @param currentProfGeoLoc
     * @param geoCheckinTolerance
     * @param idCapShiftItem
     * @return
     * @throws com.iw.iwmobile.comm.IwCommException
     */
    Map<String, MobRecordset> execProfCheckin(
            String strIdAdmission,
            String strIdProfessional,
            String patientAddressGeoLoc,
            String currentProfGeoLoc,
            double geoCheckinTolerance,
            long idCapShiftItem) throws IwCommException, Exception;

    /**
     *
     * executeProfCheckout - Synchronous version
     *
     * @param idCapAdmProfCheckIn
     * @param patAddressGeoLoc
     * @param currentProfGeoLoc
     * @param geoCheckinTolerance
     * @param idCapShiftItem
     * @return
     * @throws com.iw.iwmobile.comm.IwCommException
     */
    Map<String, MobRecordset> execProfCheckout(
            long idCapAdmProfCheckIn,
            String patAddressGeoLoc,
            String currentProfGeoLoc,
            double geoCheckinTolerance,
            long idCapShiftItem) throws IwCommException, Exception;

    /**
     *
     * executeProfCheckin - Assynchronous version
     *
     * @param strIdAdmission
     * @param strIdProfessional
     * @param patientAddressGeoLoc
     * @param currentProfGeoLoc
     * @param geoCheckinTolerance
     * @param idCapShiftItem
     * @return
     * @throws com.iw.iwmobile.comm.IwCommException
     */
    Map<String, MobRecordset> execProfCheckin(
            String strIdAdmission,
            String strIdProfessional,
            String patientAddressGeoLoc,
            String currentProfGeoLoc,
            double geoCheckinTolerance,
            long idCapShiftItem,
            Date checkInDate) throws IwCommException, Exception;

    /**
     *
     * executeProfCheckout - Assynchronous version
     *
     * @param idCapAdmProfCheckIn
     * @param patAddressGeoLoc
     * @param currentProfGeoLoc
     * @param geoCheckinTolerance
     * @param idCapShiftItem
     * @param checkOutDate
     * @return
     * @throws com.iw.iwmobile.comm.IwCommException
     */
    Map<String, MobRecordset> execProfCheckout(
            long idCapAdmProfCheckIn,
            String patAddressGeoLoc,
            String currentProfGeoLoc,
            double geoCheckinTolerance,
            long idCapShiftItem,
            Date checkOutDate) throws IwCommException, Exception;
            
    
    /**
     *
     * executeProfCheckoutInformed - Synchronous version
     *
     * @param idCapAdmProfCheckIn
     * @param checkoutDate
     * @param currentProfGeoLoc
     * @param idCapShiftItem
     * @return
     * @throws com.iw.iwmobile.comm.IwCommException
     */
    Map<String, MobRecordset> execProfCheckoutInformed(
            long idCapAdmProfCheckIn,
            String currentProfGeoLoc,
            Date checkoutDate,
            long idCapShiftItem) throws IwCommException, Exception;


    Map<String,MobRecordset> execProfCheckinNurseMedCheck(
                String strIdAdmission,
                String strIdProfessional,
                String patientAddressGeoLoc,
                String currentProfGeoLoc,
                double geoCheckinTolerance,
                long idCapShiftItem,
                Date checkinDate) throws IwCommException, Exception; 

    Map<String,MobRecordset> execProfCheckinNurseMedCheckAssync(
                String strIdAdmission,
                String strIdProfessional,
                String patientAddressGeoLoc,
                String currentProfGeoLoc,
                long idCapShiftItem,
                Date checkinDate) throws IwCommException, Exception; 
    
    Map<String,MobRecordset> execProfCheckinNurseMedCheck(
                String strIdProfessional,
                long idCapShiftItem,
                Date checkinDate) throws IwCommException, Exception; 
    
    public Map<String,MobRecordset> execProfCheckinNurseMedCheck(
                String strIdProfessional,
                long idCapShiftItem,
                Date checkinDate,int checkinConting, String checkinContingDesc) throws IwCommException, Exception; 
    
    Map<String,MobRecordset> execProfCheckoutNurseMedCheck(
                long idCapAdmProfCheckIn,
                String patAddressGeoLoc,
                String currentProfGeoLoc,
                double geoCheckinTolerance,
                long idCapShiftItem,
                Date checkOutDate
            ) throws IwCommException, Exception;
    
    Map<String,MobRecordset> execProfCheckoutNurseMedCheck(
                long idCapShiftItem,
                Date checkOutDate
            ) throws IwCommException, Exception;

    Map<String,MobRecordset> execProfCheckinNurseMedCheckAssync(
                String strIdProfessional,
                long idCapShiftItem,
                Date checkinDate) throws IwCommException, Exception;  
    
    Map<String,MobRecordset> execProfCheckoutNurseMedCheckAssync(
                long idCapShiftItem,
                Date checkOutDate
            ) throws IwCommException, Exception;
    
    
    /**
     * @return the Access Configuration object that IwServiceCaller use to do service calls
     */
    AccessConfig getAccessConfig();

    String getAccessKey();

    /**
     *
     * @param callback
     *
     * If the request is successful call success callback method
     * Builds a response with following structure into standard Response HashMap:
     * 1) A MobRecordset called rsAdmissions with data about respective Admissions
     *
     */
    void getAdmissions(IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);

    /**
     *
     * Synchronous version of the same method above
     *
     * @return 
     * @throws com.iw.iwmobile.comm.IwCommException
     */
    Map<String, MobRecordset> getAdmissions() throws IwCommException;
    
    Map<String,MobRecordset> getAdmissions(
            String status,
            String idAdmission,
            String idContract,
            String patientName,
            String carePlanRegister                            
        ) throws IwCommException;

    Map<String,MobRecordset> getAdmissions(
            String idDepartment
        ) throws IwCommException;

    String getBaseURL();

    /**
     *
     * @param idAdmission
     * @param callback
     * @callback
     *
     * if the request is successful call success callback method.
     * Builds a response with following structure into standard Response HashMap:
     * 1) MobRecordset called rsTemplatesList with a List of templates assigned to speciality of user logged in.
     * 2) MobRecordset called rsProgrammed with data about all Planned consults assigned to user logged in.
     * 3) MobRecordset called rsPlanned with data about all Planned consults assigned to user logged in.
     * 4) MobRecordset called rsAdditionalReasons with data about Additional Reasons for Stand Alone Consults.
     * These 4 MobRecordsets are used to populate combo boxes fields into IwFormAddEvoltion UI.
     *
     */
    void getData4FormAddEvolution(long idAdmission, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);
    Map<String,MobRecordset> getData4FormAddEvolution (long idAdmission) throws IwCommException;

    /**
     *
     * @param idEvolution
     * @param callback
     */
    void getData4FormEditEvolution(long idEvolution, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);

    /**
     *
     * @param idAdmission
     * @param callback
     *
     * if request is successful call success callback method.
     * Builds a response with following structure into standard Response HashMap:
     * 1) MobRecordset called rsEvolutions with data about respective Evolutions
     */
    void getEvolutions(long idAdmission, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);
    Map<String, MobRecordset> getEvolutions(long idAdmission) throws IwCommException;

    /**
     *
     * @param idAdmission
     * @param dateMin
     * @param callback
     *
     * if request is successful call success callback method.
     * Builds a response with following structure into standard Response HashMap:
     * 1) MobRecordset called rsEvolutions with data about respective Evolutions
     */
    void getEvolutions(long idAdmission, Date dateMin, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);
    Map<String, MobRecordset> getEvolutions(long idAdmission, Date dateMin) throws IwCommException;
    
    /**
     *
     * @param idAdmission
     * @param regtype
     * @param callback
     *
     * if the request is successful call success callback method.
     * Builds a response with following structure into standard Response HashMap:
     * 1) A MobRecordset called rsEvolutions with data about selected Evolutions
     * 2) A mobRecordset called rsHtml with unique row and unique String field containing
     *    a HTML with Data about all evolutions returned into rsEvoltions.
     *
     */
    void getEvolutions(long idAdmission, int regtype, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);
    Map<String, MobRecordset> getEvolutions(long idAdmission, int regtype) throws IwCommException;

    /**
     *
     * @param idAdmission
     * @param dateMin
     * @param regtype
     * @param callback
     *
     * if request is successful call success callback method.
     * Builds a response with following structure into standard Response HashMap:
     * 1) MobRecordset called rsEvolutions with data about respective Evolutions
     */
    void getEvolutions(long idAdmission, Date dateMin, int regtype, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);
    Map<String, MobRecordset> getEvolutions(long idAdmission, Date dateMin, int regtype) throws IwCommException;
    
    /**
     *
     * @param evolIdList
     * @param callback
     *
     * if the request is successful call success callback method.
     * Builds a response with following structure into standard Response HashMap:
     * 1) A MobRecordset called rsEvolutions with data about selected Evolutions
     * 2) A mobRecordset called rsHtml with unique row and unique String field containing
     *    a HTML with Data about all evolutions returned into rsEvoltions.
     *
     */
    
    void getEvolutions(ArrayList<Long> evolIdList, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);
    Map<String,MobRecordset> getEvolutions(ArrayList<Long> evolIdList) throws IwCommException;
    
    void getEvolutions(long idAdmission, long idTemplate, int regType, Date startDate, Date endDate, 
            IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);
    
    void getHtml4SummerNote(IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);
    Map<String, MobRecordset> getHtml4SummerNote()throws IwCommException;

    /**
     *
     * @param idTemplate
     * @param idText
     * @param idAdmission
     * @param callback
     * @callback
     *
     * if the request is successful call success callback method.
     * Builds a response with following structure into standard Response HashMap:
     * 1) A MobRecordset called rsTemplate with all data about requested IwTemplete.
     */
    void getIwTemplateByIdText(
            long idTemplate,
            long idText,
            long idAdmission,
            IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);
    
    public Map<String,MobRecordset> getIwTemplateByIdText (
            long idTemplate,
            long idText,
            long idAdmission) throws IwCommException;
    /**
     *
     * @param dRef
     * Reference Date for Schedule
     *
     * @param callback
     *
     * If the request is successful call success callback method
     * Builds response with following structure into standard Response HashMap:
     * 1)MobRecordset called rsAdmissions with data about respective Admissions
     *
     */
    void getMySchedule(Date dRef, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);
    void getMySchedule(
            Date dRef1,
            Date dRef2,
            IwHttpRequesterCallBack<Map<String, MobRecordset>> callback
    );

    /**
     *
     * Synchronous version of the same method above
     *
     * @param dRef
     * @return 
     * @throws com.iw.iwmobile.comm.IwCommException
     */
    Map<String, MobRecordset> getMySchedule(Date dRef) throws IwCommException;
    Map<String, MobRecordset> getMySchedule(
            Date dRef1,
            Date dRef2
    ) throws IwCommException;
    
    
    void getProfShifts(Long idProfessional, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);
    
    Map<String, MobRecordset> getProfShifts(Long idProfessional) throws IwCommException;
    
    long getPatientPhotoId(long idAdmission);

    /**
     *
     * @param idAdmission
     * @param callback
     *
     * if request is successful call success callback method.
     * Builds a response with following structure into standard Response HashMap:
     * 1) MobRecordset called rsPrescriptions with data about respective Prescriptions
     *
     */
    void getPrescriptions(long idAdmission, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);

    /**
     *
     * @param prescIdList
     * @param callback
     *
     * if the request is successful call success callback method.
     * Builds a response with following structure into standard Response HashMap:
     * 1) A MobRecordset called rsEvolutions with data about selected prescriptions
     * 2) A mobRecordset called rsHtml with unique row and unique String field containing
     *    a HTML with Data about all evolutions returned into rsEvoltions.
     *
     */
    void getPrescriptions(ArrayList<Long> prescIdList, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);

    /**
     *
     * @param callback
     *
     * If the request is successful call success callback method
     * Builds a response with following structure into standard Response HashMap:
     * 1) A MobRecordset called rsReports with data about respective reports.
     *
     */
    void getReports(IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);

    /**
     *
     * @param idAdmission
     * @param callback
     *
     * if the request is successful call success callback method.
     * Builds a response with following structure into standard Response HashMap:
     * 1) A MobRecordset called rsEvolutions with data about respective Evolutions
     * 2) A mobRecordset called rsHtml with unique row and unique String field containing
     *    a HTML with Data about all evolutions returned into rsEvoltions.
     *
     */
    void getSummarySheet(long idAdmission, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);
    // Synchronous version
    Map<String, MobRecordset> getSummarySheet(long idAdmission) throws IwCommException;


    //
    void getSummarySheetIndirect_test(long idAdmission, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);

    /**
     * @return the aToken
     */
    AccessToken getaToken();

    long removePatientPhoto(String strIdAdmission);

    /**
     *
     * @param callback
     *
     * If the request is successful call success callback method
     * Builds a response with following structure into standard Response HashMap:
     * 1) A MobRecordset called rsAdmissions with data about respective Admissions
     *
     */
    void setDebugOff(IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);

    /**
     *
     * Synchronous version of the same method above
     *
     * @return 
     * @throws com.iw.iwmobile.comm.IwCommException
     */
    Map<String, MobRecordset> setDebugOff() throws IwCommException;

    ////////////////////////////////////////////////////////////////////////////////
    // IW SYSTEM SERVICES
    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     * @param callback
     *
     * If the request is successful call success callback method
     * Builds a response with following structure into standard Response HashMap:
     * 1) A MobRecordset called rsAdmissions with data about respective Admissions
     *
     */
    void setDebugOn(IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);

    /**
     *
     * Synchronous version of the same method above
     *
     * @return 
     * @throws com.iw.iwmobile.comm.IwCommException
     */
    Map<String, MobRecordset> setDebugOn() throws IwCommException;

    /**
     *
     * setPatientGeoLocation - Synchronous version
     *
     * @param strIdAdmission
     * @param geoLocationPatient
     * @param strIdPersonPatient
     * @return
     * @throws com.iw.iwmobile.comm.IwCommException
     */
    Map<String, MobRecordset> setPatientGeoLocation(String strIdAdmission, String strIdPersonPatient, String geoLocationPatient) throws IwCommException;

    ////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INTERFACE
    ////////////////////////////////////////////////////////////////////////////////
    Map<String, MobRecordset> updateEvolution(String timeStampCtr, long idEvolution, long idAdmission, long idText, String tableName, long idRowOfDynTable, HashMap<String, String> variablesMap, ArrayList<String> imageVarNameList, ArrayList<String> pdfVarNameList) throws IwCommException, IOException;
    
    Map<String, MobRecordset> updateEvolution(String timeStampCtr, long idEvolution, long idAdmission, long idTemplate, long idText, String tableName, long idRowOfDynTable, HashMap<String, String> variablesMap, ArrayList<String> imageVarNameList, ArrayList<String> pdfVarNameList) throws IwCommException, IOException;

    /**
     *
     * @param profile
     * @param IdReference
     * @param referenceType
     * @param fileID
     * @param strFilePath
     * @return
     * @throws IwCommException
     * @throws IOException
     */
    Map<String, Long> uploadFile(String profile, long IdReference, long referenceType, String fileID, String strFilePath) throws IwCommException, IOException;

    Map<String, Long> uploadFile(String profile, long IdReference, long referenceType, String fileID, String strFilePath, String strIdPersonPatient) throws IwCommException, IOException;
    
    long uploadPatientPhoto(String strIdAdmission, String strIdPerson, String strPhotoPath) throws IwCommException, IOException;

    Map<String, MobRecordset> comparePersonPhoto(String strFilePath, String strIdPerson) throws IwCommException, IOException;

    boolean validatePassword(String pwd) throws IwCommException;
    
    
    
    /**
     * Asynchronous version
     * 
     * @param idPatient
     * @param callback
     *
     * If the request is successful call success callback method
     * Builds response with standard Response HashMap:
     * 1)MobRecordset called rsMedicamentUse
     *   with data about medicament being used by respective patient.
     *
     */
    void getMedicamentUse(
            long idPatient,
            IwHttpRequesterCallBack<Map<String, MobRecordset>> callback
    );

    /**
     *
     * Synchronous version of the same method above
     *
     * @param idPatient
     * @return 
     * @throws com.iw.iwmobile.comm.IwCommException
     */
    Map<String, MobRecordset>
         getMedicamentUse(
                 long idPatient
         ) throws IwCommException;

         /**
     * Asynchronous version
     * 
     * @param idPatient
     * @param callback
     *
     * If the request is successful call success callback method
     * Builds response with standard Response HashMap:
     * 1)MobRecordset called rsPatientDiags
     *   with data about Diagnostics of respective patient.
     *
     */
    void getPatientDiagnostics(
            long idPatient,
            IwHttpRequesterCallBack<Map<String, MobRecordset>> callback
    );

    /**
     *
     * Synchronous version of the same method above
     *
     * @param idPatient
     * @return 
     * @throws com.iw.iwmobile.comm.IwCommException
     */
    Map<String, MobRecordset>
         getPatientDiagnostics(
                 long idPatient
         ) throws IwCommException;

    /**
     *
     * Synchronous version of the same method above
     *
     * @param idProf
     * @return 
     * @throws com.iw.iwmobile.comm.IwCommException
     */
    ArrayList<ScheduledVisit> getScheduledVisits(long idProf)
            throws IwCommException;

    /**
     * Synchronous version of the same method above
     * @param idProf
     * @param callback
     */
    void getScheduledVisits(
        long idProf,
        IwHttpRequesterCallBack<ArrayList<ScheduledVisit>> callback
    );

/**
     *
     * updCleanEvent
     * 
     * @param idShiftItem
     * @param idAttendancePlace
     * @param comfortlevel
     * @param startDate
     * @return 
     * @throws com.iw.iwmobile.comm.IwCommException
     */    
    Map<String, MobRecordset>
         updCleanEvent(long idShiftItem, long idAttendancePlace,int comfortlevel,long startDate) throws IwCommException;
    
         
    public boolean execReport(long idreport, long id, String filename)  throws IwCommException, Exception ;

    public Map<String,Long> uploadPDF( String profile, String sFilePath) throws IwCommException, IOException;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iw.iwmobile.comm;

import com.iw.iwmobile.entities.AccessConfig;
import com.iw.iwmobile.entities.AccessToken;
import com.iw.iwmobile.entities.ScheduledVisit;
import com.iw.iwmobile.extensions._fakeServerResponses.*;

import java.io.IOException;
import java.util.*;

/**
 *
 * @author helio-ubu1404
 */
public class IwServiceCaller implements IwServiceCallerInterface {

    /**
     * Creates a fake IwServiceCaller
     */
    public IwServiceCaller() {
    }


    @Override
    public Map<String, MobRecordset> addEvolution(
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
            ArrayList<String> pdfVarNameList) throws IwCommException, IOException {

        MobRecordset fakeMobRecordsetResp = new MobRecordset();
        MobRow r = new MobRow();
        MobField f = new MobField("ID", "String", "1234");
        r.addField(f);
        fakeMobRecordsetResp.addRow(r);
        Map<String, MobRecordset> fakeResp = new HashMap<String, MobRecordset>();
        fakeResp.put("rsAddedEvolution", fakeMobRecordsetResp);
        return fakeResp;
    }

    @Override
    public void clearCache(IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
    }

    @Override
    public Map<String, MobRecordset> clearCache() throws IwCommException {
        return null;
    }

    @Override
    public void execConsistPluggedFunctionAsync(String idText, String idTemplate, String idPatient, String idAdmission, HashMap<String, String> variableMap, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
        MobRecordsetError rsError = new MobRecordsetError(-1,-1, "can't execute consistence function", "função de consistencia não acessível");
        callback.onFailure(rsError);
    }

    @Override
    public Map<String, String> execConsistPluggedFunctionSync(String idText, String idTemplate, String idPatient, String idAdmission, HashMap<String, String> variableMap) throws IwCommException {
        return null;
    }

    @Override
    public void execIwService(String projectName, String className, String serviceName, Map<String, Object> pMap, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
        MobRecordsetError rsError = new MobRecordsetError(-1,-1, "iw-service not available: " + serviceName, "serviço-iw não disponível");
        callback.onFailure(rsError);
    }

    @Override
    public Map<String, MobRecordset> execIwService(String projectName, String className, String serviceName, Map<String, Object> pMap) throws IwCommException {
        return null;
    }

    @Override
    public Map<String, MobRecordset> execIwService(String projectName, String className, String serviceName, Map<String, Object> pMap, int timeout) throws IwCommException {
        return null;
    }

    @Override
    public void execPluggedFunction(long idTemplate, String funcName, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
        MobRecordsetError rsError = new MobRecordsetError(-1,-1,"iw-plugged-function not available", "função-plugada não disponível");
        callback.onFailure(rsError);
    }

    @Override
    public void execPluggedFunctionAsync(String idText, String idTemplate, String funcName, String idPatient, String idAdmission, HashMap<String, String> variableMap, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
        MobRecordsetError rsError = new MobRecordsetError(-1,-1,"iw-plugged-function not available", "função-plugada não disponível");
        callback.onFailure(rsError);
    }

    @Override
    public Map<String, String> execPluggedFunctionSync(String idText, String idTemplate, String funcName, String idPatient, String idAdmission, HashMap<String, String> variableMap) throws IwCommException {
        return null;
    }

    @Override
    public Map<String, MobRecordset> execProfCheckin(String strIdAdmission, String strIdProfessional, String patientAddressGeoLoc, String currentProfGeoLoc, double geoCheckinTolerance, long idCapShiftItem) throws IwCommException, Exception {
        return null;
    }

    @Override
    public Map<String, MobRecordset> execProfCheckout(long idCapAdmProfCheckIn, String patAddressGeoLoc, String currentProfGeoLoc, double geoCheckinTolerance, long idCapShiftItem) throws IwCommException, Exception {
        return null;
    }

    @Override
    public Map<String, MobRecordset> execProfCheckin(String strIdAdmission, String strIdProfessional, String patientAddressGeoLoc, String currentProfGeoLoc, double geoCheckinTolerance, long idCapShiftItem, Date checkInDate) throws IwCommException, Exception {
        return null;
    }

    @Override
    public Map<String, MobRecordset> execProfCheckout(long idCapAdmProfCheckIn, String patAddressGeoLoc, String currentProfGeoLoc, double geoCheckinTolerance, long idCapShiftItem, Date checkOutDate) throws IwCommException, Exception {
        return null;
    }

    @Override
    public Map<String, MobRecordset> execProfCheckoutInformed(long idCapAdmProfCheckIn, String currentProfGeoLoc, Date checkoutDate, long idCapShiftItem) throws IwCommException, Exception {
        return null;
    }

    @Override
    public Map<String, MobRecordset> execProfCheckinNurseMedCheck(String strIdAdmission, String strIdProfessional, String patientAddressGeoLoc, String currentProfGeoLoc, double geoCheckinTolerance, long idCapShiftItem, Date checkinDate) throws IwCommException, Exception {
        return null;
    }

    @Override
    public Map<String, MobRecordset> execProfCheckinNurseMedCheckAssync(String strIdAdmission, String strIdProfessional, String patientAddressGeoLoc, String currentProfGeoLoc, long idCapShiftItem, Date checkinDate) throws IwCommException, Exception {
        return null;
    }

    @Override
    public Map<String, MobRecordset> execProfCheckinNurseMedCheck(String strIdProfessional, long idCapShiftItem, Date checkinDate) throws IwCommException, Exception {
        return null;
    }

    @Override
    public Map<String, MobRecordset> execProfCheckinNurseMedCheck(String strIdProfessional, long idCapShiftItem, Date checkinDate, int checkinConting, String checkinContingDesc) throws IwCommException, Exception {
        return null;
    }

    @Override
    public Map<String, MobRecordset> execProfCheckoutNurseMedCheck(long idCapAdmProfCheckIn, String patAddressGeoLoc, String currentProfGeoLoc, double geoCheckinTolerance, long idCapShiftItem, Date checkOutDate) throws IwCommException, Exception {
        return null;
    }

    @Override
    public Map<String, MobRecordset> execProfCheckoutNurseMedCheck(long idCapShiftItem, Date checkOutDate) throws IwCommException, Exception {
        return null;
    }

    @Override
    public Map<String, MobRecordset> execProfCheckinNurseMedCheckAssync(String strIdProfessional, long idCapShiftItem, Date checkinDate) throws IwCommException, Exception {
        return null;
    }

    @Override
    public Map<String, MobRecordset> execProfCheckoutNurseMedCheckAssync(long idCapShiftItem, Date checkOutDate) throws IwCommException, Exception {
        return null;
    }

    @Override
    public AccessConfig getAccessConfig() {
        return null;
    }

    @Override
    public String getAccessKey() {
        return null;
    }

    @Override
    public void getAdmissions(IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {

    }

    @Override
    public Map<String, MobRecordset> getAdmissions() throws IwCommException {
        return null;
    }

    @Override
    public Map<String, MobRecordset> getAdmissions(String status, String idAdmission, String idContract, String patientName, String carePlanRegister) throws IwCommException {
        return null;
    }

    @Override
    public Map<String, MobRecordset> getAdmissions(String idDepartment) throws IwCommException {
        return null;
    }

    @Override
    public String getBaseURL() {
        return null;
    }

    @Override
    public void getData4FormAddEvolution(long idAdmission, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
        new FakeGetData4AddEvolution().execute(callback);
    }

    @Override
    public Map<String, MobRecordset> getData4FormAddEvolution(long idAdmission) throws IwCommException {
        return null;
    }

    @Override
    public void getData4FormEditEvolution(long idEvolution, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
        MobRecordsetError rsError = new MobRecordsetError(
                -1,
                -1,
                "iw-service to get data for edit evolution not available",
                "serviço-iw para obter dados para edição de evolução não disponível");
        callback.onFailure(rsError);
    }

    @Override
    public void getEvolutions(long idAdmission, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
        new FakeGetEvolutions().execute(callback);
    }

    @Override
    public Map<String, MobRecordset> getEvolutions(long idAdmission) throws IwCommException {
        return null;
    }

    @Override
    public void getEvolutions(long idAdmission, Date dateMin, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
        new FakeGetEvolutions().execute(callback);
    }

    @Override
    public Map<String, MobRecordset> getEvolutions(long idAdmission, Date dateMin) throws IwCommException {
        return null;
    }

    @Override
    public void getEvolutions(long idAdmission, int regtype, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
        new FakeGetEvolutions().execute(callback);
    }

    @Override
    public Map<String, MobRecordset> getEvolutions(long idAdmission, int regtype) throws IwCommException {
        return null;
    }

    @Override
    public void getEvolutions(long idAdmission, Date dateMin, int regtype, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
        new FakeGetEvolutions().execute(callback);
    }

    @Override
    public Map<String, MobRecordset> getEvolutions(long idAdmission, Date dateMin, int regtype) throws IwCommException {
        return null;
    }


    // all asynchronous "getEvolutions" points to fake_getEvolutions
    @Override
    public void getEvolutions(ArrayList<Long> evolIdList, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
        new FakeGetEvolutions().execute(callback);
    }

    @Override
    public Map<String, MobRecordset> getEvolutions(ArrayList<Long> evolIdList) throws IwCommException {
        return null;
    }

    @Override
    public void getEvolutions(long idAdmission, long idTemplate, int regType, Date startDate, Date endDate, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
        new FakeGetEvolutions().execute(callback);
    }
    @Override
    public void getHtml4SummerNote(IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
    }
    @Override
    public void getIwTemplateByIdText(long idTemplate, long idText, long idAdmission, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
        if (idTemplate == 424) {
            new FakeGetTemplate424().execute(callback);
        } else if (idTemplate == 426) {
            new FakeGetTemplate426().execute(callback);
        } else {
            MobRecordsetError rsError = new MobRecordsetError(-1,-1, "tamplate not exist", "template não existe");
            callback.onFailure(rsError);
        }
    }
    @Override
    public void getMySchedule(Date dRef, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
    }
    @Override
    public void getMySchedule(Date dRef1, Date dRef2, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
    }

    @Override
    public Map<String, MobRecordset> getHtml4SummerNote() throws IwCommException {
        return null;
    }
    @Override
    public Map<String, MobRecordset> getIwTemplateByIdText(long idTemplate, long idText, long idAdmission) throws IwCommException {
        return null;
    }
    @Override
    public Map<String, MobRecordset> getMySchedule(Date dRef) throws IwCommException {
        return null;
    }
    @Override
    public Map<String, MobRecordset> getMySchedule(Date dRef1, Date dRef2) throws IwCommException {
        return null;
    }



    @Override
    public void getProfShifts(Long idProfessional, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
    }

    @Override
    public Map<String, MobRecordset> getProfShifts(Long idProfessional) throws IwCommException {
        return null;
    }

    @Override
    public long getPatientPhotoId(long idAdmission) {
        return 0;
    }

    @Override
    public void getPrescriptions(long idAdmission, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
    }

    @Override
    public void getPrescriptions(ArrayList<Long> prescIdList, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
    }

    @Override
    public void getReports(IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
    }

    @Override
    public void getSummarySheet(long idAdmission, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
    }

    @Override
    public Map<String, MobRecordset> getSummarySheet(long idAdmission) throws IwCommException {
        return null;
    }

    @Override
    public void getSummarySheetIndirect_test(long idAdmission, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
    }

    @Override
    public AccessToken getaToken() {
        return null;
    }

    @Override
    public long removePatientPhoto(String strIdAdmission) {
        return 0;
    }

    @Override
    public void setDebugOff(IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {

    }

    @Override
    public Map<String, MobRecordset> setDebugOff() throws IwCommException {
        return null;
    }

    @Override
    public void setDebugOn(IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
    }

    @Override
    public Map<String, MobRecordset> setDebugOn() throws IwCommException {
        return null;
    }

    @Override
    public Map<String, MobRecordset> setPatientGeoLocation(String strIdAdmission, String strIdPersonPatient, String geoLocationPatient) throws IwCommException {
        return null;
    }

    @Override
    public Map<String, MobRecordset> updateEvolution(String timeStampCtr, long idEvolution, long idAdmission, long idText, String tableName, long idRowOfDynTable, HashMap<String, String> variablesMap, ArrayList<String> imageVarNameList, ArrayList<String> pdfVarNameList) throws IwCommException, IOException {
        return null;
    }

    @Override
    public Map<String, MobRecordset> updateEvolution(String timeStampCtr, long idEvolution, long idAdmission, long idTemplate, long idText, String tableName, long idRowOfDynTable, HashMap<String, String> variablesMap, ArrayList<String> imageVarNameList, ArrayList<String> pdfVarNameList) throws IwCommException, IOException {
        return null;
    }

    @Override
    public Map<String, Long> uploadFile(String profile, long IdReference, long referenceType, String fileID, String strFilePath) throws IwCommException, IOException {
        return null;
    }

    @Override
    public Map<String, Long> uploadFile(String profile, long IdReference, long referenceType, String fileID, String strFilePath, String strIdPersonPatient) throws IwCommException, IOException {
        return null;
    }

    @Override
    public long uploadPatientPhoto(String strIdAdmission, String strIdPerson, String strPhotoPath) throws IwCommException, IOException {
        return 0;
    }

    @Override
    public Map<String, MobRecordset> comparePersonPhoto(String strFilePath, String strIdPerson) throws IwCommException, IOException {
        return null;
    }

    @Override
    public boolean validatePassword(String pwd) throws IwCommException {
        return false;
    }

    @Override
    public void getMedicamentUse(long idPatient, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
    }

    @Override
    public Map<String, MobRecordset> getMedicamentUse(long idPatient) throws IwCommException {
        return null;
    }

    @Override
    public void getPatientDiagnostics(long idPatient, IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
    }

    @Override
    public Map<String, MobRecordset> getPatientDiagnostics(long idPatient) throws IwCommException {
        return null;
    }

    @Override
    public ArrayList<ScheduledVisit> getScheduledVisits(long idProf) throws IwCommException {
        return null;
    }

    @Override
    public void getScheduledVisits(long idProf, IwHttpRequesterCallBack<ArrayList<ScheduledVisit>> callback) {
    }

    @Override
    public Map<String, MobRecordset> updCleanEvent(long idShiftItem, long idAttendancePlace, int comfortlevel, long startDate) throws IwCommException {
        return null;
    }

    @Override
    public boolean execReport(long idreport, long id, String filename) throws IwCommException, Exception {
        return false;
    }

    @Override
    public Map<String, Long> uploadPDF(String profile, String sFilePath) throws IwCommException, IOException {
        return null;
    }
}

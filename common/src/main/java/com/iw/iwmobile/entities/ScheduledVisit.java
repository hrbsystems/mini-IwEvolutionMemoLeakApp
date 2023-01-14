/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.entities;

import java.util.Date;

/**
 *
 * @author hrugani
 */
public class ScheduledVisit {
    
    private String strUUID;
    
    private String startHour;
    private Long elapsedTime; //number(9,0), serviço está devolvendo Long
    private long scspeciality;
    
    // 1º linha
    // LABEL1 IDCONSULT - LABEL2 AGENDASTARTDATE - LABEL3 AGENDAENDDATE
    private String label1           = "IdConsulta:";
    private Long   idConsult        = null;       // Number(9,0)
    private String label2           = "Agendar de:";
    private Date   agendaStartDate  = new Date(); // dd/mm/yyyy hh:mm:ss
    private String label3           = "Agendar até:";
    private Date   agendaEndDate    = new Date();   // dd/mm/yyyy hh:mm:ss
            
    // 2º linha
    // PATIENTNAME (IDADMISSION) - LABEL4 GENDER - LABEL5 PATIENTAGE 
    private String patientName;     // “José da Silva Junior”
    private Long   idAdmission;     // Number(9,0)
    private String label4           = "Sexo:"; 
    private String gender           = "Feminino";
    private String label5           = "Idade:";
    private String patientAge       = "25a";

    // 3º linha
    // RISKCLASSIFICATIONNAME - LABEL6 RISKCLASBEGINDATE – LABEL13 RISKCLASDAYS 
    private String riskClassificationName = "GC Nível I";
    private String label6                 = "Início Programa:";
    private Date riskClassificationDate;  // "01/10/2017"
    private String label13                = "Nº Dias:";
    private Double riskClasDays;
    
    // 4º linha
    // LABEL7 LASTVISITDATE - LABEL8 LASTVISITDAYS - LABEL9 NEXTVISITMAXDATE - LABEL10 NEXTVISITMAXDAYS
    private String label7                  = "Data Última Visita:";
    private Date lastVisitDate;            // "10/12/2017"
    private String label8                  = "Nº dias:";
    private Double lastVisitDays;
    private String label9                  = "Data Limite:";
    private Date nextVisitMaxDate;         // "31/01/2018"
    private String label10                 = "Dias Rest.:";
    private Double nextVisitMaxDays;
    
    // 5º linha
    // LABEL11 ZONECODENAME - LABEL12 PATIENTADRESS
    private String label11 = "Regional:";
    private String zoneCodeName = "Zona Sul";
    private String label12 = "Endereço:";
    private String patientAddress = "Av. Paulista,1000 - Centro - CEP:1001-010";
    
    ////////////////////////////////////////////////////////////////////////////
    // Atributos com cores
    ////////////////////////////////////////////////////////////////////////////
    
    private String stlLabel1 = "";
    private String stlIdConsult = "";
    private String stlLabel2 = "";
    private String stlAgendaStartDate = "";
    private String stlLabel3 = "";
    private String stlAgendaEndDate = "";
            
    // 2º linha
    // PATIENTNAME (IDADMISSION) - LABEL4 GENDER - LABEL5 PATIENTAGE 
    private String stlPatientName = "";
    private String stlIdAdmission = "";
    private String stlLabel4      = ""; 
    private String stlGender      = "";
    private String stlLabel5      = "";
    private String stlPatientAge  = "";

    // 3º linha
    // RISKCLASSIFICATIONNAME - LABEL6 RISKCLASBEGINDATE – LABEL13 RISKCLASDAYS 
    private String stlRiskClassificationName = "";
    private String stlLabel6                 = "";
    private String stlRiskClassificationDate = "";
    private String stlLabel13                = "";
    private String stlRiskClasDays           = "";
    
    // 4º linha
    // LABEL7 LASTVISITDATE - LABEL8 LASTVISITDAYS - LABEL9 NEXTVISITMAXDATE - LABEL10 NEXTVISITMAXDAYS
    private String stlLabel7        = "";
    private String stlLastVisitDate = "";
    private String stlLabel8        = "";
    private String stlLastVisitDays = "";
    private String stlLabel9        = "";
    private String stlNextVisitMaxDate = "";
    private String stlLabel10          = "";
    private String stlNextVisitMaxDays = "";
    
    // 5º linha
    // LABEL11 ZONECODENAME - LABEL12 PATIENTADRESS
    private String stlLabel11 = "";
    private String stlZoneCodeName = "";
    private String stlLabel12 = "";
    private String stlPatientAddress = "";

    /**
     * @return the label1
     */
    public String getLabel1() {
        return label1;
    }

    /**
     * @param label1 the label1 to set
     */
    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    /**
     * @return the idConsult
     */
    public Long getIdConsult() {
        return idConsult;
    }

    /**
     * @param idConsult the idConsult to set
     */
    public void setIdConsult(Long idConsult) {
        this.idConsult = idConsult;
    }

    /**
     * @return the label2
     */
    public String getLabel2() {
        return label2;
    }

    /**
     * @param label2 the label2 to set
     */
    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    /**
     * @return the agendaStartDate
     */
    public Date getAgendaStartDate() {
        return agendaStartDate;
    }

    /**
     * @param agendaStartDate the agendaStartDate to set
     */
    public void setAgendaStartDate(Date agendaStartDate) {
        this.agendaStartDate = agendaStartDate;
    }

    /**
     * @return the label3
     */
    public String getLabel3() {
        return label3;
    }

    /**
     * @param label3 the label3 to set
     */
    public void setLabel3(String label3) {
        this.label3 = label3;
    }

    /**
     * @return the agendaEndDate
     */
    public Date getAgendaEndDate() {
        return agendaEndDate;
    }

    /**
     * @param agendaEndDate the agendaEndDate to set
     */
    public void setAgendaEndDate(Date agendaEndDate) {
        this.agendaEndDate = agendaEndDate;
    }

    /**
     * @return the patientName
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * @param patientName the patientName to set
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    /**
     * @return the idAdmission
     */
    public Long getIdAdmission() {
        return idAdmission;
    }

    /**
     * @param idAdmission the idAdmission to set
     */
    public void setIdAdmission(Long idAdmission) {
        this.idAdmission = idAdmission;
    }

    /**
     * @return the label4
     */
    public String getLabel4() {
        return label4;
    }

    /**
     * @param label4 the label4 to set
     */
    public void setLabel4(String label4) {
        this.label4 = label4;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the label5
     */
    public String getLabel5() {
        return label5;
    }

    /**
     * @param label5 the label5 to set
     */
    public void setLabel5(String label5) {
        this.label5 = label5;
    }

    /**
     * @return the patientAge
     */
    public String getPatientAge() {
        return patientAge;
    }

    /**
     * @param patientAge the patientAge to set
     */
    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    /**
     * @return the riskClassificationName
     */
    public String getRiskClassificationName() {
        return riskClassificationName;
    }

    /**
     * @param riskClassificationName the riskClassificationName to set
     */
    public void setRiskClassificationName(String riskClassificationName) {
        this.riskClassificationName = riskClassificationName;
    }

    /**
     * @return the label6
     */
    public String getLabel6() {
        return label6;
    }

    /**
     * @param label6 the label6 to set
     */
    public void setLabel6(String label6) {
        this.label6 = label6;
    }

    /**
     * @return the riskClassificationDate
     */
    public Date getRiskClassificationDate() {
        return riskClassificationDate;
    }

    /**
     * @param riskClassificationDate the riskClassificationDate to set
     */
    public void setRiskClassificationDate(Date riskClassificationDate) {
        this.riskClassificationDate = riskClassificationDate;
    }

    /**
     * @return the label13
     */
    public String getLabel13() {
        return label13;
    }

    /**
     * @param label13 the label13 to set
     */
    public void setLabel13(String label13) {
        this.label13 = label13;
    }

    /**
     * @return the riskClasDays
     */
    public Double getRiskClasDays() {
        return riskClasDays;
    }

    public String getRiskClasDaysAsString() {
        if (this.riskClasDays == null) {
            return "";
        }
        else {
            return "" + this.riskClasDays.intValue();
        }
    }
    

    /**
     * @param riskClasDays the riskClasDays to set
     */
    public void setRiskClasDays(Double riskClasDays) {
        this.riskClasDays = riskClasDays;
    }

    /**
     * @return the label7
     */
    public String getLabel7() {
        return label7;
    }

    /**
     * @param label7 the label7 to set
     */
    public void setLabel7(String label7) {
        this.label7 = label7;
    }

    /**
     * @return the lastVisitDate
     */
    public Date getLastVisitDate() {
        return lastVisitDate;
    }

    /**
     * @param lastVisitDate the lastVisitDate to set
     */
    public void setLastVisitDate(Date lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    /**
     * @return the label8
     */
    public String getLabel8() {
        return label8;
    }

    /**
     * @param label8 the label8 to set
     */
    public void setLabel8(String label8) {
        this.label8 = label8;
    }

    /**
     * @return the lastVisitDays
     */
    public Double getLastVisitDays() {
        return lastVisitDays;
    }
    public String getLastVisitDaysAsString() {
        if (this.lastVisitDays == null) {
            return "";
        }
        else {
            return "" + this.lastVisitDays.intValue();
        }
    }
    

    /**
     * @param lastVisitDays the lastVisitDays to set
     */
    public void setLastVisitDays(Double lastVisitDays) {
        this.lastVisitDays = lastVisitDays;
    }

    /**
     * @return the label9
     */
    public String getLabel9() {
        return label9;
    }

    /**
     * @param label9 the label9 to set
     */
    public void setLabel9(String label9) {
        this.label9 = label9;
    }

    /**
     * @return the nextVisitMaxDate
     */
    public Date getNextVisitMaxDate() {
        return nextVisitMaxDate;
    }

    /**
     * @param nextVisitMaxDate the nextVisitMaxDate to set
     */
    public void setNextVisitMaxDate(Date nextVisitMaxDate) {
        this.nextVisitMaxDate = nextVisitMaxDate;
    }

    /**
     * @return the label10
     */
    public String getLabel10() {
        return label10;
    }

    /**
     * @param label10 the label10 to set
     */
    public void setLabel10(String label10) {
        this.label10 = label10;
    }

    /**
     * @return the nextVisitMaxDays
     */
    public Double getNextVisitMaxDays() {
        return nextVisitMaxDays;
    }
    public String getNextVisitMaxDaysAsString() {
        if (this.nextVisitMaxDays == null) {
            return "";
        }
        else {
            return "" + nextVisitMaxDays.intValue();
        }
    }

    /**
     * @param nextVisitMaxDays the nextVisitMaxDays to set
     */
    public void setNextVisitMaxDays(Double nextVisitMaxDays) {
        this.nextVisitMaxDays = nextVisitMaxDays;
    }

    /**
     * @return the label11
     */
    public String getLabel11() {
        return label11;
    }

    /**
     * @param label11 the label11 to set
     */
    public void setLabel11(String label11) {
        this.label11 = label11;
    }

    /**
     * @return the zoneCodeName
     */
    public String getZoneCodeName() {
        return zoneCodeName;
    }

    /**
     * @param zoneCodeName the zoneCodeName to set
     */
    public void setZoneCodeName(String zoneCodeName) {
        this.zoneCodeName = zoneCodeName;
    }

    /**
     * @return the label12
     */
    public String getLabel12() {
        return label12;
    }

    /**
     * @param label12 the label12 to set
     */
    public void setLabel12(String label12) {
        this.label12 = label12;
    }

    /**
     * @return the patientAddress
     */
    public String getPatientAddress() {
        return patientAddress;
    }

    /**
     * @param patientAddress the patientAddress to set
     */
    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    /**
     * @return the stlLabel1
     */
    public String getStlLabel1() {
        return stlLabel1;
    }

    /**
     * @param stlLabel1 the stlLabel1 to set
     */
    public void setStlLabel1(String stlLabel1) {
        this.stlLabel1 = stlLabel1;
    }

    /**
     * @return the stlIdConsult
     */
    public String getStlIdConsult() {
        return stlIdConsult;
    }

    /**
     * @param stlIdConsult the stlIdConsult to set
     */
    public void setStlIdConsult(String stlIdConsult) {
        this.stlIdConsult = stlIdConsult;
    }

    /**
     * @return the stlLabel2
     */
    public String getStlLabel2() {
        return stlLabel2;
    }

    /**
     * @param stlLabel2 the stlLabel2 to set
     */
    public void setStlLabel2(String stlLabel2) {
        this.stlLabel2 = stlLabel2;
    }

    /**
     * @return the stlAgendaStartDate
     */
    public String getStlAgendaStartDate() {
        return stlAgendaStartDate;
    }

    /**
     * @param stlAgendaStartDate the stlAgendaStartDate to set
     */
    public void setStlAgendaStartDate(String stlAgendaStartDate) {
        this.stlAgendaStartDate = stlAgendaStartDate;
    }

    /**
     * @return the stlLabel3
     */
    public String getStlLabel3() {
        return stlLabel3;
    }

    /**
     * @param stlLabel3 the stlLabel3 to set
     */
    public void setStlLabel3(String stlLabel3) {
        this.stlLabel3 = stlLabel3;
    }

    /**
     * @return the stlAgendaEndDate
     */
    public String getStlAgendaEndDate() {
        return stlAgendaEndDate;
    }

    /**
     * @param stlAgendaEndDate the stlAgendaEndDate to set
     */
    public void setStlAgendaEndDate(String stlAgendaEndDate) {
        this.stlAgendaEndDate = stlAgendaEndDate;
    }

    /**
     * @return the stlPatientName
     */
    public String getStlPatientName() {
        return stlPatientName;
    }

    /**
     * @param stlPatientName the stlPatientName to set
     */
    public void setStlPatientName(String stlPatientName) {
        this.stlPatientName = stlPatientName;
    }

    /**
     * @return the stlIdAdmission
     */
    public String getStlIdAdmission() {
        return stlIdAdmission;
    }

    /**
     * @param stlIdAdmission the stlIdAdmission to set
     */
    public void setStlIdAdmission(String stlIdAdmission) {
        this.stlIdAdmission = stlIdAdmission;
    }

    /**
     * @return the stlLabel4
     */
    public String getStlLabel4() {
        return stlLabel4;
    }

    /**
     * @param stlLabel4 the stlLabel4 to set
     */
    public void setStlLabel4(String stlLabel4) {
        this.stlLabel4 = stlLabel4;
    }

    /**
     * @return the stlGender
     */
    public String getStlGender() {
        return stlGender;
    }

    /**
     * @param stlGender the stlGender to set
     */
    public void setStlGender(String stlGender) {
        this.stlGender = stlGender;
    }

    /**
     * @return the stlLabel5
     */
    public String getStlLabel5() {
        return stlLabel5;
    }

    /**
     * @param stlLabel5 the stlLabel5 to set
     */
    public void setStlLabel5(String stlLabel5) {
        this.stlLabel5 = stlLabel5;
    }

    /**
     * @return the stlPatientAge
     */
    public String getStlPatientAge() {
        return stlPatientAge;
    }

    /**
     * @param stlPatientAge the stlPatientAge to set
     */
    public void setStlPatientAge(String stlPatientAge) {
        this.stlPatientAge = stlPatientAge;
    }

    /**
     * @return the stlRiskClassificationName
     */
    public String getStlRiskClassificationName() {
        return stlRiskClassificationName;
    }

    /**
     * @param stlRiskClassificationName the stlRiskClassificationName to set
     */
    public void setStlRiskClassificationName(String stlRiskClassificationName) {
        this.stlRiskClassificationName = stlRiskClassificationName;
    }

    /**
     * @return the stlLabel6
     */
    public String getStlLabel6() {
        return stlLabel6;
    }

    /**
     * @param stlLabel6 the stlLabel6 to set
     */
    public void setStlLabel6(String stlLabel6) {
        this.stlLabel6 = stlLabel6;
    }

    /**
     * @return the stlRiskClassificationDate
     */
    public String getStlRiskClassificationDate() {
        return stlRiskClassificationDate;
    }

    /**
     * @param stlRiskClassificationDate the stlRiskClassificationDate to set
     */
    public void setStlRiskClassificationDate(String stlRiskClassificationDate) {
        this.stlRiskClassificationDate = stlRiskClassificationDate;
    }

    /**
     * @return the stlLabel13
     */
    public String getStlLabel13() {
        return stlLabel13;
    }

    /**
     * @param stlLabel13 the stlLabel13 to set
     */
    public void setStlLabel13(String stlLabel13) {
        this.stlLabel13 = stlLabel13;
    }

    /**
     * @return the stlRiskClasDays
     */
    public String getStlRiskClasDays() {
        return stlRiskClasDays;
    }

    /**
     * @param stlRiskClasDays the stlRiskClasDays to set
     */
    public void setStlRiskClasDays(String stlRiskClasDays) {
        this.stlRiskClasDays = stlRiskClasDays;
    }

    /**
     * @return the stlLabel7
     */
    public String getStlLabel7() {
        return stlLabel7;
    }

    /**
     * @param stlLabel7 the stlLabel7 to set
     */
    public void setStlLabel7(String stlLabel7) {
        this.stlLabel7 = stlLabel7;
    }

    /**
     * @return the stlLastVisitDate
     */
    public String getStlLastVisitDate() {
        return stlLastVisitDate;
    }

    /**
     * @param stlLastVisitDate the stlLastVisitDate to set
     */
    public void setStlLastVisitDate(String stlLastVisitDate) {
        this.stlLastVisitDate = stlLastVisitDate;
    }

    /**
     * @return the stlLabel8
     */
    public String getStlLabel8() {
        return stlLabel8;
    }

    /**
     * @param stlLabel8 the stlLabel8 to set
     */
    public void setStlLabel8(String stlLabel8) {
        this.stlLabel8 = stlLabel8;
    }

    /**
     * @return the stlLastVisitDays
     */
    public String getStlLastVisitDays() {
        return stlLastVisitDays;
    }

    /**
     * @param stlLastVisitDays the stlLastVisitDays to set
     */
    public void setStlLastVisitDays(String stlLastVisitDays) {
        this.stlLastVisitDays = stlLastVisitDays;
    }

    /**
     * @return the stlLabel9
     */
    public String getStlLabel9() {
        return stlLabel9;
    }

    /**
     * @param stlLabel9 the stlLabel9 to set
     */
    public void setStlLabel9(String stlLabel9) {
        this.stlLabel9 = stlLabel9;
    }

    /**
     * @return the stlNextVisitMaxDate
     */
    public String getStlNextVisitMaxDate() {
        return stlNextVisitMaxDate;
    }

    /**
     * @param stlNextVisitMaxDate the stlNextVisitMaxDate to set
     */
    public void setStlNextVisitMaxDate(String stlNextVisitMaxDate) {
        this.stlNextVisitMaxDate = stlNextVisitMaxDate;
    }

    /**
     * @return the stlLabel10
     */
    public String getStlLabel10() {
        return stlLabel10;
    }

    /**
     * @param stlLabel10 the stlLabel10 to set
     */
    public void setStlLabel10(String stlLabel10) {
        this.stlLabel10 = stlLabel10;
    }

    /**
     * @return the stlNextVisitMaxDays
     */
    public String getStlNextVisitMaxDays() {
        return stlNextVisitMaxDays;
    }

    /**
     * @param stlNextVisitMaxDays the stlNextVisitMaxDays to set
     */
    public void setStlNextVisitMaxDays(String stlNextVisitMaxDays) {
        this.stlNextVisitMaxDays = stlNextVisitMaxDays;
    }

    /**
     * @return the stlLabel11
     */
    public String getStlLabel11() {
        return stlLabel11;
    }

    /**
     * @param stlLabel11 the stlLabel11 to set
     */
    public void setStlLabel11(String stlLabel11) {
        this.stlLabel11 = stlLabel11;
    }

    /**
     * @return the stlZoneCodeName
     */
    public String getStlZoneCodeName() {
        return stlZoneCodeName;
    }

    /**
     * @param stlZoneCodeName the stlZoneCodeName to set
     */
    public void setStlZoneCodeName(String stlZoneCodeName) {
        this.stlZoneCodeName = stlZoneCodeName;
    }

    /**
     * @return the stlLabel12
     */
    public String getStlLabel12() {
        return stlLabel12;
    }

    /**
     * @param stlLabel12 the stlLabel12 to set
     */
    public void setStlLabel12(String stlLabel12) {
        this.stlLabel12 = stlLabel12;
    }

    /**
     * @return the stlPatientAddress
     */
    public String getStlPatientAddress() {
        return stlPatientAddress;
    }

    /**
     * @param stlPatientAddress the stlPatientAddress to set
     */
    public void setStlPatientAddress(String stlPatientAddress) {
        this.stlPatientAddress = stlPatientAddress;
    }

    /**
     * @return the strUUID
     */
    public String getStrUUID() {
        return strUUID;
    }

    /**
     * @param strUUID the strUUID to set
     */
    public void setStrUUID(String strUUID) {
        this.strUUID = strUUID;
    }

    /**
     * @return the elapsedTime
     */
    public Long getElapsedTime() {
        return elapsedTime;
    }

    /**
     * @param elapsedTime the elapsedTime to set
     */
    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    /**
     * @return the scspeciality
     */
    public long getScspeciality() {
        return scspeciality;
    }

    /**
     * @param scspeciality the scspeciality to set
     */
    public void setScspeciality(long scspeciality) {
        this.scspeciality = scspeciality;
    }

    /**
     * @return the startHour
     */
    public String getStartHour() {
        return startHour;
    }

    /**
     * @param startHour the startHour to set
     */
    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }
    
    
}

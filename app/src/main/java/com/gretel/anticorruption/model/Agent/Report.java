package com.gretel.anticorruption.model.Agent;

public class Report {

    private String myOfficer;
    private String myAuthority;
    private String myPlace;
    private String myReportDate;
    private String myReport;

    public Report(String officer, String authority, String place, String reportDate, String report){
        myOfficer = officer;
        myAuthority = authority;
        myPlace = place;
        myReportDate = reportDate;
        myReport = report;
    }

    public String getOfficer(){
        return myOfficer;
    }

    public String getAuthority(){
        return myAuthority;
    }

    public String getPlace(){
        return myPlace;
    }

    public String getReportDate(){
        return myReportDate;
    }

    public String getReport(){
        return myReport;
    }

}

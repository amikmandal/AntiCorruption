package com.gretel.anticorruption.model.Agent;

public class Report {

    private String officer;
    private String authority;
    private String place;
    private String reportDate;
    private String report;
    private String author;
    private Long up;
    private Long down;
    private Long timestamp;

    public Report(String author, String officer, String authority, String place, String reportDate, String report){
        this.author = author;
        this.officer = officer;
        this.authority = authority;
        this.place = place;
        this.reportDate = reportDate;
        this.report = report;
        up = 0L;
        down = 0L;
        timestamp = 0 - System.currentTimeMillis();
    }

    public String getOfficer(){
        return officer;
    }

    public String getAuthority(){
        return authority;
    }

    public String getPlace(){
        return place;
    }

    public String getReportDate(){
        return reportDate;
    }

    public String getReport(){
        return report;
    }

    public String getAuthor() { return author; }

    public Long getUp() { return up; }

    public Long getDown() { return down; }

    public Long getDiff() { return down - up; }

    public Long getTimestamp() { return timestamp; }

    public void upvote(){
        up++;
    }

    public void downvote(){
        down++;
    }


}

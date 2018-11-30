package com.example.alexis.starkr.model;

import android.util.Log;

public class Calendar {
    private String service_id;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;
    private String start_date;
    private String end_date;

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public static Calendar createObject(String str){
        int cpt = 1;
        Calendar c = new Calendar();
        for(String s : str.split(",")){
            s = s.substring(1,s.length()-1);
            switch (cpt){
                case 1:
                    c.setService_id(s);
                    break;
                case 2:
                    c.setMonday(s);
                    break;
                case 3:
                    c.setTuesday(s);
                    break;
                case 4:
                    c.setWednesday(s);
                    break;
                case 5:
                    c.setThursday(s);
                    break;
                case 6:
                    c.setFriday(s);
                    break;
                case 7:
                    c.setSaturday(s);
                    break;
                case 8:
                    c.setSunday(s);
                    break;
                case 9:
                    c.setStart_date(s);
                    break;
                case 10:
                    c.setEnd_date(s);
                    break;
            }
            cpt++;
        }
        return c;
    }
}

package com.example.alexis.starkr.model;

public class Stop {
    private String stop_id;
    private String stop_code;
    private String stop_name;
    private String stop_desc;
    private String stop_lat;
    private String stop_lon;
    private String zone_id;
    private String stop_url;
    private String location_type;
    private String parent_station;
    private String stop_timezone;
    private String wheelchair_boarding;

    public String getStop_id() {
        return stop_id;
    }

    public void setStop_id(String stop_id) {
        this.stop_id = stop_id;
    }

    public String getStop_code() {
        return stop_code;
    }

    public void setStop_code(String stop_code) {
        this.stop_code = stop_code;
    }

    public String getStop_name() {
        return stop_name;
    }

    public void setStop_name(String stop_name) {
        this.stop_name = stop_name;
    }

    public String getStop_desc() {
        return stop_desc;
    }

    public void setStop_desc(String stop_desc) {
        this.stop_desc = stop_desc;
    }

    public String getStop_lat() {
        return stop_lat;
    }

    public void setStop_lat(String stop_lat) {
        this.stop_lat = stop_lat;
    }

    public String getStop_lon() {
        return stop_lon;
    }

    public void setStop_lon(String stop_lon) {
        this.stop_lon = stop_lon;
    }

    public String getZone_id() {
        return zone_id;
    }

    public void setZone_id(String zone_id) {
        this.zone_id = zone_id;
    }

    public String getStop_url() {
        return stop_url;
    }

    public void setStop_url(String stop_url) {
        this.stop_url = stop_url;
    }

    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }

    public String getParent_station() {
        return parent_station;
    }

    public void setParent_station(String parent_station) {
        this.parent_station = parent_station;
    }

    public String getStop_timezone() {
        return stop_timezone;
    }

    public void setStop_timezone(String stop_timezone) {
        this.stop_timezone = stop_timezone;
    }

    public String getWheelchair_boarding() {
        return wheelchair_boarding;
    }

    public void setWheelchair_boarding(String wheelchair_boarding) {
        this.wheelchair_boarding = wheelchair_boarding;
    }

    public static Stop createObject(String str){
        int cpt = 1;
        Stop st = new Stop();
        for(String s : str.split(",")){
            s = s.substring(1,s.length()-1);
            switch (cpt){
                case 1:
                    st.setStop_id(s);
                    break;
                case 2:
                    st.setStop_code(s);
                    break;
                case 3:
                    st.setStop_name(s);
                    break;
                case 4:
                    st.setStop_desc(s);
                    break;
                case 5:
                    st.setStop_lat(s);
                    break;
                case 6:
                    st.setStop_lon(s);
                    break;
                case 7:
                    st.setZone_id(s);
                    break;
                case 8:
                    st.setStop_url(s);
                    break;
                case 9:
                    st.setLocation_type(s);
                    break;
                case 10:
                    st.setParent_station(s);
                    break;
                case 11:
                    st.setStop_timezone(s);
                    break;
                case 12:
                    st.setWheelchair_boarding(s);
                    break;
            }
            cpt++;
        }
        return st;
    }
}

package com.example.alexis.starkr.model;
public class Route {
    private String route_id;
    private String agency_id;
    private String route_short_name;
    private String route_long_name;
    private String route_desc;
    private String route_type;
    private String route_url;
    private String route_color;
    private String route_text_color;
    private String route_sort_order;

    public String getRoute_id() {
        return route_id;
    }
    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }
    public String getAgency_id() {
        return agency_id;
    }
    public void setAgency_id(String agency_id) {
        this.agency_id = agency_id;
    }
    public String getRoute_short_name() {
        return route_short_name;
    }
    public void setRoute_short_name(String route_short_name) {this.route_short_name = route_short_name;}
    public String getRoute_long_name() {
        return route_long_name;
    }
    public void setRoute_long_name(String route_long_name) {this.route_long_name = route_long_name;}
    public String getRoute_desc() {
        return route_desc;
    }
    public void setRoute_desc(String route_desc) {
        this.route_desc = route_desc;
    }
    public String getRoute_type() {
        return route_type;
    }
    public void setRoute_type(String route_type) {
        this.route_type = route_type;
    }
    public String getRoute_url() {
        return route_url;
    }
    public void setRoute_url(String route_url) {
        this.route_url = route_url;
    }
    public String getRoute_color() {
        return route_color;
    }
    public void setRoute_color(String route_color) {
        this.route_color = route_color;
    }
    public String getRoute_text_color() {
        return route_text_color;
    }
    public void setRoute_text_color(String route_text_color) {this.route_text_color = route_text_color;}
    public String getRoute_sort_order() {
        return route_sort_order;
    }
    public void setRoute_sort_order(String route_sort_order) {this.route_sort_order = route_sort_order;}

    public static Route createObject(String str){
        int cpt = 1;
        Route r = new Route();
        for(String s : str.split(",")){
            s = s.substring(1,s.length()-1);
            switch (cpt){
                case 1:
                    r.setRoute_id(s);
                    break;
                case 2:
                    r.setAgency_id(s);
                    break;
                case 3:
                    r.setRoute_short_name(s);
                    break;
                case 4:
                    r.setRoute_long_name(s);
                    break;
                case 5:
                    r.setRoute_desc(s);
                    break;
                case 6:
                    r.setRoute_type(s);
                    break;
                case 7:
                    r.setRoute_url(s);
                    break;
                case 8:
                    r.setRoute_color(s);
                    break;
                case 9:
                    r.setRoute_text_color(s);
                    break;
                case 10:
                    r.setRoute_sort_order(s);
                    break;
            }
            cpt++;
        }
        return r;
    }
}

package com.example.alexis.starkr.model;
public class Trip {
    private String route_id;
    private String service_id;
    private String trip_id;
    private String trip_headsign;
    private String trip_short_name;
    private String direction_id;
    private String block_id;
    private String wheelchair_accessible;
    private String bikes_allowed;

    public String getRoute_id() {
        return route_id;
    }
    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }
    public String getService_id() {
        return service_id;
    }
    public void setService_id(String service_id) {
        this.service_id = service_id;
    }
    public String getTrip_id() {
        return trip_id;
    }
    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }
    public String getTrip_headsign() {
        return trip_headsign;
    }
    public void setTrip_headsign(String trip_headsign) {
        this.trip_headsign = trip_headsign;
    }
    public String getTrip_short_name() {
        return trip_short_name;
    }
    public void setTrip_short_name(String trip_short_name) {this.trip_short_name = trip_short_name;}
    public String getDirection_id() {
        return direction_id;
    }
    public void setDirection_id(String direction_id) {
        this.direction_id = direction_id;
    }
    public String getBlock_id() {
        return block_id;
    }
    public void setBlock_id(String block_id) {
        this.block_id = block_id;
    }
    public String getWheelchair_accessible() {
        return wheelchair_accessible;
    }
    public void setWheelchair_accessible(String wheelchair_accessible) {this.wheelchair_accessible = wheelchair_accessible;}
    public String getBikes_allowed() {
        return bikes_allowed;
    }
    public void setBikes_allowed(String bikes_allowed) {
        this.bikes_allowed = bikes_allowed;
    }

    public static Trip createObject(String str){
        int cpt = 1;
        Trip t = new Trip();
        for(String s : str.split(",")){
            s = s.substring(1,s.length()-1);
            switch (cpt){
                case 1:
                    t.setRoute_id(s);break;
                case 2:
                    t.setService_id(s);break;
                case 3:
                    t.setTrip_id(s);break;
                case 4:
                    t.setTrip_headsign(s);break;
                case 5:
                    t.setTrip_short_name(s);break;
                case 6:
                    t.setDirection_id(s);break;
                case 7:
                    t.setBlock_id(s);break;
                case 8:
                    t.setWheelchair_accessible(s);break;
                case 9:
                    t.setBikes_allowed(s);break;
            }
            cpt++;
        }
        return t;
    }
}

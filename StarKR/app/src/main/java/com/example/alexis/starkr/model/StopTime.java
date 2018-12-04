package com.example.alexis.starkr.model;

public class StopTime {
    private String trip_id;
    private String arrival_time;
    private String departure_time;
    private String stop_id;
    private String stop_sequence;
    private String stop_headsign;
    private String pickup_type;
    private String drop_off_type;
    private String shape_dist_traveled;

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getStop_id() {
        return stop_id;
    }

    public void setStop_id(String stop_id) {
        this.stop_id = stop_id;
    }

    public String getStop_sequence() {
        return stop_sequence;
    }

    public void setStop_sequence(String stop_sequence) {
        this.stop_sequence = stop_sequence;
    }

    public String getStop_headsign() {
        return stop_headsign;
    }

    public void setStop_headsign(String stop_headsign) {
        this.stop_headsign = stop_headsign;
    }

    public String getPickup_type() {
        return pickup_type;
    }

    public void setPickup_type(String pickup_type) {
        this.pickup_type = pickup_type;
    }

    public String getDrop_off_type() {
        return drop_off_type;
    }

    public void setDrop_off_type(String drop_off_type) {
        this.drop_off_type = drop_off_type;
    }

    public String getShape_dist_traveled() {
        return shape_dist_traveled;
    }

    public void setShape_dist_traveled(String shape_dist_traveled) {
        this.shape_dist_traveled = shape_dist_traveled;
    }

    public static StopTime createObject(String str){
        int cpt = 0;
        StopTime st = new StopTime();
        for(String s : str.split(",")){
            s = s.substring(1,s.length()-1);
            switch (cpt){
                case 1:
                    st.setTrip_id(s);
                    break;
                case 2:
                    st.setArrival_time(s);
                    break;
                case 3:
                    st.setDeparture_time(s);
                    break;
                case 4:
                    st.setStop_id(s);
                    break;
                case 5:
                    st.setStop_sequence(s);
                    break;
                case 6:
                    st.setStop_headsign(s);
                    break;
                case 7:
                    st.setPickup_type(s);
                    break;
                case 8:
                    st.setDrop_off_type(s);
                    break;
                case 9:
                    st.setShape_dist_traveled(s);
                    break;
            }
            cpt++;
        }
        return st;
    }
}

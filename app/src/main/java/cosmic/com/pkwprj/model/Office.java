package cosmic.com.progressbartest;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Office implements Serializable {

    @SerializedName( "name" ) private String name;
    @SerializedName( "location" ) private String location;
    @SerializedName( "reservations" )private ArrayList<Reservations> reservations;

    public Office(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Office(String name, String location, ArrayList<Reservations> reservations) {
        this.name = name;
        this.location = location;
        this.reservations = reservations;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public class Reservations implements Serializable{
        @SerializedName( "startTime" )
        private String startTime;
        @SerializedName( "endTime" )
        private String endTime;

        public Reservations(String startTime, String endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }


    public ArrayList<Reservations> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Reservations> reservations) {
        this.reservations = reservations;
    }
}

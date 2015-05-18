package com.mathura.javed_000.famjam.utils;

/**
 * Created by javed_000 on 17/05/2015.
 */
public class PersonLocation {

        public String name;
        private String email;
        public     double longitude;
        public    double latitude;

        //constructor
        public PersonLocation(String name,String email, double longitude, double latitude) {
            this.name=name;
            this.setEmail(email);
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
}

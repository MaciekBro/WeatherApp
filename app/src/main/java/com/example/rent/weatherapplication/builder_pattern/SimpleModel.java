package com.example.rent.weatherapplication.builder_pattern;

/**
 * Created by RENT on 2017-03-16.
 */

public class SimpleModel {   //do wzorca projektowego Builder !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    private String name;
    private String surname;
    private String adress;
    private String phone;

    public SimpleModel(String name, String surname, String adress, String phone) {
        this.name = name;
        this.surname = surname;
        this.adress = adress;
        this.phone = phone;
    }

        public static  class Builder{           //wazne zeby byla statyczna!

        private String name;
        private String surname;
        private String adress;
        private String phone;

        public Builder withName(String name){
            this.name=name;
            return this;        //dzieki zwracaniu this mozemy to chainowac
        }

        public Builder withSurname (String surname){
            this.surname = surname;
            return this;
        }

        public Builder withAdress (String adress){
            this.adress = adress;
            return this;
        }
        public Builder withPhone (String phone){
            this.phone = phone;
            return this;
        }

        public SimpleModel build(){
            return new SimpleModel(name,surname,adress,phone);  //kolejność z konstruktora
        }
    }
}

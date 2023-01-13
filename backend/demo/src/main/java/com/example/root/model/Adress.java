package com.example.root.model;

import com.example.root.custom_annotation_handler.ValidAdressList;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Adress")
public class Adress implements Serializable {
    @Id
    @SequenceGenerator(name = "adress_sequence", sequenceName = "adress_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adress_sequence")
    private Long id;
    private String streetAdress;
    private String state;
    private String zipcode;
    private String city;

    public Adress() {
    }

    public Adress(String streetAdress, String state, String zipcode, String city) {
        this.streetAdress = streetAdress;
        this.state = state;
        this.zipcode = zipcode;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAdress() {
        return streetAdress;
    }

    public void setStreetAdress(String streetAdress) {
        this.streetAdress = streetAdress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
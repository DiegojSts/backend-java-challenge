package com.example.root.model;

import com.example.root.custom_annotation_handler.ValidAdressList;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "Adress")
public class Adress implements Serializable {
    @Id
    @SequenceGenerator(name = "adress_sequence", sequenceName = "adress_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adress_sequence")
    private Long id;

    @NotEmpty(message = "Rua é obrigatório!")
    private String streetAdress;
    @NotEmpty(message = "Estado é obrigatório!")
    private String state;
    @NotEmpty(message = "CEP é obrigatório!")
    private String zipcode;
    @NotEmpty(message = "Cidade é obrigatório!")
    private String city;

    private boolean mainAdress;

    @ManyToOne
    @JoinColumn(name = "person_id")
    Person person;

    public Adress() {
    }

    public Adress(String streetAdress, String state, String zipcode, String city, boolean mainAdress) {
        this.streetAdress = streetAdress;
        this.state = state;
        this.zipcode = zipcode;
        this.city = city;
        this.mainAdress = mainAdress;
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

    public boolean isMainAdress() {
        return mainAdress;
    }

    public void setMainAdress(boolean mainAdress) {
        this.mainAdress = mainAdress;
    }

//    public Person getPerson() {
//        return person;
//    }
//
//    public void setPerson(Person person) {
//        this.person = person;
//    }

    @Override
    public String toString() {
        return "Adress{" +
                "id=" + id +
                ", streetAdress='" + streetAdress + '\'' +
                ", state='" + state + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", mainAdress=" + mainAdress +
                '}';
    }
}
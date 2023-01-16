package com.example.root.model;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Numero é obrigatório!")
    private String number;
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

    public Adress(String streetAdress, String number, String zipcode, String city, boolean mainAdress) {
        this.streetAdress = streetAdress;
        this.number = number;
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
        return number;
    }

    public void setState(String number) {
        this.number = number;
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

    @Override
    public String toString() {
        return "Adress{" +
                "id=" + id +
                ", streetAdress='" + streetAdress + '\'' +
                ", number='" + number + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", mainAdress=" + mainAdress +
                '}';
    }
}
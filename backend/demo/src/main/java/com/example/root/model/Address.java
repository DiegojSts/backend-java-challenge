package com.example.root.model;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "Address")
public class Address implements Serializable {
    @Id
    @SequenceGenerator(name = "address_sequence", sequenceName = "address_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_sequence")
    private Long id;
    @NotNull
    @NotEmpty(message = "Rua é obrigatório!")
    private String streetAddress;

    @NotNull
    @NotEmpty(message = "Numero é obrigatório!")
    private String number;

    @NotNull
    @NotEmpty(message = "CEP é obrigatório!")
    private String zipcode;
    @NotNull
    @NotEmpty(message = "Cidade é obrigatório!")
    private String city;

    private boolean mainAddress;

    @ManyToOne
    @JoinColumn(name = "person_id")
    Person person;

    public Address() {
    }

    public Address(String streetAddress, String number, String zipcode, String city, boolean mainAddress) {
        this.streetAddress = streetAddress;
        this.number = number;
        this.zipcode = zipcode;
        this.city = city;
        this.mainAddress = mainAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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

    public boolean isMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(boolean mainAddress) {
        this.mainAddress = mainAddress;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", streetAddress='" + streetAddress + '\'' +

                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", mainAddress=" + mainAddress +
                '}';
    }
}
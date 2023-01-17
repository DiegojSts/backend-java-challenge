package com.example.root;

import com.example.root.model.Address;
import com.example.root.model.Person;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class DataBuilder {

    public static Address createSingleAddress() {
        return new Address(
                "Address1",
                "13",
                "000",
                "Sao Paulo",
                true
        );
    }

    public static Address createSingleAddress(String address,
                                            String number,
                                            String zipcode,
                                            String city,
                                            boolean mainAddress) {
        return new Address(
                address,
                number,
                zipcode,
                city,
                mainAddress
        );
    }

    public static List<Address> createTwoAddressesWithMainAddressTrue() {

        Address address1 = new Address(
                "Address1",
                "12",
                "000",
                "Sao Paulo",
                true
        );

        Address address2 = new Address(
                "Address2",
                "11",
                "000",
                "Santa Catarina",
                true
        );

        return List.of(address1, address2);
    }

    public static Person createSinglePerson() {
        return new Person(
                "NewName",
                LocalDate.of(2022, Month.DECEMBER, 30)
        );
    }

    public static Person createSinglePerson(int futureDate) {
        return new Person(
                "Robson Nunes",
                LocalDate.of(futureDate, Month.DECEMBER, 30)
        );
    }

        public static Person createSinglePerson(String name, int year) {
        return new Person(
                name,
                LocalDate.of(year, Month.APRIL, 3)
        );

    }


}

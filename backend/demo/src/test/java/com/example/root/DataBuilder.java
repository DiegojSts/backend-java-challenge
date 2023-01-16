package com.example.root;
import com.example.root.model.Adress;
import com.example.root.model.Person;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class DataBuilder {

    public static Adress createSingleAdress(){
        return  new Adress(
                "Address1",
                "SP",
                "000",
                "Sao Paulo",
                true
        );
    }

    public static List<Adress> createTwoAdressesWithMainAdressTrue(){

        Adress adress1 = new Adress(
                "Address1",
                "SP",
                "000",
                "Sao Paulo",
                true
        );

        Adress adress2 = new Adress(
                "Address2",
                "SC",
                "000",
                "Santa Catarina",
                true
        );

        return List.of(adress1, adress2);
    }

    public static Person createSinglePerson(){
       return new Person(
                "Robson Nunes",
                LocalDate.of(2022, Month.DECEMBER, 30)
        );
    }


    public static Person createSinglePerson(int futureDate){
       return new Person(
                "Robson Nunes",
                LocalDate.of(futureDate, Month.DECEMBER, 30)
        );
    }


}

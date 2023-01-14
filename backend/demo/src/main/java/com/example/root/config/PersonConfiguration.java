//package com.example.root.config;
//
//import com.example.root.model.Adress;
//import com.example.root.model.Person;
//import com.example.root.repository.PersonRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class PersonConfiguration {
//
//    @Bean
//    CommandLineRunner commandLineRunner (PersonRepository personRepository){
//        return args -> {
//            Adress adress1 = new Adress(
//                    "M.Navarro",
//                    "SP",
//                    "000",
//                    "Sao Paulo",
//                    true
//            );
//
//            Adress adress2 = new Adress(
//                    "Amazonas",
//                    "Ba",
//                    "333",
//                    "Salvador",
//                    false
//            );
//
//
//            Person person = new Person(
//                    "Diego",
//                    LocalDate.of(1997, Month.FEBRUARY, 1)
//            );
//
//            person.setAdress(List.of(adress1, adress2));
//            personRepository.saveAll(List.of(person));
//
//        };
//    }
//}

//package com.example.root.config;
//
//import com.example.root.model.Address;
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
//            Address address1 = new Address(
//                    "M.Navarro",
//                    "13",
//                    "000",
//                    "Sao Paulo",
//                    true
//            );
//
//            Address address2 = new Address(
//                    "Amazonas",
//                    "12",
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
//            person.setAddress(List.of(address1, address2));
//            personRepository.saveAll(List.of(person));
//
//        };
//    }
//}

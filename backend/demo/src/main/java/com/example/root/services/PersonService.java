package com.example.root.services;

import com.example.root.exception.BadRequestException;
import com.example.root.model.Adress;
import com.example.root.model.Person;
import com.example.root.repository.AdressRepository;
import com.example.root.repository.PersonRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final AdressRepository adressRepository;

    public PersonService(PersonRepository personRepository, AdressRepository adressRepository) {
        this.personRepository = personRepository;
        this.adressRepository = adressRepository;
    }

    public Person addPerson(Person person) {
        List<Adress> adresses = person.getAdress();

        long countTrue = adresses.stream().filter(Adress::isMainAdress).count();
        if (countTrue > 1) throw new BadRequestException("Deve existir apenas um endereço principal");

        return personRepository.save(person);
    }


    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }

    public Person findPersonById(Long id) throws IllegalArgumentException {

        Optional<Person> person = personRepository.findById(id);
        if(!person.isPresent())
            throw new BadRequestException("Pessoa de ID " + id + " não foi encontrada.");
        return person.get();
    }

    public List<Adress> findPersonAdressById(Long id) {
        Optional<Person> person = Optional.ofNullable(personRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Endereço de ID" + id + "não foi encontrado.")));

//        if(!person.isPresent())
//            throw new IllegalArgumentException("Endereço de ID " + id + " não foi encontrada.");

        return person.get().getAdress();
    }

//    public Person updatePerson(Long id, Person person) {
//        Optional<Person> optionalPerson = Optional.ofNullable(personRepository.findById(id)
//                .orElseThrow(() -> new IllegalStateException("Pessoa de ID" + id + "não foi encontrada.")));
//
////        if(!optionalPerson.isPresent())
////            throw new IllegalArgumentException("Pessoa de ID " + id + " não foi encontrada.");
//
////        Person existingPerson = new Person(person, id);
//
//        var listIds = optionalPerson.get().getAdress().stream().map(Adress::getId).collect(Collectors.toList());
//
////        Person existingPerson = optionalPerson.get();
////        existingPerson.setName(person.getName());
////        existingPerson.setBirthDate(person.getBirthDate());
////        existingPerson.setAdress(person.getAdress());
//
//        adressRepository.deleteAllById(listIds);
//        personRepository.save(existingPerson);
//
//        return existingPerson;
//    }

    public Person updatePerson(Long id, Person person) {
//        Optional<Person> optionalPerson = Optional.ofNullable(personRepository.findById(id)
//                .orElseThrow(() -> new IllegalStateException("Pessoa de ID" + id + "não foi encontrada.")));


//        Person existingPerson ;
//
//        var listIds = optionalPerson.get().getAdress().stream().map(Adress::getId).collect(Collectors.toList());
//
//        existingPerson = new Person(person, id);
//
//
//
//        personRepository.save(existingPerson);
//        adressRepository.deleteAllById(listIds);
//
//        return person;

        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Pessoa de ID" + id + "não foi encontrada."));

        existingPerson.setName(person.getName());
        existingPerson.setBirthDate(person.getBirthDate());

        List<Adress> newAdresses = person.getAdress();
        List<Adress> currentAdresses = existingPerson.getAdress();

        for(Adress newAdress: newAdresses) {
            boolean found = false;
            for(Adress currentAdress: currentAdresses) {
                if(newAdress.getId() == currentAdress.getId()) {
                    currentAdress.setStreetAdress(newAdress.getStreetAdress());
                    currentAdress.setState(newAdress.getState());
                    currentAdress.setZipcode(newAdress.getZipcode());
                    currentAdress.setCity(newAdress.getCity());
                    currentAdress.setMainAdress(newAdress.isMainAdress());
                    found = true;
                    break;
                }
            }
            if(!found) {
//                newAdress.setPerson(existingPerson);
                currentAdresses.add(newAdress);
            }

        }

        List<Adress> adressesToRemove = new ArrayList<>();
        for(Adress currentAdress: currentAdresses) {
            boolean found = false;
            for(Adress newAdress: newAdresses) {
                if(newAdress.getId() == currentAdress.getId()) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                adressesToRemove.add(currentAdress);
            }
        }
        currentAdresses.removeAll(adressesToRemove);
        existingPerson.setAdress(currentAdresses);

        personRepository.save(existingPerson);
        for (Adress adress: existingPerson.getAdress()) {
            adressRepository.save(adress);
        }
        return existingPerson;
    }
}





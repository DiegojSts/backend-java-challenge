package com.example.root.services;

import com.example.root.model.Adress;
import com.example.root.model.Person;
import com.example.root.repository.PersonRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person addPerson(Person person) {
        return personRepository.save(person);
    }

    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }

    public Person findPersonById(Long id) throws IllegalArgumentException {

        Optional<Person> person = personRepository.findById(id);
        if(!person.isPresent())
            throw new IllegalArgumentException("Pessoa de ID " + id + " não foi encontrada.");
        return person.get();
    }

    public List<Adress> findPersonAdressById(Long id) {
        Optional<Person> person = personRepository.findById(id);

        if(!person.isPresent())
            throw new IllegalArgumentException("Endereço de ID " + id + " não foi encontrada.");

        return person.get().getAdress();
    }

    public Person updatePerson(Long id, Person person) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if(!optionalPerson.isPresent())
            throw new IllegalArgumentException("Pessoa de ID " + id + " não foi encontrada.");

        Person existingPerson = optionalPerson.get();
        existingPerson.setName(person.getName());
        existingPerson.setBirthDate(person.getBirthDate());
        existingPerson.setAdress(person.getAdress());

        return personRepository.save(existingPerson);
    }


}
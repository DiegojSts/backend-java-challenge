package com.example.root.services;

import com.example.root.model.Person;
import com.example.root.repository.PersonRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
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
}
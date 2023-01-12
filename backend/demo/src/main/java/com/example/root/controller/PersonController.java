package com.example.root.controller;

import com.example.root.model.Person;
import com.example.root.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/person")
@CrossOrigin(origins = "*")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    //Retorna todos os cadastros do banco
    @GetMapping
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }

    //Busca um cadastro por id

    @GetMapping(path = "/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") Long id) {

        Person person = personService.findPersonById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);

    }

    //Cadastro de novos usuários
    @PostMapping(path = "/save")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        try {
            Person newPerson = personService.addPerson(person);
            return new ResponseEntity<>(newPerson, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

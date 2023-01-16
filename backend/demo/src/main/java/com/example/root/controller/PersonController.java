package com.example.root.controller;

import com.example.root.model.Adress;
import com.example.root.model.Person;
import com.example.root.services.PersonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/person")
@CrossOrigin(origins = "*")
@Api(value = "### Person API Rest")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    @ApiOperation(value = "Returns all persons")
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Returns a person by id")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") Long id) {
        Person person = personService.findPersonById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping(path = "adress/{id}")
    @ApiOperation(value = "Returns an adress by person id")
    public ResponseEntity<List<Adress>> findPersonAdressById(@PathVariable("id") Long id) {
        try {

            List<Adress> adress = personService.findPersonAdressById(id);
            return new ResponseEntity<>(adress, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/save")
    @ApiOperation(value = "Save a person in a database")
    public ResponseEntity<Person> addPerson(@Valid @RequestBody Person person) {

        Person newPerson = personService.addPerson(person);
        return new ResponseEntity<>(newPerson, HttpStatus.CREATED);

    }

    @PutMapping(path = "/update/{id}")
    @ApiOperation(value = "Updates a person by id")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @Valid @RequestBody Person person) {
        Person updatedPerson = personService.updatePerson(id, person);
        if (updatedPerson == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
    }

}

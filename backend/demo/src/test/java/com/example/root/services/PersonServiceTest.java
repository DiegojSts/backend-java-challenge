package com.example.root.services;

import com.example.root.exception.BadRequestException;
import com.example.root.model.Adress;
import com.example.root.model.Person;
import com.example.root.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@DataJpaTest
//@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PersonService personService;

    private Person person;



    @DisplayName("JUnit test for add a Person")
    @Test
    void canAddPerson() {
        //given
        Adress adress1 = new Adress(
                "M.Navarro",
                "SP",
                "000",
                "Sao Paulo",
                true
        );

        Person person1 = new Person(
                "Robson Nunes",
                LocalDate.of(2000, Month.DECEMBER, 30)
        );
        person1.setAdress(List.of(adress1));

        //when
        personService.addPerson(person1);

        //then
        ArgumentCaptor<Person> personArgumentCaptor =
                ArgumentCaptor.forClass(Person.class);

        verify(personRepository)
                .save(personArgumentCaptor.capture());

        Person capturedPerson = personArgumentCaptor.getValue();

        assertThat(capturedPerson).isEqualTo(person1);

    }

    @DisplayName("JUnit test to verify if a person has only one main adress")
    @Test
    void itShouldThrowWhenHaveMoreThanOneMainAdress() {
        //given
        Adress adress1 = new Adress(
                "M.Navarro",
                "SP",
                "000",
                "Sao Paulo",
                true
        );
        Adress adress2 = new Adress(
                "M.Navarro",
                "SP",
                "000",
                "Sao Paulo",
                true
        );
        Person person1 = new Person(
                "Robson Nunes",
                LocalDate.of(2000, Month.DECEMBER, 30)
        );
        person1.setAdress(List.of(adress1, adress2));

        //when
        //then
        assertThatThrownBy(() -> personService.addPerson(person1))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Deve existir apenas um endereço principal");

        verify(personRepository, never()).save(any());

    }

    @DisplayName("JUnit to get all persons")
    @Test
    void getAllPerson() {
        //given
            Adress adress1 = new Adress(
                    "M.Navarro",
                    "SP",
                    "000",
                    "Sao Paulo",
                    true
            );
            Adress adress2 = new Adress(
                    "Amazonas",
                    "Ba",
                    "333",
                    "Salvador",
                    true
            );

            Person person1 = new Person(
                    "Robson Nunes",
                    LocalDate.of(2000, Month.DECEMBER, 30)
            );
            Person person2 = new Person(
                    "Matheo",
                    LocalDate.of(2013, Month.MARCH, 25)
            );
            person1.setAdress(List.of(adress1));
            person2.setAdress(List.of(adress2));

            //when
            personService.getAllPerson();

             //then
            verify(personRepository).findAll();
    }

    @Test
    @DisplayName("JUnit test to get a person by id")
    public void testFindPersonByIdSuccess() {
      //given
        Long id = 1L;
        Adress adress1 = new Adress(
                "M.Navarro",
                "SP",
                "000",
                "Sao Paulo",
                true
        );
        Person person1 = new Person(
                "Robson Nunes",
                LocalDate.of(2000, Month.DECEMBER, 30)
        );
        person1.setAdress(List.of(adress1));
        personRepository.save(person1);

        BDDMockito.given(personRepository.findById(id)).willReturn(Optional.of(person1));

        //when
        Person savedPerson = personService.findPersonById(id);

        //then
        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getName()).isEqualTo(person1.getName());
        assertThat(savedPerson.getBirthDate()).isEqualTo(person1.getBirthDate());
        assertThat(savedPerson.getAdress()).isEqualTo(person1.getAdress());
    }

    @Test
    @DisplayName("JUnit to test if given an ID that doesnt exists throw a BadRequestException")
    public void itShouldThwrowErrorIfPersonIdDoesntExist() {
      //given
        Long id = 1L;
        Adress adress1 = new Adress(
                "M.Navarro",
                "SP",
                "000",
                "Sao Paulo",
                true
        );
        Person person1 = new Person(
                "Robson Nunes",
                LocalDate.of(2000, Month.DECEMBER, 30)
        );
        person1.setAdress(List.of(adress1));
        personRepository.save(person1);

        BDDMockito.given(personRepository.findById(id)).willReturn(Optional.of(person1));

        //when
        Person savedPerson = personService.findPersonById(id);

        //then
        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getName()).isEqualTo(person1.getName());
        assertThat(savedPerson.getBirthDate()).isEqualTo(person1.getBirthDate());
        assertThat(savedPerson.getAdress()).isEqualTo(person1.getAdress());
    }

    @Test
    @Disabled
    void findPersonAdressById() {
    }

    @Test
    @Disabled
    void updatePerson() {
    }
}
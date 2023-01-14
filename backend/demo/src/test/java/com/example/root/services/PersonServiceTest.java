package com.example.root.services;

import com.example.root.exception.BadRequestException;
import com.example.root.model.Adress;
import com.example.root.model.Person;
import com.example.root.repository.PersonRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.Mockito.verify;


@DataJpaTest
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PersonService underTest;

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
        underTest.addPerson(person1);

        //then
        ArgumentCaptor<Person> personArgumentCaptor =
                ArgumentCaptor.forClass(Person.class);

        verify(personRepository)
                .save(personArgumentCaptor.capture());

        Person capturedPerson = personArgumentCaptor.getValue();

        assertThat(capturedPerson).isEqualTo(person1);

    }

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
        assertThatThrownBy(() -> underTest.addPerson(person1))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Deve existir apenas um endereço principal");

    }

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
            underTest.getAllPerson();

             //then
            verify(personRepository).findAll();
    }

    @Test
    @Disabled
    void findPersonById() {
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
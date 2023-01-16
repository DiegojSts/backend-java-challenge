package com.example.root.services;

import static org.mockito.BDDMockito.*;

import com.example.root.DataBuilder;
import com.example.root.exception.BadRequestException;
import com.example.root.exception.NotFoundException;
import com.example.root.model.Adress;
import com.example.root.model.Person;
import com.example.root.repository.PersonRepository;
import org.assertj.core.error.ShouldContainExactlyInAnyOrder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

    @DisplayName("add a single Person with success")
    @Test
    void addPersonWithinSuccess() {
        //given
        Adress adress1 = DataBuilder.createSingleAdress();
        Person person1 = DataBuilder.createSinglePerson();
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

    @DisplayName("verify if a person has only one main address and throw a IllegalArgumentException if doesn't")
    @Test
    void itShouldThrowErrorWhenHaveMoreThanOneMainAdressTrue() {
        //given
        List<Adress> adresses = DataBuilder.createTwoAdressesWithMainAdressTrue();
        Person person1 = DataBuilder.createSinglePerson();
        person1.setAdress(adresses);

        //when
        //then
        assertThatThrownBy(() -> personService.addPerson(person1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Deve existir apenas um endereço principal");

        verify(personRepository, never()).save(any());
    }

    //TODO
    @DisplayName("verify if birthdate is future")
    @Test
    void itShouldNotSaveIfDateIsfuture() {
        //given
        Adress adress1 = DataBuilder.createSingleAdress();
        Person person1 = DataBuilder.createSinglePerson(2024);
        person1.setAdress(List.of(adress1));

        //when
        when(personService.addPerson(person1));
        //then
        verify(personRepository, never()).save(any());
    }

    @DisplayName("get all persons with success")
    @Test
    void getAllPersonWithSuccess() {
        //given
        Adress adress1 = DataBuilder.createSingleAdress();
        Adress adress2 = DataBuilder.createSingleAdress();
        Person person1 = DataBuilder.createSinglePerson();
        Person person2 = DataBuilder.createSinglePerson();

        person1.setAdress(List.of(adress1));
        person2.setAdress(List.of(adress2));

        //when
        when(personRepository.findAll()).thenReturn(List.of(person1, person2));
        List<Person> personList = personService.getAllPerson();

        //then
        assertThat(personList).isNotNull();
        assertThat(personList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("get a person by id with success")
    public void findPersonByIdSuccess() {
        //given
        Long id = 1L;
        Adress adress1 = DataBuilder.createSingleAdress();
        Person person1 = DataBuilder.createSinglePerson();
        person1.setAdress(List.of(adress1));
        personRepository.save(person1);

        given(personRepository.findById(id)).willReturn(Optional.of(person1));

        //when
        Person savedPerson = personService.findPersonById(id);

        //then
        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getName()).isEqualTo(person1.getName());
        assertThat(savedPerson.getBirthDate()).isEqualTo(person1.getBirthDate());
        assertThat(savedPerson.getAdress()).isEqualTo(person1.getAdress());
    }

    @Test
    @DisplayName("if a ID doesn't exist throw a NotFoundException")
    public void itShouldThwrowErrorIfPersonIdDoesntExist() {
        //given
        Long id = 1L;

        //when
        //then
        assertThatThrownBy(() -> personService.findPersonById(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Pessoa de ID " + id + " não foi encontrada");
    }

    @Test
    @DisplayName("")
    @Disabled
    void findPersonAdressById() {
    }

    @Test
    @DisplayName("")
    @Disabled
    void updatePerson() {
    }
}
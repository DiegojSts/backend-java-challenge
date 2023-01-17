package com.example.root.services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;
import com.example.root.DataBuilder;
import com.example.root.exception.NotFoundException;
import com.example.root.model.Address;
import com.example.root.model.Person;
import com.example.root.repository.AddressRepository;
import com.example.root.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
    @Mock

    private AddressRepository addressRepository;
    @InjectMocks
    private PersonService personService;

    @Test
    @DisplayName("add a single Person with success")
    void addPersonWithinSuccess() {
        //given
        Address address1 = DataBuilder.createSingleAddress();
        Person person = DataBuilder.createSinglePerson();
        person.setAddress(List.of(address1));

        //when
        personService.addPerson(person);

        //then
        ArgumentCaptor<Person> personArgumentCaptor =
                ArgumentCaptor.forClass(Person.class);

        verify(personRepository)
                .save(personArgumentCaptor.capture());

        Person capturedPerson = personArgumentCaptor.getValue();

        assertThat(capturedPerson).isEqualTo(person);

    }

    @Test
    @DisplayName("verify if a person has only one main address and throw a IllegalArgumentException if doesn't")
    void itShouldThrowErrorWhenHaveMoreThanOneMainAddressTrueInAddMethod() throws IllegalArgumentException{
        //given
        List<Address> addresses = DataBuilder.createTwoAddressesWithMainAddressTrue();
        Person person = DataBuilder.createSinglePerson();
        person.setAddress(addresses);

        //when
        //then
        assertThatThrownBy(() -> personService.addPerson(person))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Deve existir apenas um endereço principal");

        verify(personRepository, never()).save(any());
    }

    @Test
    @DisplayName("get all persons with success")
    void getAllPersonWithSuccess() {
        //given
        Address address1 = DataBuilder.createSingleAddress();
        Address address2 = DataBuilder.createSingleAddress();
        Person person = DataBuilder.createSinglePerson();
        Person person2 = DataBuilder.createSinglePerson();

        person.setAddress(List.of(address1));
        person2.setAddress(List.of(address2));

        //when
        when(personRepository.findAll()).thenReturn(List.of(person, person2));
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
        Address address1 = DataBuilder.createSingleAddress();
        Person person = DataBuilder.createSinglePerson();
        person.setAddress(List.of(address1));

        given(personRepository.findById(id)).willReturn(Optional.of(person));

        //when
        Person savedPerson = personService.findPersonById(id);

        //then
        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getName()).isEqualTo(person.getName());
        assertThat(savedPerson.getBirthDate()).isEqualTo(person.getBirthDate());
        assertThat(savedPerson.getAddress()).isEqualTo(person.getAddress());
    }

    @Test
    @DisplayName("if a ID doesn't exist throw a NotFoundException")
    public void itShouldThwrowErrorIfPersonIdDoesntExist() throws NotFoundException{
        //given
        Long id = 1L;

        //when
        //then
        assertThatThrownBy(() -> personService.findPersonById(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Pessoa de ID " + id + " não foi encontrada");
    }

    @Test
    @DisplayName("get an person address by given a person id")
    void findPersonAddressByPersonIdWithSuccess() {
        //given
        Long id = 1L;
        Address address1 = DataBuilder.createSingleAddress();
        Person person = DataBuilder.createSinglePerson();
        person.setAddress(List.of(address1));

        given(personRepository.findById(id)).willReturn(Optional.of(person));

        //when
        Person savedPerson = personService.findPersonById(id);

        //then
        assertThat(savedPerson.getAddress()).isNotNull();
        assertThat(savedPerson.getAddress().size()).isGreaterThan(0);
        assertEquals(address1, savedPerson.getAddress().get(0));

    }

    @Test
    @DisplayName("test if id exists to verify address from person")
    public void itShouldThwrowErrorIfPersonIdDoesntExistInFindAddressByPersonId() throws NotFoundException{
        //given
        Long id = 1L;

        //when
        //then
        assertThatThrownBy(() -> personService.findPersonAddressById(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Busque por um novo ID, essa pessoa não está cadastrada");
    }

    @Test
    @DisplayName("updates a pre-existing person")
    void updatePersonWithSuccess() {
        // given
        Long id = 1L;
        Address address = DataBuilder.createSingleAddress();
        Person person = DataBuilder.createSinglePerson();
        person.setAddress(List.of(address));
        person.setName("Atualizado");
        person.setBirthDate(LocalDate.of(2021, Month.APRIL, 21));
        given(personRepository.findById(id)).willReturn(Optional.of(person));

        // when
        personService.updatePerson(id, person);

        // then
        verify(personRepository, times(1)).save(person);
        assertThat(person.getName()).isEqualTo("Atualizado");
        assertThat(person.getBirthDate()).isEqualTo(LocalDate.of(2021, Month.APRIL, 21));
    }

    @Test
    @DisplayName("returns an error if passed id doesnt exist to be updated")
    public void itShouldThrowNotFoundExceptionIfAIdDoesntExist() throws NotFoundException{
        Long id = 2L;
        Address address = DataBuilder.createSingleAddress();
        Person person = DataBuilder.createSinglePerson();
        person.setAddress(List.of(address));

        // when
        assertThatThrownBy(() -> personService.updatePerson(id, person))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Pessoa de ID " + id + " não foi encontrada");
        verify(personRepository, never()).save(any());
    }
    @Test
    @DisplayName("verify if a person has only one main address and throw a IllegalArgumentException if doesn't")
    void itShouldThrowErrorWhenHaveMoreThanOneMainAddressTrueInUpdateMethod() throws IllegalArgumentException{
        //given
        Long id = 1L;
        List<Address> addresses = DataBuilder.createTwoAddressesWithMainAddressTrue();
        Person person = DataBuilder.createSinglePerson();
        person.setAddress(addresses);
        given(personRepository.findById(id)).willReturn(Optional.of(person));

        //when
        //then
        assertThatThrownBy(() -> personService.updatePerson(1L, person))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Deve existir apenas um endereço principal");

        verify(personRepository, never()).save(any());
    }

    @Test
    @DisplayName("deletes a person with success")
    public void deletePersonWithSuccess(){
        //given
        Long id = 1L;
        Address address = DataBuilder.createSingleAddress();
        Person person = DataBuilder.createSinglePerson();
        person.setAddress(List.of(address));
        given(personRepository.findById(id)).willReturn(Optional.of(person));

        //when
        personService.deletePersonById(id);
        //then
        verify(personRepository, times(1)).deleteById(id);

    }


    @Test
    @DisplayName("throws NotFoundException if id passed in delete doesnt exist")
    public void itShouldThwrowErrorIfPersonIdDoesntExistInDeleteMethod() throws NotFoundException{
        //given
        Long id = 1L;

        //when
        //then
        assertThatThrownBy(() -> personService.deletePersonById(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Pessoa de ID " + id + " não foi encontrada");
    }



}
package com.example.root.services;
import com.example.root.exception.NotFoundException;
import com.example.root.model.Address;
import com.example.root.model.Person;
import com.example.root.repository.AddressRepository;
import com.example.root.repository.PersonRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    public PersonService(PersonRepository personRepository, AddressRepository addressRepository) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
    }

    public Person addPerson(Person person) throws IllegalArgumentException{
        List<Address> addresses = person.getAddress();

        long countTrue = addresses.stream().filter(Address::isMainAddress).count();
        if (countTrue > 1) throw new IllegalArgumentException("Deve existir apenas um endereço principal");

        return personRepository.save(person);

    }

    public List<Person> getAllPerson() {
         return personRepository.findAll();
    }

    public Person findPersonById(Long id) throws NotFoundException {

        Optional<Person> person = personRepository.findById(id);
        if(!person.isPresent())
            throw new NotFoundException("Pessoa de ID " + id + " não foi encontrada");
        return person.get();
    }

    public List<Address> findPersonAddressById(Long id) throws NotFoundException{
        Optional<Person> person = Optional.ofNullable(personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Busque por um novo ID, essa pessoa não está cadastrada")));

        return person.get().getAddress();
    }

    public Person updatePerson(Long id, Person person) throws  NotFoundException, IllegalArgumentException{
        List<Address> addresses = person.getAddress();

        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pessoa de ID " + id + " não foi encontrada"));

        long countTrue = addresses.stream().filter(Address::isMainAddress).count();
        if (countTrue > 1) throw new IllegalArgumentException("Deve existir apenas um endereço principal");


        existingPerson.setName(person.getName());
        existingPerson.setBirthDate(person.getBirthDate());

        List<Address> newAddresses = person.getAddress();
        List<Address> currentAddresses = existingPerson.getAddress();


        for(Address newAddress: newAddresses) {
            boolean found = false;
            for(Address currentAddress: currentAddresses) {
                if(newAddress.getId() == currentAddress.getId()) {
                    currentAddress.setStreetAddress(newAddress.getStreetAddress());
                    currentAddress.setNumber(newAddress.getNumber());
                    currentAddress.setZipcode(newAddress.getZipcode());
                    currentAddress.setCity(newAddress.getCity());
                    currentAddress.setMainAddress(newAddress.isMainAddress());
                    found = true;
                    break;
                }
            }
            if(!found) {
                currentAddresses.add(newAddress);
            }

        }

        List<Address> addressesToRemove = new ArrayList<>();
        for(Address currentAddress: currentAddresses) {
            boolean found = false;
            for(Address newAddress: newAddresses) {
                if(newAddress.getId() == currentAddress.getId()) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                addressesToRemove.add(currentAddress);
            }
        }
        existingPerson.setAddress(currentAddresses);

        personRepository.save(existingPerson);
        for (Address address: existingPerson.getAddress()) {
            addressRepository.save(address);
        }
        return existingPerson;
    }

    public void deletePersonById(Long id) {
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pessoa de ID " + id + " não foi encontrada"));

        personRepository.deleteById(id);
    }
}





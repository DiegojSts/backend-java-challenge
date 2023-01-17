package com.example.root.model;

import com.example.root.custom_annotation_handler.ValidAddressList;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person implements Serializable {
    @Id
    @SequenceGenerator(name = "person_sequence", sequenceName = "person_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_sequence")
    private Long id;

    @NotBlank(message = "Nome é obrigatório!")
    private String name;

    @NotNull(message = "Data de nascimento é obrigatório")
    @Past(message = "Data de aniversário não pode ser futura!")
    private LocalDate birthDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "person_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Size(min = 1, message = "Obrigatório ao menos 1 endereço!")
//    @ValidAddressList
    private List<@Valid Address> address = new ArrayList<>();

    public Person() {
    }

    public Person(String name, LocalDate birthDate, List<Address> address) {
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
    }

    public Person(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public Person(Person person, Long id){
        this.id = id;
        this.name = person.getName();
        this.birthDate = person.getBirthDate();
        this.address = person.getAddress();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Address> getAddress() {
        return address;
    }


    public void setAddress(List<Address> address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", address=" + address +
                '}';
    }
}

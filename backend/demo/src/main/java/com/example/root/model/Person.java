package com.example.root.model;

import com.example.root.custom_annotation_handler.ValidAdressList;
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
//    @ValidAdressList
    private List<@Valid Adress> adress = new ArrayList<>();

    public Person() {
    }

    public Person(String name, LocalDate birthDate, List<Adress> adress) {
        this.name = name;
        this.birthDate = birthDate;
        this.adress = adress;
    }

    public Person(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public Person(Person person, Long id){
        this.id = id;
        this.name = person.getName();
        this.birthDate = person.getBirthDate();
        this.adress = person.getAdress();
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

    public List<Adress> getAdress() {
        return adress;
    }


    public void setAdress(List<Adress> adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", adress=" + adress +
                '}';
    }
}

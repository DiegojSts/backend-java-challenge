package com.example.root.model;

import com.example.root.custom_annotation_handler.ValidAdressList;

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

    @NotNull
    @Past(message = "Data de aniversário não pode ser futura!")
    private LocalDate birthDate;

    @OneToMany(targetEntity = Adress.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "person_adress_fk", referencedColumnName = "id")
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
}

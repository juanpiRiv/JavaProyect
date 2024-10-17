package edu.coder.house.fact.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Client {
    public Client(UUID id) {
        this.id = id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Client(String dni, String name, String lastname, String mail, String telefono) {
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.telefono = telefono;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true)

    private String dni;

    @Column(nullable = false)

    private String name;

    @Column(nullable = false)

    private String lastname;

    @Column(nullable = false, unique = true)

    private String mail;

    @Column
    private String telefono;

    public void setName(String name) {
        this.name = name;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setEmail(String email) {
        this.mail = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public UUID getId() {
        return id;
    }
}

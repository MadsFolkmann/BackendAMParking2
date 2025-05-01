package org.parking.backendamparking.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String number;
    private String lejemaal;


    public User() {
    }

    public User(String name, String email, String password, String number, String lejemaal) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.number = number;
        this.lejemaal = lejemaal;
    }
}

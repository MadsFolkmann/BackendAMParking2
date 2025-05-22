package org.parking.backendamparking.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "`case`") // Escaping the table name
public class Case {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate time;
    private String description;
    private Boolean done;
    @ManyToOne
    @JoinColumn(name = "pvagt_id", nullable = false)
    private User user;
}

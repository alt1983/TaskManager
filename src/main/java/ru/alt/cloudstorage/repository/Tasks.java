package ru.alt.cloudstorage.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks", schema = "manager")
public class Tasks{
    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String header;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String priority;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String executor;
}


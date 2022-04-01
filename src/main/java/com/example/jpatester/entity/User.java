package com.example.jpatester.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

import static java.util.Collections.emptySet;

@Entity
@Setter
@Getter
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Article> articles = emptySet();

}

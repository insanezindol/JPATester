package com.example.jpatester.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tb_album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false)
    private String singer;

    @Column(columnDefinition = "TEXT", length = 200, nullable = false)
    private String albumTitle;

    //    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // 1번 상황
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // 2번 상황
//    @JoinColumn(name = "album_id")
    private Set<Song> songs = new HashSet<>();

}

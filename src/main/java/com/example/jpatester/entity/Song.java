package com.example.jpatester.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tb_song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int track;

    @Column(length = 200, nullable = false)
    private String title;

//    @ManyToOne(fetch = FetchType.LAZY) // 1번상황
//    @ManyToOne(fetch = FetchType.EAGER) // 2번상황
    @ManyToOne(fetch = FetchType.LAZY) // 1번상황
    @JoinColumn(name = "album_id")
    private Album album;

}

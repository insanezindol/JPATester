package com.example.jpatester.controller;

import com.example.jpatester.entity.Album;
import com.example.jpatester.entity.Song;
import com.example.jpatester.model.ResponseInfo;
import com.example.jpatester.service.JpaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jpa")
@Slf4j
public class JpaController {

    private final JpaService jpaService;

    public JpaController(JpaService jpaService) {
        this.jpaService = jpaService;
    }

    @GetMapping("/album")
    public ResponseInfo getAlbum() {
        log.info("getAlbum");
        ResponseInfo output = new ResponseInfo();
        output.setData(jpaService.getAlbums());
        return output;
    }

    @GetMapping("/album/{albumId}")
    public ResponseInfo getAlbumById(@PathVariable String albumId) {
        log.info("getAlbumById");
        ResponseInfo output = new ResponseInfo();
        output.setData(jpaService.getAlbum(Long.parseLong(albumId)));
        return output;
    }

    @PostMapping("/album")
    public ResponseInfo postAlbum(@RequestBody Album album) {
        log.info("postAlbum");
        jpaService.insertAlbum(album);
        return new ResponseInfo();
    }

    @PutMapping("/album/{albumId}")
    public ResponseInfo putAlbum(@PathVariable String albumId, @RequestBody Album album) {
        log.info("putAlbum");
        jpaService.updateAlbum(Long.parseLong(albumId), album);
        return new ResponseInfo();
    }

    @DeleteMapping("/album/{albumId}")
    public ResponseInfo deleteAlbum(@PathVariable String albumId) {
        log.info("deleteAlbum");
        jpaService.deleteAlbum(Long.parseLong(albumId));
        return new ResponseInfo();
    }


    @GetMapping("/song")
    public ResponseInfo getSong() {
        log.info("getSong");
        ResponseInfo output = new ResponseInfo();
        output.setData(jpaService.getSongs());
        return output;
    }

    @GetMapping("/song/{songId}")
    public ResponseInfo getSongById(@PathVariable String songId) {
        log.info("getSongById");
        ResponseInfo output = new ResponseInfo();
        output.setData(jpaService.getSong(Long.parseLong(songId)));
        return output;
    }

    @PostMapping("/song")
    public ResponseInfo postSong(@RequestBody Song song) {
        log.info("postSong");
        jpaService.insertSong(song);
        return new ResponseInfo();
    }

    @PutMapping("/song/{songId}")
    public ResponseInfo putSong(@PathVariable String songId, @RequestBody Song song) {
        jpaService.updateSong(Long.parseLong(songId), song);
        return new ResponseInfo();
    }

    @DeleteMapping("/song/{songId}")
    public ResponseInfo deleteSong(@PathVariable String songId) {
        jpaService.deleteSong(Long.parseLong(songId));
        return new ResponseInfo();
    }

}

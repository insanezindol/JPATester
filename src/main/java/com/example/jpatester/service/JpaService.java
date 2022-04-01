package com.example.jpatester.service;

import com.example.jpatester.entity.Album;
import com.example.jpatester.entity.Song;
import com.example.jpatester.repository.AlbumRepository;
import com.example.jpatester.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JpaService {

    private final AlbumRepository albumRepository;

    private final SongRepository songRepository;

    public JpaService(SongRepository songRepository, AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
    }

    @Transactional
    public List<Album> getAlbums() {
        return albumRepository.findAll();
    }

    @Transactional
    public Album getAlbum(long albumId) {
        return albumRepository.findById(albumId).orElse(null);
    }

    @Transactional
    public Album insertAlbum(Album album) {
        return albumRepository.save(album);
    }

    @Transactional
    public Album updateAlbum(long albumId, Album album) {
        album.setId(albumId);
        return albumRepository.save(album);
    }

    @Transactional
    public void deleteAlbum(long id) {
        Album album = new Album();
        album.setId(id);
        albumRepository.delete(album);
    }

    @Transactional
    public List<Song> getSongs() {
        return songRepository.findAll();
    }

    @Transactional
    public Song getSong(long songId) {
        return songRepository.findById(songId).orElse(null);
    }

    @Transactional
    public Song insertSong(Song song) {
        return songRepository.save(song);
    }

    @Transactional
    public Song updateSong(long songId, Song song) {
        song.setId(songId);
        return songRepository.save(song);
    }

    @Transactional
    public void deleteSong(long id) {
        Song song = new Song();
        song.setId(id);
        songRepository.delete(song);
    }

}

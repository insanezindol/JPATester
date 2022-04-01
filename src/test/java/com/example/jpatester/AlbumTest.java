package com.example.jpatester;

import com.example.jpatester.entity.Album;
import com.example.jpatester.entity.Song;
import com.example.jpatester.repository.AlbumRepository;
import com.example.jpatester.repository.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@SpringBootTest
@Slf4j
public class AlbumTest {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    SongRepository songRepository;

    private final static int _LOOP = 3;

    @BeforeEach
    void setup() {
        log.info("[BEG] setup");
        // given
        String singer = "aespa";
        String albumTitle = "aespa album";
        String[] titles = {"black mamba", "savage", "next level", "dreams come true"};
        for (int i = 0; i < _LOOP; i++) {
            Album album = new Album();
            album.setSinger(singer);
            album.setAlbumTitle(albumTitle);
            albumRepository.save(album);

            int songIdx = 1;
            for (String title : titles) {
                Song song = new Song();
                song.setTrack(songIdx++);
                song.setTitle(title);
                song.setAlbum(album);
                songRepository.save(song);
            }
        }

//        for (int i = 0; i < _LOOP; i++) {
//            Album album = new Album();
//            album.setSinger(singer);
//            album.setAlbumTitle(albumTitle);
//            int songIdx = 1;
//            Set<Song> songs = new LinkedHashSet<>();
//            for (String title : titles) {
//                Song song = new Song();
//                song.setTrack(songIdx++);
//                song.setTitle(title);
//                song.setAlbum(album);
//                songs.add(song);
//            }
//            album.setSongs(songs);
//            albumRepository.save(album);
//        }
        log.info("[END] setup");
    }

    @AfterEach
    void cleanup() {
        log.info("[BEG] cleanup");
        songRepository.deleteAll();
        albumRepository.deleteAll();
        log.info("[END] cleanup");
    }

    @DisplayName("Album의 Song에 접근하지 않은 경우")
    @Test
    void findAlbumWithoutSongs() {
        log.info("[BEG] findAlbumWithoutSongs");
        // when
        List<Album> albums = albumRepository.findAll();

        for (Album album : albums) {
            log.info("[album] {}, {}, {}", album.getId(), album.getSinger(), album.getAlbumTitle());
        }

        // then
        assert albums.size() == _LOOP;
        log.info("[END] findAlbumWithoutSongs");

        // (1) LAZY 방식 결과 : Album.java:26 : OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
        // 하위 엔티티에 접근하지 않았기 때문에 Album만 가져오는 것을 볼 수 있습니다.
        // Hibernate: select album0_.id as id1_0_, album0_.album_title as album_ti2_0_, album0_.singer as singer3_0_ from album album0_

        // (2) EAGER 방식 결과 : Album.java:27 : @OneToMany(mappedBy = "album", fetch = FetchType.EAGER)
        // N+1 발생 : JPQL에서 동작한 쿼리를 통해서 Album 데이터를 조회합니다. 그 이후 JPA에서는 글로벌 패치 전략(EAGER 로딩)을 보고 Album의 Song 대해서 추가적인 로딩 작업을 진행해 N+1 문제를 발생시킵니다.
        // Hibernate: select album0_.id as id1_0_, album0_.album_title as album_ti2_0_, album0_.singer as singer3_0_ from album album0_
        // Hibernate: select songs0_.album_id as album_id4_1_0_, songs0_.id as id1_1_0_, songs0_.id as id1_1_1_, songs0_.album_id as album_id4_1_1_, songs0_.title as title2_1_1_, songs0_.track as track3_1_1_ from song songs0_ where songs0_.album_id=?
        // Hibernate: select songs0_.album_id as album_id4_1_0_, songs0_.id as id1_1_0_, songs0_.id as id1_1_1_, songs0_.album_id as album_id4_1_1_, songs0_.title as title2_1_1_, songs0_.track as track3_1_1_ from song songs0_ where songs0_.album_id=?
    }

    @DisplayName("Album의 Song에 접근하는 경우")
    @Test
    @Transactional
    // LAZY 로딩을 하기 위해서는 해당 Entity가 영속 상태여야 합니다.
    // 보통 Repository에서 리스트로 가져오면 영속이 끊긴 상태로 가져오기 때문에 LAZY 전략 테스팅 시 @Transactional를 꼭 사용해야합니다
    // org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.example.jpatester.entity.Album.songs, could not initialize proxy - no Session
    void findAlbumWithSongs() {
        log.info("[BEG] findAlbumWithSongs");
        // when
        List<Album> albums = albumRepository.findAll();

        for (Album album : albums) {
            log.info("[album] {}, {}, {}, {}", album.getId(), album.getSinger(), album.getAlbumTitle(), album.getSongs());
            for (Song song : album.getSongs()) {
                log.info("[song] {}, {}, {}, {}", song.getId(), song.getTrack(), song.getTitle());
            }
        }

        List<Song> songs = songRepository.findAll();

        for (Song song : songs) {
            log.info("[song] {}, {}, {}, {}", song.getId(), song.getAlbum().getId(), song.getTitle(), song.getTrack());
        }

        Album album = albumRepository.findById(1L).orElse(null);
        log.info("[album] {}, {}, {}", album.getId(), album.getSinger(), album.getAlbumTitle());
        Set<Song> songSet = album.getSongs();
        log.info("[song] {}", songSet.toString());

        // then
        assert albums.size() == _LOOP;
        log.info("[END] findAlbumWithSongs");
    }

    @DisplayName("앨범, 노래 저장 후 노래 조회 테스트")
    @Test
//    @Transactional
    void saveSongTest() {
        log.info("[BEG] saveSongTest");

        List<Album> albums = albumRepository.findAllJoinFetch();
        for (Album album : albums) {
            log.info("[album : {}, {}, {}], [song : {}]", album.getId(), album.getSinger(), album.getAlbumTitle(), album.getSongs().size());
        }

        // then
        assert albums.size() == _LOOP;
        log.info("[END] saveSongTest");
    }

}


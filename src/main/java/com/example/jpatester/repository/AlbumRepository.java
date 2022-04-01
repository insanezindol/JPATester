package com.example.jpatester.repository;

import com.example.jpatester.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("select DISTINCT a from Album a join fetch a.songs")
    List<Album> findAllJoinFetch();

}

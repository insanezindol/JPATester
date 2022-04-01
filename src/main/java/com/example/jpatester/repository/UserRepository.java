package com.example.jpatester.repository;

import com.example.jpatester.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select distinct u from User u left join u.articles")
    List<User> findAllJPQL();

    @Query("select distinct u from User u left join fetch u.articles")
    List<User> findAllJPQLFetch();

    User findByName(String name);

    @EntityGraph(attributePaths = {"articles"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select distinct u from User u left join u.articles")
    List<User> findAllEntityGraph();

    @EntityGraph(attributePaths = {"articles"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select distinct u from User u left join u.articles")
    Page<User> findAllPage(Pageable pageable);

}

package com.example.jpatester;


import com.example.jpatester.entity.Article;
import com.example.jpatester.entity.User;
import com.example.jpatester.repository.ArticleRepository;
import com.example.jpatester.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@SpringBootTest
@Slf4j
public class UserTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    private final static int _LOOP = 100;

    @BeforeEach
    void setup() {
        log.info("[BEG] setup");
        // given
        String name = "dean";
        String[] titles = {"black mamba", "savage", "next level", "dreams come true"};
        for (int i = 0; i < _LOOP; i++) {
            User user = new User();
            user.setName(name + "_" + i);
            userRepository.save(user);

            for (String title : titles) {
                Article article = new Article();
                article.setTitle(title);
                article.setUser(user);
                articleRepository.save(article);
            }
        }
        log.info("[END] setup");
    }

    // @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    // @ManyToOne(fetch = FetchType.EAGER)

    @Test
    @DisplayName("Eager type은 User를 단일 조회할 때 join문이 날아간다.")
    void userSingleFindTest() {
        log.info("== start ==");
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        log.info("== end ==");
        log.info(user.getName());
    }

    @Test
    @DisplayName("Eager type은 User를 전체 검색할 때 N+1문제가 발생한다.")
    void userFindTestForEager() {
        log.info("== start ==");
        List<User> users = userRepository.findAll();
        log.info("== find all ==");
    }

    @Test
    @DisplayName("Lazy type은 User 검색 후 필드 검색을 할 때 N+1문제가 발생한다.")
    @Transactional
    void userFindTestForLazy() {
        log.info("== start ==");
        List<User> users = userRepository.findAll();
        log.info("== find all ==");
        for (User user : users) {
            log.info("length : {}", user.getArticles().size());
        }
    }

    @Test
    @DisplayName("일반 jpql 쿼리문은 N+1문제가 발생한다.")
    @Transactional
    void normalJpqlTest() {
        log.info("== start ==");
        User user = userRepository.findByName("dean_1");
        log.info("== end ==");
        log.info("user : {}", user);
        log.info("length : {}", user.getArticles().size());
    }

    @Test
    @DisplayName("fetch join을 하면 N+1문제가 발생하지 않는다.")
    @Transactional
    void fetchJoinTest() {
        log.info("== start ==");
        List<User> users = userRepository.findAllJPQLFetch();
        log.info("== find all ==");
        for (User user : users) {
            log.info("length : {}", user.getArticles().size());
        }
    }

    @Test
    @DisplayName("EntityGraph 테스트")
    @Transactional
    void entityGraphTest() {
        log.info("== start ==");
        List<User> users = userRepository.findAllEntityGraph();
        log.info("== find all ==");
        for (User user : users) {
            log.info("length : {}", user.getArticles().size());
        }
    }

    @Test
    @DisplayName("PageRequest 테스트 (One To Many)")
    void pagingFetchJoinOneToManyTest() {
        log.info("== start ==");
        PageRequest pageRequest = PageRequest.of(1, 5);
        Page<User> users = userRepository.findAllPage(pageRequest);
        log.info("== find all ==");
        for (User user : users) {
            Set<Article> articles = user.getArticles();
            for (Article article : articles) {
                log.info("article : {}, {}, {}", user.getName(), article.getId(), article.getTitle());
            }
        }
    }

    @Test
    @DisplayName("PageRequest 테스트 (Many To One)")
    void pagingFetchJoinManyToOneTest() {
        log.info("== start ==");
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Article> articles = articleRepository.findAllPage(pageRequest);
        log.info("== find all ==");
        for (Article article : articles) {
            User user = article.getUser();
            log.info("article : {}, {}, {}", article.getId(), article.getTitle(), user.getName());
        }
    }

}

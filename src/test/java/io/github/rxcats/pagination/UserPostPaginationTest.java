package io.github.rxcats.pagination;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

import io.github.rxcats.pagination.entity.PageResult;
import io.github.rxcats.pagination.entity.UserPost;
import io.github.rxcats.pagination.repository.UserPostRepository;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserPostPaginationTest {

    @Autowired
    private UserPostRepository repository;

    @Before
    public void setup() {

        for (int i = 1; i <= 1000; i++) {
            repository.remove("1001", i);
            repository.insert(UserPost.of("1001", i));
        }

    }

    @Test
    public void paginationTest() {

        PageResult<UserPost> paging = repository.findPaging("1001", 0, 100);
        log.info("nextNo:{}", paging.getNextNo());
        log.info("hasNext:{}", paging.isHasNext());
        log.info("size:{}", paging.getSize());
        paging.getContent().forEach(content -> log.info("content:{}", content));
        assertThat(paging).isNotNull();
        assertThat(paging.getNextNo()).isEqualTo(900);
        assertThat(paging.isHasNext()).isTrue();
        assertThat(paging.getSize()).isEqualTo(100);

    }

    @Test
    public void paginationTest2() {

        PageResult<UserPost> paging = repository.findPaging("1001", 5, 10);
        log.info("nextNo:{}", paging.getNextNo());
        log.info("hasNext:{}", paging.isHasNext());
        log.info("size:{}", paging.getSize());
        paging.getContent().forEach(content -> log.info("content:{}", content));
        assertThat(paging).isNotNull();
        assertThat(paging.getNextNo()).isEqualTo(0);
        assertThat(paging.isHasNext()).isFalse();
        assertThat(paging.getSize()).isEqualTo(5);

    }

}

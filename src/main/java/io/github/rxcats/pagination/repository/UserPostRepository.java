package io.github.rxcats.pagination.repository;

import io.github.rxcats.pagination.entity.PageResult;
import io.github.rxcats.pagination.entity.UserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserPostRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void insert(UserPost userPost) {
        mongoTemplate.insert(userPost);
    }

    public void remove(String userId, long no) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(no));
        query.addCriteria(Criteria.where("userId").is(userId));
        mongoTemplate.remove(query, UserPost.class);
    }

    public PageResult<UserPost> findPaging(String userId, long startNo, int limit) {

        Query query = new Query();

        if (startNo > 0) {
            query.addCriteria(Criteria.where("_id").lte(startNo));
        } else {
            query.addCriteria(Criteria.where("_id").lt(Long.MAX_VALUE));
        }

        query.addCriteria(Criteria.where("userId").is(userId));

        query.limit(limit + 1);

        List<UserPost> userPosts = mongoTemplate.find(query, UserPost.class);

        if (userPosts.size() > limit) {
            List<UserPost> list = userPosts.subList(0, limit);
            long nextNo = userPosts.get(limit).getNo();
            return new PageResult<>(list, true, nextNo);
        } else {
            return new PageResult<>(userPosts, false, 0);
        }

    }

}

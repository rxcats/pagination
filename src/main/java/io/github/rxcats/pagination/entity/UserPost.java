package io.github.rxcats.pagination.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
@CompoundIndexes({
    @CompoundIndex(name = "i_no", def = "{_id:-1, userId:1}")
})
public class UserPost {
    @Id
    private long no;

    private String userId;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private boolean read;

    public static UserPost of(String userId, long no) {
        UserPost userPost = new UserPost();
        userPost.no = no;
        userPost.userId = userId;
        userPost.createdDate = LocalDateTime.now();
        userPost.updatedDate = LocalDateTime.now();
        userPost.read = false;
        return userPost;
    }

}

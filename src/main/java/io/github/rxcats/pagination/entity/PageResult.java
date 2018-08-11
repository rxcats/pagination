package io.github.rxcats.pagination.entity;

import java.util.List;

import org.springframework.lang.NonNull;

import lombok.Data;

@Data
public class PageResult<T> {

    private List<T> content;

    private int size;

    private boolean hasNext;

    private long nextNo;

    public PageResult(@NonNull List<T> content, boolean hasNext, long nextNo) {
        this.content = content;
        this.size = content.size();
        this.hasNext = hasNext;
        this.nextNo = nextNo;
    }

}

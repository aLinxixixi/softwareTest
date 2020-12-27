package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Review;

import java.util.List;

public interface ReviewService {

    void add(Review c);
    void delete(int id);
    void update(Review c);
    List list(int pid);

    Review get(int id);
    int getCount(int pid);


}

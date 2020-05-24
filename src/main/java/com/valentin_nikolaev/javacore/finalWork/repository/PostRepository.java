package com.valentin_nikolaev.javacore.finalWork.repository;

import com.valentin_nikolaev.javacore.finalWork.models.Post;

import java.util.List;

public interface PostRepository extends GenericRepository<Post,Long> {

    List<Post> getPostsByUserId(Long userId);

    void removePostsByUserId(Long userId);


}

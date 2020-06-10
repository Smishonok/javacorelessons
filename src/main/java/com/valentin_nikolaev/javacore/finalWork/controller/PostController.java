package com.valentin_nikolaev.javacore.finalWork.controller;

import com.valentin_nikolaev.javacore.finalWork.models.Post;
import com.valentin_nikolaev.javacore.finalWork.repository.PostRepository;
import com.valentin_nikolaev.javacore.finalWork.repository.RepositoryManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;


public class PostController {

    static Logger log = Logger.getLogger(PostController.class);

    private PostRepository postRepository;

    public PostController() throws ClassNotFoundException {
        initPostRepository();
    }

    private void initPostRepository() throws ClassNotFoundException {
        log.debug("Starting initialisation of posts repository");
        postRepository = RepositoryManager.getRepositoryFactory().getPostRepository();
        log.debug("Posts repository implementation is: " + postRepository.getClass().getName());
    }

    public void addPost(String userId, String content) {
        long id = Long.parseLong(userId);
        Post post = new Post(id, content);
        this.postRepository.add(post);
    }

    public Optional<Post> getPost(String postId) {
        long id = Long.parseLong(postId);
        Optional<Post> post = Optional.empty();
        if (this.postRepository.contains(id)) {
            post = Optional.of(this.postRepository.get(id));
        }
        return post;
    }

    public List<Post> getAllPostsList() {
        return this.postRepository.getAll();
    }

    public List<Post> getPostsByUserId(String userId) {
        long id = Long.parseLong(userId);
        return this.postRepository.getPostsByUserId(id);
    }

    public void changePost(String postId,String newContent) {
        long id = Long.parseLong(postId);
        if (this.postRepository.contains(id)) {
            Post post = this.postRepository.get(id);
            post.setContent(newContent);
            this.postRepository.change(post);
        }
    }

    public void removePost(String postId) {
        long id = Long.parseLong(postId);
        this.postRepository.remove(id);
    }

    public void removeAllPostByUser(String userId) {
        long id = Long.parseLong(userId);
        this.postRepository.removePostsByUserId(id);
    }

    public void removeAllPosts() {
        this.postRepository.removeAll();
    }
}

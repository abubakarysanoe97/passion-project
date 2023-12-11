package com.VibezConnect0.VibezConnect0.service;

import com.VibezConnect0.VibezConnect0.models.Post;
import com.VibezConnect0.VibezConnect0.models.User;
import com.VibezConnect0.VibezConnect0.repository.PostRepository;
import com.VibezConnect0.VibezConnect0.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    public Post createPost(Post post) {
User user = new User();
post.setAuthor(user);
            post.setContent(post.getContent());
           post.setContent(post.getContent());
            return postRepository.save(post);
    }

    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    public List<Post> getAllPosts() {
        return (List<Post>) postRepository.findAll();
    }

    public void editPost(Long postId, String newContent) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setContent(newContent);
            postRepository.save(post);
        }
        // Handle the case where the post with the given ID is not found.
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

}



//    public Post createPost(Post post) {
//        Post createdPost = postRepository.save(post);
//        logger.info("Post created with ID: {}", createdPost.getId());
//        return createdPost;
//    }
//
//
//    public Optional<Post> getPostById(Long id) {
//        return postRepository.findById(id);
//    }
//
//   public Iterable<Post> getAllPost(){
//        return postRepository.findAll();
//    }
//    public Post editPost(Long postId, Post updatedPostData) {
//        Optional<Post> existingPostOptional = postRepository.findById(postId);
//
//        if (existingPostOptional.isPresent()) {
//            Post existingPost = existingPostOptional.get();
//
//            existingPost.setContent(updatedPostData.getContent());
//
//            Post updatedPost = postRepository.save(existingPost);
//
//            logger.info("Successfully updated post with ID: {}", postId);
//
//            return updatedPost;
//        } else {
//
//            logger.error("Post with ID {} not found", postId);
//            throw new NoSuchElementException("Post with ID " + postId + " not found");
//        }
//    }
//    public void deletePost(Long id){
//        postRepository.deleteById(id);
//    }
//




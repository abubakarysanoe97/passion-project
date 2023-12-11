package com.VibezConnect0.VibezConnect0.controller;

import com.VibezConnect0.VibezConnect0.models.Post;
import com.VibezConnect0.VibezConnect0.models.User;
import com.VibezConnect0.VibezConnect0.service.PostService;
import com.VibezConnect0.VibezConnect0.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;

//    @Autowired
//    public PostController(PostService postService, UserService userService) {
//        this.postService = postService;
//        this.userService = userService;
//    }

    @PostMapping("user/{userId}/post")
    public ResponseEntity<Post> postUser(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(post.getId()).toUri();
        responseHeaders.setLocation(newUserUri);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);

    }

//    @PostMapping("user{userId}post")
//    public ResponseEntity<Post> createPost(@RequestParam Long userId, @RequestParam String content) {
//        Optional<User> optionalUser = userService.getUserById(userId);
//        if (optionalUser.isPresent()) {
//            User author = optionalUser.get();
//            Post createdPost = postService.createPost(content, author);
//            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("post/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        Optional<Post> optionalPost = postService.getPostById(postId);
        return optionalPost.map(post -> new ResponseEntity<>(post, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/posts/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<String> editPost(@PathVariable Long postId, @RequestParam String newContent) {
        try {
            postService.editPost(postId, newContent);
            return new ResponseEntity<>("Post edited successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error editing post: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting post: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



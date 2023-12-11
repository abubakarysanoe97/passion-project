package com.VibezConnect0.VibezConnect0.controller;

import com.VibezConnect0.VibezConnect0.dto.CommentRequest;
import com.VibezConnect0.VibezConnect0.models.Comment;
import com.VibezConnect0.VibezConnect0.service.CommentService;
import com.VibezConnect0.VibezConnect0.service.PostService;
import com.VibezConnect0.VibezConnect0.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("user/{userId}/{postId}/comment")
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequest commentRequest) {
        try {
            Comment createdComment = commentService.createComment(commentRequest);
            return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//    public ResponseEntity<Comment> createComment(
//            @RequestParam Long postId,
//            @RequestParam Long userId,
//            @RequestParam String text
//    ) {
//        try {
//            Comment createdComment = commentService.createComment(postId, userId, text);
//            return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        Optional<Comment> optionalComment = commentService.getCommentById(commentId);
        return optionalComment.map(comment -> new ResponseEntity<>(comment, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/comments/all")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<String> editComment(@PathVariable Long commentId, @RequestParam String newText) {
        try {
            commentService.editComment(commentId, newText);
            return new ResponseEntity<>("Comment edited successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error editing comment: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error deleting comment: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

package com.VibezConnect0.VibezConnect0.service;

import com.VibezConnect0.VibezConnect0.dto.CommentRequest;
import com.VibezConnect0.VibezConnect0.models.Comment;
import com.VibezConnect0.VibezConnect0.models.Post;
import com.VibezConnect0.VibezConnect0.models.User;
import com.VibezConnect0.VibezConnect0.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostService postService, UserService userService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public Comment createComment(CommentRequest commentRequest) {
        Optional<Post> postOptional = postService.getPostById(commentRequest.getPostId());
        Optional<User> userOptional = userService.getUserById(commentRequest.getUserId());

        if (postOptional.isPresent() && userOptional.isPresent()) {
            Post post = postOptional.get();
            User commenter = userOptional.get();

            Comment comment = new Comment();
            comment.setText(commentRequest.getText());
            comment.setPost(post);
            comment.setCommenter(commenter);

            return commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("Invalid post ID or user ID");
        }
    }

//    public Comment createComment(Long postId, Long userId, String text) {
//        Optional<Post> PostToCommentOn = postService.getPostById(postId);
//        Optional<User> UserWhoIsCommenting = userService.getUserById(userId);
//
//        if (PostToCommentOn.isPresent() && UserWhoIsCommenting.isPresent()) {
//            Post post = PostToCommentOn.get();
//            User commenter = UserWhoIsCommenting.get();
//
//            Comment comment = new Comment();
//            comment.setText(text);
//            comment.setPost(post);
//            comment.setCommenter(commenter);
//
//            return commentRepository.save(comment);
//        } else {
//
//            throw new IllegalArgumentException("Invalid post ID or user ID");
//        }
//    }

    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public List<Comment> getAllComments() {
        return (List<Comment>) commentRepository.findAll();
    }

    public void editComment(Long commentId, String newText) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setText(newText);
            commentRepository.save(comment);
        } else {
            // Handle the case where the comment with the given ID is not found.
            throw new IllegalArgumentException("Comment not found");
        }
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}

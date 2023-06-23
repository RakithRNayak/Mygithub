package com.myblog1.Controller;

import com.myblog1.Payload.CommentDto;
import com.myblog1.Service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    //http://localhost:8080/api/posts/1/
    @PostMapping("/{postId}/")
    public ResponseEntity<CommentDto> createComment
    (@PathVariable("postId") long postId, @RequestBody CommentDto commentDto){
        CommentDto savedComment = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }
    //http://localhost:8080/api/posts/1/comments
    @GetMapping("/{postId}/comments")

    public List<CommentDto> getCommentByPostId(@PathVariable(value = "postId") long postId){
        List<CommentDto> commentsByPostId = commentService.getCommentsByPostId(postId);
        return commentsByPostId;

    }
    //http://localhost:8080/api/posts/1/1
    @GetMapping("/{postId}/{commentId}")
    public ResponseEntity<CommentDto>
    getCommentById(@PathVariable(value = "postId")long postId,@PathVariable(value = "commentId")long commentId){
        CommentDto commentById = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentById,HttpStatus.FOUND);
    }
    //http://localhost:8080/api/posts/1/1
    @PutMapping("/{postId}/{commentId}")
    public ResponseEntity<CommentDto> updateComment
            (@PathVariable("postId") long postId, @PathVariable("commentId")
            long commentId,@RequestBody CommentDto commentDto){
        CommentDto dto = commentService.upadateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,
                                                @PathVariable("commentId") long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("delete done successfully",HttpStatus.CREATED);
    }

}

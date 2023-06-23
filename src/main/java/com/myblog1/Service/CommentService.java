package com.myblog1.Service;

import com.myblog1.Payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);
    CommentDto getCommentById(long postId, long commentId);


    CommentDto upadateComment(long postId, long commentId,CommentDto commentDto);

    void deleteComment(long postId, long commentId);
}

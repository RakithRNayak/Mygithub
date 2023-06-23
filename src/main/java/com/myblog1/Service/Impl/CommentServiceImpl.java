package com.myblog1.Service.Impl;

import com.myblog1.Entity.Comment;
import com.myblog1.Entity.Post;
import com.myblog1.Exception.BlogAPIException;
import com.myblog1.Exception.ResourceNotFoundException;
import com.myblog1.Payload.CommentDto;
import com.myblog1.Repository.CommentRepository;
import com.myblog1.Repository.PostRepository;
import com.myblog1.Service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepo;
    private PostRepository postRepo;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo, ModelMapper modelMapper) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow
                (() -> new EntityNotFoundException("no post found"));
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);
        CommentDto dto = mapToDto(savedComment);

        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);
        List<CommentDto> collectDto = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return collectDto;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepo.findById(postId).orElseThrow
                (() -> new ResourceNotFoundException("no post found"));
        Comment comments = commentRepo.findById(commentId).orElseThrow
                (() -> new ResourceNotFoundException("no comment found"));
        if(comments.getPost().getId()!=(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment doesn't belongs to this post");

        }

        return mapToDto(comments);
    }

    @Override
    public CommentDto upadateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow
                (() -> new ResourceNotFoundException("no post found"));
        Comment comments = commentRepo.findById(commentId).orElseThrow
                (() -> new ResourceNotFoundException("no comment found"));
        if(comments.getPost().getId()!=(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment does not belongs to this post");
        }
        comments.setName(commentDto.getName());
        comments.setEmail(commentDto.getEmail());
        comments.setBody(commentDto.getBody());
        Comment updatedComment = commentRepo.save(comments);
        CommentDto dto = mapToDto(updatedComment);
        return dto;
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepo.findById(postId).orElseThrow
                (() -> new ResourceNotFoundException("no post found"));
        Comment comments = commentRepo.findById(commentId).orElseThrow
                (() -> new ResourceNotFoundException("no comment found"));
        if(comments.getPost().getId()!=(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment does not belongs to this post");
        }
        commentRepo.deleteById(commentId);
    }


    CommentDto mapToDto(Comment savedComment) {
        CommentDto dto = modelMapper.map(savedComment, CommentDto.class);
//        CommentDto dto= new CommentDto();
//        dto.setId(savedComment.getId());
//        dto.setName(savedComment.getName());
//        dto.setEmail(savedComment.getEmail());
//        dto.setBody(savedComment.getBody());
        return dto;
    }

    Comment mapToEntity(CommentDto commentDto) {
        Comment comment= modelMapper.map(commentDto, Comment.class);
//        Comment c = new Comment();
//        c.setName(commentDto.getName());
//        c.setEmail(commentDto.getEmail());
//        c.setBody(commentDto.getBody());
        return comment;

    }
}

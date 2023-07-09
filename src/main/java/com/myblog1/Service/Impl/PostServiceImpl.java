package com.myblog1.Service.Impl;

import com.myblog1.Entity.Post;
import com.myblog1.Exception.ResourceNotFoundException;
import com.myblog1.Payload.PostDto;
import com.myblog1.Payload.PostResponse;
import com.myblog1.Repository.PostRepository;
import com.myblog1.Service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepo.save(post);

        PostDto postDto1 = mapToDto(savedPost);
        return postDto1;
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize, String sortBy, String sortDir) {
       Sort sort= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending()
                :Sort.by(sortBy).ascending();

        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> content = postRepo.findAll(pageable);
        List<Post> posts = content.getContent();
        List<PostDto> dto= posts.stream().map(post ->mapToDto(post)).collect(Collectors.toList());
        PostResponse pr = new PostResponse();
        pr.setContent(dto);
        pr.setPageNo(content.getNumber());
        pr.setPageSize(content.getSize());
        pr.setTotalPages(content.getTotalPages());
        pr.setLast(content.isLast());
        pr.setTotalElements((int)content.getTotalElements());
        return pr;


    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record Found"));
        PostDto postDtoById = mapToDto(post);
        return postDtoById;
    }

    @Override
    public PostDto updatePostById(long id, PostDto postDto) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record Found"));
       post.setTitle(postDto.getTitle());
       post.setDescription(postDto.getDescription());
       post.setContent(postDto.getContent());
        Post updatedPost = postRepo.save(post);
        PostDto updatedDto = mapToDto(updatedPost);

        return updatedDto;
    }

    @Override
    public void deleteById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record Found"));
        postRepo.deleteById(id);
    }


    public Post mapToEntity(PostDto postDto) {
        Post post= modelMapper.map(postDto,Post.class);

//        Post p = new Post();
//        p.setContent(postDto.getContent());
//        p.setDescription(postDto.getDescription());
//        p.setTitle(postDto.getTitle());
        return post;

    }

    public PostDto mapToDto(Post post) {
        PostDto dto = modelMapper.map(post,PostDto.class);
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setContent(post.getContent());
//        dto.setDescription(post.getDescription());
//        dto.setTitle(post.getTitle());
        return dto;
    }
}
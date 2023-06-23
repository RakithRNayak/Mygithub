package com.myblog1.Service;

import com.myblog1.Payload.PostDto;
import com.myblog1.Payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePostById(long id, PostDto postDto);

    void deleteById(long id);
}

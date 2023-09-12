package com.socialmedia.service;

import com.socialmedia.payload.PostDto;
import com.socialmedia.payload.PostResponse;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir);

    PostDto updatePost(long id, PostDto postDto);

    void deleteById(long id);
}

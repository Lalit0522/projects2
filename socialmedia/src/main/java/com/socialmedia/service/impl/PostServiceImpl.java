package com.socialmedia.service.impl;

import com.socialmedia.entity.Post;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.payload.PostDto;
import com.socialmedia.payload.PostResponse;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post created = postRepository.save(post);
        return mapToDto(created);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> all = postRepository.findAll(pageable);

        List<PostDto> contents = all.stream().map(c->mapToDto(c)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setPageNo(all.getNumber());
        postResponse.setPageSize(all.getSize());
        postResponse.setTotalPages(all.getTotalPages());
        postResponse.setTotalElements(all.getTotalElements());
        postResponse.setContent(contents);
        postResponse.setLast(all.isLast());
        return postResponse;
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
       Post post = postRepository.findById(id).orElseThrow(
               ()->new ResourceNotFoundException("Post","id",id)
       );

        post.setTitle(postDto.getContent());
        post.setMessage(postDto.getMessage());
        post.setContent(postDto.getContent());

        Post updated = postRepository.save(post);
        return mapToDto(updated);
    }

    @Override
    public void deleteById(long id) {
        postRepository.deleteById(id);
    }


    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setMessage(postDto.getMessage());
        post.setContent(postDto.getContent());
        return post;
    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setMessage(post.getMessage());
        postDto.setContent(post.getContent());
        return postDto;
    }

}

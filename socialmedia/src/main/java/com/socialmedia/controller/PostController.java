package com.socialmedia.controller;

import com.socialmedia.payload.PostDto;
import com.socialmedia.payload.PostResponse;
import com.socialmedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK )
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue ="asc",required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id, @RequestBody PostDto postDto){
       return new ResponseEntity<>(postService.updatePost(id,postDto),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteById(@PathVariable("id") long id){
        postService.deleteById(id);
    }
}

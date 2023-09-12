package com.socialmedia.payload;

import lombok.Data;

@Data
public class PostDto {

    private long id;
    private String title;
    private String message;
    private String content;
}

package com.myblog1.Payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min=2,message = "Post title should have atleast Two characters")
    private String title;
    @NotEmpty
    @Size(min=2,message = "Post title should have atleast 10 characters")
    private String description;
    private String content;
}

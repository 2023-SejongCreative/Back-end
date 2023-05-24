package com.example.Waffle.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Getter
@Setter
public class ProfileDto {
    private String name;
    private String introduction;
    private String img;
    private String content;
}

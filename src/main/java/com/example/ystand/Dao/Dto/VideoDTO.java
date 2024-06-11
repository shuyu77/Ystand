package com.example.ystand.Dao.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {

    private Long id;

    private Integer userId;

    private String title;

    private String description;

    private String url;

    private String thumbnail;

    private Integer viewCount;

    private Integer likeCount;

    private Integer coinCount;

    private Integer collectCount;

    private Integer commentCount;

    private MultipartFile video;

    private MultipartFile images;

    private String userName;

    private LocalDateTime createdAt;

    private Integer isCheck;
}

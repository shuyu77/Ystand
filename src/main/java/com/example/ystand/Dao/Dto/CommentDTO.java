package com.example.ystand.Dao.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;

    private Long videoId;

    private String content;

    private Integer userId;

    private Integer likeCount;

    private Long rootId;

    private Long toId;

    private LocalDateTime createdAt;

    private String userName;

    private String avatar;

    private List<CommentDTO> commentSon;

    private String toIdUserName;
}

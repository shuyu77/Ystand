package com.example.ystand.Dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ystand.Dao.Dto.CommentDTO;
import com.example.ystand.Dao.po.Comment;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-05-13
 */
public interface CommentMapper extends BaseMapper<Comment> {

    @Results({
            @Result(column = "nickname", property = "userName"), // 将 nickname 映射到 userName
            @Result(column = "avatar", property = "avatar") // 将 nickname 映射到 userName
    })
    @Select("SELECT c.*, u.nickname, u.avatar FROM comment c " +
            "JOIN users u ON c.user_id = u.id " +
            "WHERE c.video_id = #{id} AND c.root_id IS NULL " +
            "ORDER BY c.created_at DESC ")
    List<CommentDTO> ListComment(Long id);

    @Results({
            @Result(column = "nickname", property = "userName"), // 将 nickname 映射到 userName
            @Result(column = "avatar", property = "avatar") // 将 nickname 映射到 userName
    })
    @Select("SELECT c.*, u.nickname, u.avatar FROM comment c " +
            "JOIN users u ON c.user_id = u.id " +
            "WHERE c.video_id = #{videoId} AND c.root_id = #{userId} " +
            "ORDER BY c.created_at DESC ")
    List<CommentDTO> ListCommentSon(Long videoId, Long userId);
}

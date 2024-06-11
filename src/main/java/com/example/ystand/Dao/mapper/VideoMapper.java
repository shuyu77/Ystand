package com.example.ystand.Dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ystand.Dao.Dto.VideoDTO;
import com.example.ystand.Dao.po.Video;
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
public interface VideoMapper extends BaseMapper<Video> {

    @Results({
            @Result(column = "nickname", property = "userName") // 将 nickname 映射到 userName
    })
    @Select("SELECT v.*, u.nickname, u.avatar FROM video v " +
            "JOIN users u ON v.user_id = u.id " +
            "WHERE v.is_check = 1 " +
            "ORDER BY v.created_at DESC ")
    List<VideoDTO> listVideo();

    @Results({
            @Result(column = "nickname", property = "userName") // 将 nickname 映射到 userName
    })
    @Select("SELECT v.*, u.nickname, u.avatar FROM video v " +
            "JOIN users u ON v.user_id = u.id " +
            "WHERE v.id != #{id} And v.is_check = 1 " +
            "ORDER BY v.created_at DESC ")
    List<VideoDTO> ListVideo(Long id);
}

package com.example.ystand.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ystand.Dao.Dto.CommentDTO;
import com.example.ystand.Dao.Dto.VideoDTO;
import com.example.ystand.Dao.po.Users;
import com.example.ystand.Dao.po.Video;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-05-13
 */
public interface IVideoService extends IService<Video> {

    boolean insertVideo(VideoDTO videoDTO);

    boolean deleteVideo(Integer id);

    boolean updateVideo(VideoDTO videoDTO);

    List<VideoDTO> listVideo();

    Users getUserById(Integer userId);

    List<VideoDTO> ListVideo(Long id);

    List<CommentDTO> ListComment(Long id);

    Users getByAccount(String name);

    boolean succeedVideo(Integer id);

    boolean failVideo(Integer id);
}

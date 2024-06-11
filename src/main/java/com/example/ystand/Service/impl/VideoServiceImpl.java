package com.example.ystand.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ystand.Dao.Dto.CommentDTO;
import com.example.ystand.Dao.Dto.VideoDTO;
import com.example.ystand.Dao.mapper.CommentMapper;
import com.example.ystand.Dao.mapper.UserMapper;
import com.example.ystand.Dao.mapper.VideoMapper;
import com.example.ystand.Dao.po.Comment;
import com.example.ystand.Dao.po.Users;
import com.example.ystand.Dao.po.Video;
import com.example.ystand.Service.IVideoService;
import com.example.ystand.Utils.ConvertBeanUtils;
import com.example.ystand.Utils.HttpUtils;
import com.example.ystand.Utils.MyStringUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-05-13
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private CommentMapper commentMapper;

    @Override
    public boolean insertVideo(VideoDTO videoDTO) {
        if(videoDTO.getVideo() == null) return false;
        if(videoDTO.getImages() == null) return false;
        if(videoDTO.getTitle() == null) return false;

        Video video = ConvertBeanUtils.convert(videoDTO, Video.class);

        String filenamevideo = HttpUtils.postMultipartFile(videoDTO.getVideo());
        String filenameimage = HttpUtils.postMultipartFile(videoDTO.getImages());
        video.setUrl("http://8.138.112.13:8180/Y-stand/" + filenamevideo);
        video.setThumbnail("http://8.138.112.13:8180/Y-stand/" + filenameimage);

        return videoMapper.insert(video) > 0;
    }

    @Override
    public boolean deleteVideo(Integer id) {
        return videoMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateVideo(VideoDTO videoDTO) {
        if(videoDTO.getTitle() == null) return false;

        Video video = ConvertBeanUtils.convert(videoDTO, Video.class);
        video.setId(videoDTO.getId());
        String filenamevideo, filenameimage;
        if(!videoDTO.getVideo().isEmpty()){
            filenamevideo = HttpUtils.postMultipartFile(videoDTO.getVideo());
            video.setUrl("http://8.138.112.13:8180/Y-stand/" + filenamevideo);
        }
        if(!videoDTO.getImages().isEmpty()) {
            filenameimage = HttpUtils.postMultipartFile(videoDTO.getImages());
            video.setThumbnail("http://8.138.112.13:8180/Y-stand/" + filenameimage);
        }
        return videoMapper.updateById(video) > 0;
    }

    @Override
    public List<VideoDTO> listVideo() {
        return setlistVideo(videoMapper.listVideo(), 55);
    }

    @Override
    public Users getUserById(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public List<VideoDTO> ListVideo(Long id) {
        return setlistVideo(videoMapper.ListVideo(id), 41);
    }

    @Override
    public List<CommentDTO> ListComment(Long id) {
        List<CommentDTO> list = commentMapper.ListComment(id);
        for (CommentDTO commentDTO : list) {
//            查询出所有这个视频额定相同父节点的评论
            commentDTO.setCommentSon(commentMapper.ListCommentSon(commentDTO.getVideoId(), commentDTO.getId()));
//            查询这个父节点评论的回复评论并赋值
            for(CommentDTO commentDTO1 : commentDTO.getCommentSon()){
                if(commentDTO1.getToId() != null){
//                    感觉回复的id找到回复的是那一条评论
                    Comment commentitme = commentMapper.selectById(commentDTO1.getToId());
//                    查询这条评论的名字
                    Users users = userMapper.selectById(commentitme.getUserId());
                    commentDTO1.setToIdUserName("回复 @" + users.getNickname());
                }

            }
        }
        return list;
    }

    @Override
    public Users getByAccount(String name) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Users::getAccount, name);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean succeedVideo(Integer id) {
        Video video = videoMapper.selectById(id);
        video.setIsCheck(1);
        return videoMapper.updateById(video) > 0;
    }

    @Override
    public boolean failVideo(Integer id) {
        Video video = videoMapper.selectById(id);
        video.setIsCheck(2);
        return videoMapper.updateById(video) > 0;
    }

    public List<VideoDTO> setlistVideo(List<VideoDTO> videoDTOList, Integer num){
        for (VideoDTO video : videoDTOList) {
            String content = video.getTitle();
            content = MyStringUtils.truncateString(content, num);
            video.setTitle(content);
        }
        return videoDTOList;
    }

}

package com.example.ystand.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ystand.Dao.mapper.LikesMapper;
import com.example.ystand.Dao.mapper.VideoMapper;
import com.example.ystand.Dao.po.Likes;
import com.example.ystand.Dao.po.Video;
import com.example.ystand.Service.ILikesService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-05-13
 */
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes> implements ILikesService {

    @Resource
    private LikesMapper likesMapper;

    @Resource
    private VideoMapper videoMapper;

    @Override
    public boolean insertLike(Likes likes) {
        if(getVideoUser(likes.getVideoId(), likes.getUserId()) && likesMapper.insert(likes) > 0){
            return updateVideo(likes.getVideoId(), new QueryWrapper<>());
        }
        return false;
    }

    @Override
    public boolean updateLike(Likes likes) {
        if(getVideoUser(likes.getVideoId(), likes.getUserId()) && likesMapper.updateById(likes) > 0){
            return updateVideo(likes.getVideoId(), new QueryWrapper<>());
        }
        return false;
    }

    public boolean updateVideo(Long id, QueryWrapper<Likes> queryWrapper) {
        queryWrapper.clear();
        queryWrapper.lambda().eq(Likes::getVideoId, id);

        Video video = new Video();
        video.setId(id);
        video.setLikeCount(likesMapper.selectCount(queryWrapper).intValue());
        return videoMapper.updateById(video) > 0;
    }

    @Override
    public boolean deleteLike(Long id, Long videoId) {
        return likesMapper.deleteById(id) > 0 && updateVideo(videoId, new QueryWrapper<Likes>());
    }

    @Override
    public boolean getVideoUser(Long videoId, Integer userId) {
        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Likes::getUserId, userId);
        queryWrapper.lambda().eq(Likes::getVideoId, videoId);
        return likesMapper.selectOne(queryWrapper) == null;
    }

    @Override
    public boolean deleteLikeByUserIdAndVideoId(Likes likes) {
        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Likes::getUserId, likes.getUserId());
        queryWrapper.lambda().eq(Likes::getVideoId, likes.getVideoId());
        return likesMapper.delete(queryWrapper) > 0 && updateVideo(likes.getVideoId(), new QueryWrapper<>());
    }

}

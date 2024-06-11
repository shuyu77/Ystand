package com.example.ystand.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ystand.Dao.mapper.CollectMapper;
import com.example.ystand.Dao.mapper.VideoMapper;
import com.example.ystand.Dao.po.Collect;
import com.example.ystand.Dao.po.Video;
import com.example.ystand.Service.ICollectService;
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
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements ICollectService {

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private VideoMapper videoMapper;

    @Override
    public boolean insertCollect(Collect collect) {
        if(getVideoUser(collect.getVideoId(), collect.getUserId()) && collectMapper.insert(collect) > 0){
            return updateVideo(collect.getVideoId(), new QueryWrapper<>());
        }
        return false;
    }

    @Override
    public boolean updateCollect(Collect collect) {
        if(getVideoUser(collect.getVideoId(), collect.getUserId()) && collectMapper.updateById(collect) > 0){
            return updateVideo(collect.getVideoId(), new QueryWrapper<>());
        }
        return false;
    }

    @Override
    public boolean deleteCollect(Long id, Long videoId) {
        return collectMapper.deleteById(id) > 0 && updateVideo(videoId, new QueryWrapper<Collect>());
    }

    @Override
    public boolean getVideoUser(Long videoId, Integer userId) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Collect::getUserId, userId);
        queryWrapper.lambda().eq(Collect::getVideoId, videoId);
        return collectMapper.selectOne(queryWrapper) == null;
    }

    @Override
    public boolean deleteCollectByUserIdAndVideoId(Collect collect) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Collect::getUserId, collect.getUserId());
        queryWrapper.lambda().eq(Collect::getVideoId, collect.getVideoId());
        return collectMapper.delete(queryWrapper) > 0 && updateVideo(collect.getVideoId(), new QueryWrapper<>());
    }

    public boolean updateVideo(Long id, QueryWrapper<Collect> queryWrapper) {
        queryWrapper.clear();
        queryWrapper.lambda().eq(Collect::getVideoId, id);

        Video video = new Video();
        video.setId(id);
        video.setCollectCount(collectMapper.selectCount(queryWrapper).intValue());
        return videoMapper.updateById(video) > 0;
    }
}

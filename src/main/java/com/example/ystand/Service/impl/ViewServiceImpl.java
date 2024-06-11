package com.example.ystand.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ystand.Dao.mapper.VideoMapper;
import com.example.ystand.Dao.mapper.ViewMapper;
import com.example.ystand.Dao.po.Video;
import com.example.ystand.Dao.po.View;
import com.example.ystand.Service.IViewService;
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
public class ViewServiceImpl extends ServiceImpl<ViewMapper, View> implements IViewService {

    @Resource
    private ViewMapper viewMapper;

    @Resource
    private VideoMapper videoMapper;

    @Override
    public boolean insertView(View view) {
        if(getVideoUser(view.getVideoId(), view.getUserId()) && viewMapper.insert(view) > 0){
            return updateVideo(view.getVideoId(), new QueryWrapper<>());
        }
        return false;
    }

    @Override
    public boolean updateView(View view) {
        if(getVideoUser(view.getVideoId(), view.getUserId()) && viewMapper.updateById(view) > 0){
            return updateVideo(view.getVideoId(), new QueryWrapper<>());
        }
        return false;
    }

    @Override
    public boolean getVideoUser(Long videoId, Integer userId) {
        QueryWrapper<View> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(View::getUserId, userId);
        queryWrapper.lambda().eq(View::getVideoId, videoId);
        return viewMapper.selectOne(queryWrapper) == null;
    }

    @Override
    public boolean insertViewRepeatable(View view) {
        if(viewMapper.insert(view) > 0){
            return updateVideo(view.getVideoId(), new QueryWrapper<>());
        }
        return false;
    }

    @Override
    public boolean deleteView(Long id, Long videoId) {
        return viewMapper.deleteById(id) > 0 && updateVideo(videoId, new QueryWrapper<>());
    }

    public boolean updateVideo(Long id, QueryWrapper<View> queryWrapper) {
        queryWrapper.clear();
        queryWrapper.lambda().eq(View::getVideoId, id);

        Video video = new Video();
        video.setId(id);
        video.setViewCount(viewMapper.selectCount(queryWrapper).intValue());
        return videoMapper.updateById(video) > 0;
    }
}

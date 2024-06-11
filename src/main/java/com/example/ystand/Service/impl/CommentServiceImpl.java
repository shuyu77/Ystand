package com.example.ystand.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ystand.Dao.mapper.CommentMapper;
import com.example.ystand.Dao.mapper.VideoMapper;
import com.example.ystand.Dao.po.Comment;
import com.example.ystand.Dao.po.Video;
import com.example.ystand.Service.ICommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private VideoMapper videoMapper;

    @Override
    public boolean insertComment(Comment comment) {
        if(commentMapper.insert(comment) > 0){
            return updateVideo(comment.getVideoId(), new QueryWrapper<Comment>());
        }
        return false;
    }

    @Override
    public boolean updateComment(Comment comment) {
        if(commentMapper.updateById(comment) > 0){
            return updateVideo(comment.getVideoId(), new QueryWrapper<Comment>());
        }
        return false;
    }

    @Override
    public boolean deleteComment(Integer id, Long videoId) {
        return commentMapper.deleteById(id) > 0 && updateVideo(videoId, new QueryWrapper<Comment>());
    }

    public boolean updateVideo(Long id, QueryWrapper<Comment> queryWrapper) {
        queryWrapper.clear();
        queryWrapper.lambda().eq(Comment::getVideoId, id);

        Video video = new Video();
        video.setId(id);
        video.setCommentCount(commentMapper.selectCount(queryWrapper).intValue());
        return videoMapper.updateById(video) > 0;
    }
}

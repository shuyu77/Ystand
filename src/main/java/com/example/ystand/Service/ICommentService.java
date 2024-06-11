package com.example.ystand.Service;

import com.example.ystand.Dao.po.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-05-13
 */
public interface ICommentService extends IService<Comment> {

    boolean insertComment(Comment comment);

    boolean updateComment(Comment comment);

    boolean deleteComment(Integer id, Long videoId);
}

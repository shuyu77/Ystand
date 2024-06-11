package com.example.ystand.Service;

import com.example.ystand.Dao.po.Likes;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-05-13
 */
public interface ILikesService extends IService<Likes> {

    boolean insertLike(Likes likes);

    boolean updateLike(Likes likes);

    boolean deleteLike(Long id, Long videoId);

    boolean getVideoUser(Long videoId, Integer userId);

    boolean deleteLikeByUserIdAndVideoId(Likes likes);
}

package com.example.ystand.Service;

import com.example.ystand.Dao.po.Collect;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-05-13
 */
public interface ICollectService extends IService<Collect> {

    boolean insertCollect(Collect collect);

    boolean updateCollect(Collect collect);

    boolean deleteCollect(Long id, Long videoId);

    boolean getVideoUser(Long videoId, Integer userId);

    boolean deleteCollectByUserIdAndVideoId(Collect collect);
}

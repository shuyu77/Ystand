package com.example.ystand.Service;

import com.example.ystand.Dao.po.View;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-05-13
 */
public interface IViewService extends IService<View> {

    boolean insertView(View view);

    boolean updateView(View view);

    boolean deleteView(Long id, Long videoId);

    boolean getVideoUser(Long videoId, Integer userId);

    boolean insertViewRepeatable(View view);
}

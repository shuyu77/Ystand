package com.example.ystand.Service;

import com.example.ystand.Dao.po.Coin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-05-13
 */
public interface ICoinService extends IService<Coin> {

    boolean deleteCoin(Long id, Long videoId);

    boolean updateCoin(Coin coin);

    boolean insertCoin(Coin coin);

    boolean getVideoUser(Long videoId, Integer userId);

    boolean deleteCoinByUserIdAndVideoId(Coin coin);
}

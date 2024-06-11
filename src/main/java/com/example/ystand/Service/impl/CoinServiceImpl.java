package com.example.ystand.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ystand.Dao.mapper.CoinMapper;
import com.example.ystand.Dao.mapper.VideoMapper;
import com.example.ystand.Dao.po.Coin;
import com.example.ystand.Dao.po.Video;
import com.example.ystand.Service.ICoinService;
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
public class CoinServiceImpl extends ServiceImpl<CoinMapper, Coin> implements ICoinService {

    @Resource
    private CoinMapper coinMapper;

    @Resource
    private VideoMapper videoMapper;

    @Override
    public boolean deleteCoin(Long id, Long videoId) {
        return coinMapper.deleteById(id) > 0 && updateVideo(videoId, new QueryWrapper<Coin>());
    }

    @Override
    public boolean updateCoin(Coin coin) {
        if(getVideoUser(coin.getVideoId(), coin.getUserId()) && coinMapper.updateById(coin) > 0){
            return updateVideo(coin.getVideoId(), new QueryWrapper<>());
        }
        return false;
    }

    @Override
    public boolean insertCoin(Coin coin) {
        if(getVideoUser(coin.getVideoId(), coin.getUserId()) && coinMapper.insert(coin) > 0){
            return updateVideo(coin.getVideoId(), new QueryWrapper<>());
        }
        return false;
    }

    @Override
    public boolean getVideoUser(Long videoId, Integer userId) {
        QueryWrapper<Coin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Coin::getUserId, userId);
        queryWrapper.lambda().eq(Coin::getVideoId, videoId);
        return coinMapper.selectOne(queryWrapper) == null;
    }

    @Override
    public boolean deleteCoinByUserIdAndVideoId(Coin coin) {
        QueryWrapper<Coin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Coin::getUserId, coin.getUserId());
        queryWrapper.lambda().eq(Coin::getVideoId, coin.getVideoId());
        return coinMapper.delete(queryWrapper) > 0 && updateVideo(coin.getVideoId(), new QueryWrapper<>());
    }

    public boolean updateVideo(Long id, QueryWrapper<Coin> queryWrapper) {
        queryWrapper.clear();
        queryWrapper.lambda().eq(Coin::getVideoId, id);

        Video video = new Video();
        video.setId(id);
        video.setCoinCount(coinMapper.selectCount(queryWrapper).intValue());
        return videoMapper.updateById(video) > 0;
    }
}

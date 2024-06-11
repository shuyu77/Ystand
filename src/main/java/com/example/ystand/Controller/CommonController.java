package com.example.ystand.Controller;

import com.example.ystand.Dao.Dto.UserVideoDTO;
import com.example.ystand.Dao.po.View;
import com.example.ystand.Service.ICoinService;
import com.example.ystand.Service.ICollectService;
import com.example.ystand.Service.ILikesService;
import com.example.ystand.Service.IViewService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;

@Controller
public class CommonController {
    @Resource
    private  ICoinService iCoinService;

    @Resource
    private  ICollectService iCollectService;

    @Resource
    private  ILikesService iLikesService;

    @Resource
    private IViewService iViewService;

    public  UserVideoDTO UserVideoDTOFun(Long videoId, Integer userId){
        UserVideoDTO userVideoDTO = new UserVideoDTO();
        userVideoDTO.setCoin(!iCoinService.getVideoUser(videoId, userId));
        userVideoDTO.setCollect(!iCollectService.getVideoUser(videoId, userId));
        userVideoDTO.setLike(!iLikesService.getVideoUser(videoId, userId));
        return userVideoDTO;
    }

    public boolean insertView(Integer userId, Long videoId){
        View view = new View();
        view.setUserId(userId);
        view.setVideoId(videoId);
        return iViewService.insertViewRepeatable(view);
    }
}


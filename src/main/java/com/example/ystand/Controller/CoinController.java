package com.example.ystand.Controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ystand.Dao.po.Coin;
import com.example.ystand.Dao.po.Likes;
import com.example.ystand.Service.ICoinService;
import com.example.ystand.Utils.CommonUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-05-13
 */
@Controller
public class CoinController {

    @Resource
    private ICoinService iCoinService;

    @GetMapping("/toCoin")
    public String toCoin(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        Page<Coin> userPage = new Page<>(pageNum,6);
        Page<Coin> page = iCoinService.page(userPage,null);
        model.addAttribute("page", page);
        model.addAttribute("coin", new Likes());
        model.addAttribute("globalUser", CommonUtils.getUsers());
        return "/manage/coin";
    }

    @PostMapping("/insertOrUpdateCoin")
    public String insertOrUpdateCoin(@ModelAttribute Coin coin){
        if(coin.getId() == null) {
            if (iCoinService.insertCoin(coin)) {
                System.out.println("插入成功");
            } else {
                System.out.println("插入失敗");
            }
        } else {
            if(iCoinService.updateCoin(coin)){
                System.out.println("修改成功");
            } else{
                System.out.println("修改失败");
            }
        }
        return "redirect:/toCoin";
    }

    @GetMapping("/deleteCoin")
    public String deleteCoin(Long id, Long videoId){
        if(iCoinService.deleteCoin(id, videoId)){
            System.out.println("删除成功");
        } else{
            System.out.println("删除失败");
        }
        return "redirect:/toCoin";
    }

    @GetMapping("/addOrDeleteCoin")
    public RedirectView addOrDeleteCoin(Integer userId, Long videoId, Boolean isCoin){
        if(CommonUtils.getUsers() != null){
            Coin coin = new Coin();
            coin.setVideoId(videoId);
            coin.setUserId(userId);
            if(isCoin){
//                已收藏, 点击取消收藏
                if(iCoinService.deleteCoinByUserIdAndVideoId(coin)){
                    System.out.println("投币删除成功");
                } else{
                    System.out.println("投币删除失败");
                }
            } else{
//                未收藏，添加收藏
                if(iCoinService.insertCoin(coin)){
                    System.out.println("投币增加成功");
                } else{
                    System.out.println("投币增加失败");
                }
            }
        }
        else{
            System.out.println("未登入");
        }
        RedirectView redirectView = new RedirectView("/Video");
        redirectView.addStaticAttribute("id", videoId);
        redirectView.addStaticAttribute("userId",  userId);

        return redirectView;
    }
}

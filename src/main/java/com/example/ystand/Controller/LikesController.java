package com.example.ystand.Controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ystand.Dao.po.Likes;
import com.example.ystand.Service.ILikesService;
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
public class LikesController {

    @Resource
    private ILikesService iLikesService;

    @GetMapping("/toLike")
    public String toLike(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        //分页查询数据，每页显示5条，
        Page<Likes> userPage = new Page<>(pageNum,6);
        //分页查询的结果
        Page<Likes> page = iLikesService.page(userPage,null);
        model.addAttribute("page", page);
        model.addAttribute("like", new Likes());
        model.addAttribute("globalUser", CommonUtils.getUsers());
        return "/manage/like";
    }

    @PostMapping("/insertOrUpdateLike")
    public String insertUser(@ModelAttribute Likes likes){
        if(likes.getId() == null) {
            if (iLikesService.insertLike(likes)) {
                System.out.println("插入成功");
            } else {
                System.out.println("插入失敗");
            }
        } else {
            if(iLikesService.updateLike(likes)){
                System.out.println("修改成功");
            } else{
                System.out.println("修改失败");
            }
        }
        return "redirect:/toLike";
    }

    @GetMapping("/deleteLike")
    public String deleteUser(Long id, Long videoId){
        if(iLikesService.deleteLike(id, videoId)){
            System.out.println("删除成功");
        } else{
            System.out.println("删除失败");
        }
        return "redirect:/toLike";
    }

    @GetMapping("/addOrDeleteLike")
    public RedirectView addOrDeleteLike(Integer userId, Long videoId, Boolean isLike){
        if(CommonUtils.getUsers() != null){
            Likes likes = new Likes();
            likes.setVideoId(videoId);
            likes.setUserId(userId);
            if(isLike){
//                已收藏, 点击取消收藏
                if(iLikesService.deleteLikeByUserIdAndVideoId(likes)){
                    System.out.println("投币删除成功");
                } else{
                    System.out.println("投币删除失败");
                }
            } else{
//                未收藏，添加收藏
                if(iLikesService.insertLike(likes)){
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

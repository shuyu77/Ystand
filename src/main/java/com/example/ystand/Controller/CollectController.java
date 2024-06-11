package com.example.ystand.Controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ystand.Dao.po.Collect;
import com.example.ystand.Dao.po.Likes;
import com.example.ystand.Dao.po.Users;
import com.example.ystand.Service.ICollectService;
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
public class CollectController {

    @Resource
    private ICollectService iCollectService;

    @GetMapping("/toCollect")
    public String toCollect(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        Page<Collect> userPage = new Page<>(pageNum,6);
        Page<Collect> page = iCollectService.page(userPage,null);
        model.addAttribute("page", page);
        model.addAttribute("collect", new Likes());
        model.addAttribute("globalUser", CommonUtils.getUsers());
        return "/manage/collect";
    }

    @PostMapping("/insertOrUpdateCollect")
    public String insertOrUpdateCollect(@ModelAttribute Collect collect){
        if(collect.getId() == null) {
            if (iCollectService.insertCollect(collect)) {
                System.out.println("插入成功");
            } else {
                System.out.println("插入失敗");
            }
        } else {
            if(iCollectService.updateCollect(collect)){
                System.out.println("修改成功");
            } else{
                System.out.println("修改失败");
            }
        }
        return "redirect:/toCollect";
    }

    @GetMapping("/deleteCollect")
    public String deleteUser(Long id, Long videoId){
        if(iCollectService.deleteCollect(id, videoId)){
            System.out.println("删除成功");
        } else{
            System.out.println("删除失败");
        }
        return "redirect:/toCollect";
    }

    @GetMapping("/addOrDeleteCollect")
    public RedirectView addOrDeleteCollect(Integer userId, Long videoId, Boolean isCollect){
        Users users = CommonUtils.getUsers();
        if(users != null){
            Collect collect = new Collect();
            collect.setVideoId(videoId);
            collect.setUserId(userId);
            if(isCollect){
//                已收藏, 点击取消收藏
                if(iCollectService.deleteCollectByUserIdAndVideoId(collect)){
                    System.out.println("删除成功");
                } else{
                    System.out.println("删除失败");
                }
            } else{
//                未收藏，添加收藏
                if(iCollectService.insertCollect(collect)){
                    System.out.println("收藏增加成功");
                } else{
                    System.out.println("收藏增加失败");
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

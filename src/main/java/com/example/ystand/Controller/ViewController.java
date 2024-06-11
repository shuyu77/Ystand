package com.example.ystand.Controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ystand.Dao.po.Likes;
import com.example.ystand.Dao.po.View;
import com.example.ystand.Service.IViewService;
import com.example.ystand.Utils.CommonUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-05-13
 */
@Controller
public class ViewController {

    @Resource
    private IViewService iViewService;

    @GetMapping("/toView")
    public String toView(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        Page<View> userPage = new Page<>(pageNum,6);
        Page<View> page = iViewService.page(userPage,null);
        model.addAttribute("page", page);
        model.addAttribute("view", new Likes());
        model.addAttribute("globalUser", CommonUtils.getUsers());
        return "/manage/view";
    }

    @PostMapping("/insertOrUpdateView")
    public String insertOrUpdateView(@ModelAttribute View view){
        if(view.getId() == null) {
            if (iViewService.insertView(view)) {
                System.out.println("插入成功");
            } else {
                System.out.println("插入失敗");
            }
        } else {
            if(iViewService.updateView(view)){
                System.out.println("修改成功");
            } else{
                System.out.println("修改失败");
            }
        }
        return "redirect:/toView";
    }

    @GetMapping("/deleteView")
    public String deleteView(Long id, Long videoId){
        if(iViewService.deleteView(id, videoId)){
            System.out.println("删除成功");
        } else{
            System.out.println("删除失败");
        }
        return "redirect:/toView";
    }
}

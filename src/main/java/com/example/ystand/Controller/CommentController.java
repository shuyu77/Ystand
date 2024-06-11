package com.example.ystand.Controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ystand.Dao.po.Comment;
import com.example.ystand.Dao.po.Users;
import com.example.ystand.Dao.po.Video;
import com.example.ystand.Service.ICommentService;
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
public class CommentController {

    @Resource
    private ICommentService iCommentService;

    @GetMapping("/toComment_check")
    public String toComment_check(){
        return "/manage/comment_check";
    }

    @GetMapping("/toComment")
    public String toComment(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        Page<Comment> userPage = new Page<>(pageNum,6);
        Page<Comment> page=iCommentService.page(userPage,null);
        model.addAttribute("page", page);
        model.addAttribute("comment", new Comment());
        model.addAttribute("globalUser", CommonUtils.getUsers());
        return "/manage/comment";
    }

    @PostMapping("/insertOrUpdateComment")
    public String insertOrUpdateComment(@ModelAttribute Comment comment){
        if(comment.getId() == null){
            if(iCommentService.insertComment(comment)){
                System.out.println("插入成功");
            }else{
                System.out.println("插入失敗");
            }
        } else{
            if(iCommentService.updateComment(comment)){
                System.out.println("修改成功");
            } else{
                System.out.println("修改失败");
            }
        }
        return "redirect:/toComment";
    }

    @GetMapping("/deleteComment")
    public String deleteUser(Integer id, Long videoId){
        if(iCommentService.deleteComment(id, videoId)){
            System.out.println("删除成功");
        } else{
            System.out.println("删除失败");
        }
        return "redirect:/toComment";
    }

    @GetMapping("/insertComment")
    public RedirectView insertComment(Video video, String content){
        Users users = CommonUtils.getUsers();
        if(users != null) {
            Comment comment = new Comment();
            comment.setUserId(users.getId());
            comment.setVideoId(video.getId());
            comment.setContent(content);
            if(iCommentService.insertComment(comment)){
                System.out.println("增加成功");
            } else{
                System.out.println("增加失败");
            }
        }
        RedirectView redirectView = new RedirectView("/Video");
        redirectView.addStaticAttribute("id", video.getId());
        redirectView.addStaticAttribute("userId",  video.getUserId());

        return redirectView;
    }

    @GetMapping("/insertSonComment")
    public RedirectView insertSonComment(Video video, Long fatherId, Long userIds, String content){
//        获取评论用户id
        Users users = CommonUtils.getUsers();
        if(users != null) {
            Comment comment = new Comment();
            comment.setUserId(users.getId());
            comment.setVideoId(video.getId());
//            父节点，无论是回复父还是父里的评论子回复，都需要父节点
            comment.setRootId(fatherId);
//            如果有userIds, 表示回复的子评论的评论
            comment.setToId(userIds);
//            评论内容
            comment.setContent(content);
            if(iCommentService.insertComment(comment)){
                System.out.println("增加成功");
            } else{
                System.out.println("增加失败");
            }
        }
        RedirectView redirectView = new RedirectView("/Video");
        redirectView.addStaticAttribute("id", video.getId());
        redirectView.addStaticAttribute("userId", video.getUserId());

        return redirectView;
    }
}

package com.example.ystand.Controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ystand.Dao.Dto.VideoDTO;
import com.example.ystand.Dao.po.Video;
import com.example.ystand.Service.IVideoService;
import com.example.ystand.Utils.CommonUtils;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-05-13
 */
@Controller
public class VideoController {

    @Resource
    private IVideoService iVideoService;

    @Resource
    private CommonController commonController;


    @GetMapping("/")
    public String toHome(Model model){
        CommonUtils.setAvatar("/images/登入头像.png");
        setModel(model);
        return "home";
    }

    @GetMapping("/home")
    public String toHomes(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            //存入全局变量给所有控制层调用
            CommonUtils.setUsers(iVideoService.getByAccount(authentication.getName()));
            //返回给视图值
            if(authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("管理员"))) {
                model.addAttribute("role", "管理员");
            }
        }
        else{
            CommonUtils.setAvatar("/images/登入头像.png");
        }
        setModel(model);
        return "home";
    }

    public void setModel(Model model){
        List<VideoDTO> videoList = iVideoService.listVideo();
        List<VideoDTO> firstSixVideos = videoList.subList(0, Math.min(videoList.size(), 6));
        List<VideoDTO> afterVideos = videoList.subList(6, videoList.size());
        model.addAttribute("videoFirst", firstSixVideos);
        model.addAttribute("videoAfter", afterVideos);
        model.addAttribute("globalUser", CommonUtils.getUsers());
        model.addAttribute("filename", "/images/加号.png");
    }

    @GetMapping("/Video")
    public String toVideo(Model model, Long id, Integer userId){
//        点击视频增加这个视频的播放量
        if(commonController.insertView(CommonUtils.getUsers().getId(), id)){
            System.out.println("播放数增加成功");
        } else {
            System.out.println("播放数增加失败");
        }
//        视频数据
        model.addAttribute("video", iVideoService.getById(id));
//        推荐视频列表
        model.addAttribute("videoList", iVideoService.ListVideo(id));
//        评论列表
        model.addAttribute("commentList", iVideoService.ListComment(id));
//        视频作者信息
        model.addAttribute("users", iVideoService.getUserById(userId));
//        用户
        model.addAttribute("globalUser", CommonUtils.getUsers());
//        用户是否点赞
        model.addAttribute("user_video", commonController.UserVideoDTOFun(id, userId));
        return "/video";
    }

    @GetMapping("/toVideoByUser")
    public String toVideoByUser(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        //分页查询数据，每页显示5条，
        Page<Video> userPage = new Page<>(pageNum,3);

        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Video::getUserId, CommonUtils.getUsers().getId());
        //分页查询的结果
        Page<Video> page = iVideoService.page(userPage,queryWrapper);
        model.addAttribute("page", page);
        model.addAttribute("video", new VideoDTO());

        model.addAttribute("globalUser", CommonUtils.getUsers());
        return "/submission_video";
    }

    @GetMapping("/toVideo_check")
    public String toVideo_check(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        //分页查询数据，每页显示5条，
        Page<Video> userPage = new Page<>(pageNum,3);

        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Video::getIsCheck, 0);
        //分页查询的结果
        Page<Video> page = iVideoService.page(userPage,queryWrapper);
        model.addAttribute("page", page);
        model.addAttribute("video", new VideoDTO());

        model.addAttribute("globalUser", CommonUtils.getUsers());
        return "/manage/video_check";
    }

    @GetMapping("/toVideo_content")
    public String toVideo_content(){
        return "/manage/video_content";
    }

    @GetMapping("/toVideo")
    public String toVideo(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        //分页查询数据，每页显示5条，
        Page<Video> userPage = new Page<>(pageNum,3);
        //分页查询的结果
        Page<Video> page = iVideoService.page(userPage,null);
        model.addAttribute("page", page);
        model.addAttribute("video", new VideoDTO());

        model.addAttribute("globalUser", CommonUtils.getUsers());
        model.addAttribute("filename", "/images/加号.png");
        return "/manage/video_content";
    }

    @GetMapping("/toSubmission")
    public String toSubmission(Model model) {
        model.addAttribute("globalUser", CommonUtils.getUsers());
        model.addAttribute("video", new VideoDTO());
        model.addAttribute("filename", "/images/加号.png");
        return "/submission";
    }

    @PostMapping("/insertOrUpdateVideo")
    public String insertUser(@ModelAttribute VideoDTO videoDTO){
        String returnUrl = "redirect:/toVideo";
        videoDTO.setIsCheck(1);
        if(videoDTO.getUserId() == null){
//            说明是用户投稿视频，需要自己设置userid
            videoDTO.setUserId(CommonUtils.getUsers().getId());
            videoDTO.setIsCheck(0);
            returnUrl = "redirect:/toSubmission";
        }
        if(videoDTO.getId() == null) {
            if (iVideoService.insertVideo(videoDTO)) {
                System.out.println("插入成功");
            } else {
                System.out.println("插入失敗");
            }
        } else {
            if(iVideoService.updateVideo(videoDTO)){
                System.out.println("修改成功");
            } else {
                System.out.println("修改失败");
            }
        }
        return returnUrl;
    }

    @GetMapping("/deleteVideo")
    public String deleteUser(Integer id, Integer isUser){
        String returnUrl = "redirect:/toVideo";
        if(isUser != null) returnUrl = "redirect:/toVideoByUser";
        if(iVideoService.deleteVideo(id)){
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
        return returnUrl;
    }

    @GetMapping("/succeedVideo")
    public String succeedVideo(Integer id){
        if(iVideoService.succeedVideo(id)){
            System.out.println("视频通过成功");
        } else {
            System.out.println("视频通过失败");
        }
        return "redirect:/toVideo_check";
    }

    @GetMapping("/failVideo")
    public String failVideo(Integer id){
        if(iVideoService.failVideo(id)){
            System.out.println("视频不通过成功");
        } else {
            System.out.println("视频不通过失败");
        }
        return "redirect:/toVideo_check";
    }
}

package com.example.ystand.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ystand.Dao.Dto.UserDTO;
import com.example.ystand.Dao.po.Users;
import com.example.ystand.Service.IUserService;
import com.example.ystand.Utils.CommonUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UserController {

    @Resource
    private IUserService iUserService;

    @GetMapping("/noPermit")
    public String noPermit(){
        return "noPermit"; //没有权限警告页
    }

    @GetMapping("/toLogin/error") //登录失败发生异常时的访问路径
    public String tologin(HttpServletRequest request, Model model) {
        AuthenticationException authenticationException =
                (AuthenticationException) request.getSession()
                        .getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if (authenticationException instanceof UsernameNotFoundException ||
                authenticationException instanceof BadCredentialsException) {
            model.addAttribute("msg", "用户名或密码错误");
        } else if (authenticationException instanceof DisabledException) {
            model.addAttribute("msg", "用户已被禁用");
        } else if (authenticationException instanceof LockedException) {
            model.addAttribute("msg", "账户被锁定");
        } else if (authenticationException instanceof
                AccountExpiredException) {
            model.addAttribute("msg", "账户过期");
        } else if (authenticationException instanceof
                CredentialsExpiredException) {
            model.addAttribute("msg", "证书过期");
        }
        return "home"; //登录失败返回登录页,并显示错误信息
    }


    @PostMapping("/register")
    public RedirectView register(@ModelAttribute UserDTO userDTO){
        if(iUserService.getByAccount(userDTO.getAccount()) == null) {
            if (iUserService.insertUser(userDTO)) {
                System.out.println("插入成功");
            } else {
                System.out.println("插入失敗");
            }
        } else{
            System.out.println("已有账号");
        }
        return new RedirectView("/");
    }

    @GetMapping("/toManageHome")
    public String toManageHome(Model model) {
        model.addAttribute("globalUser", CommonUtils.getUsers());
        return "/manageHome";
    }


    @GetMapping("/toUser")
    public String toUser(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        //分页查询数据，每页显示5条，
        Page<Users> userPage = new Page<>(pageNum,4);
        //分页查询的结果
        Page<Users> page=iUserService.page(userPage,null);
        System.out.println(page);
        model.addAttribute("page", page);
        model.addAttribute("filename", "/images/加号.png");
        model.addAttribute("globalUser", CommonUtils.getUsers());

        model.addAttribute("user", new UserDTO());
        return "/manage/user";
    }

    @PostMapping("/insertOrUpdateUser")
    public String insertUser(@ModelAttribute UserDTO userDTO){
        if(userDTO.getId() == null){
            if(iUserService.insertUser(userDTO)){
                System.out.println("插入成功");
            }else{
                System.out.println("插入失敗");
            }
        } else{
            if(iUserService.updateUser(userDTO)){
                System.out.println("修改成功");
            } else{
                System.out.println("修改失败");
            }
        }
        return "redirect:/toUser";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(Integer id){
        if(iUserService.deleteUser(id)){
            System.out.println("删除成功");
        } else{
            System.out.println("删除失败");
        }
        return "redirect:/toUser";
    }
}

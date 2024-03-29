package com.zlw.blog.controller;

import com.zlw.blog.po.Blog;
import com.zlw.blog.po.HotBlog;
import com.zlw.blog.po.User;
import com.zlw.blog.service.BlogService;
import com.zlw.blog.service.HotBlogService;
import com.zlw.blog.service.UserService;
import com.zlw.blog.utils.HotBlogUtils;
import com.zlw.blog.utils.IndexUtils;
import com.zlw.blog.utils.UserUtils;
import com.zlw.blog.vo.BlogEdit;
import com.zlw.blog.vo.BlogIndex;
import com.zlw.blog.vo.ContactInfo;
import com.zlw.blog.vo.UserIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ranger
 * @create 2019-06-02 20:38
 */
@Controller
public class MainController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private HotBlogService hotBlogService;
    @Autowired
    private UserService userService;
    //注入javaMail发送器
    @Autowired
    private JavaMailSender mailSender;

    //邮件发送者
    @Value("${spring.mail.username}")
    private String fromEmail;
    //管理员邮箱
    @Value("${ADMIN_EMAIL}")
    private String ADMIN_EMAIL;
    //分页查询每页显示数据
    @Value("${PAGE_SIZE}")
    private Integer PAGE_SIZE;

    /**
     * 获取博客信息，并展示在主页
     * 跳转到主页
     */
    @GetMapping("/")
    public String toIndex(Model model, HttpServletRequest request,
                          @RequestParam(required = false) Integer currentPage) {
        if(currentPage == null){
            currentPage = 0;
        }
        //默认差群起始页
        Page<Blog> pageObj = blogService.findBlogByPage(currentPage, PAGE_SIZE);
        List<Blog> blogList =  pageObj.getContent();
        com.zlw.blog.vo.Page page = new com.zlw.blog.vo.Page(currentPage, pageObj.getTotalPages());

        List<BlogIndex> blogIndexList = IndexUtils.getIndexList(blogList);

        UserUtils.setUserIndex(model, request);
        //设置热门博客
        List<HotBlog> hotBlogList = hotBlogService.findAllHotBlog();
        //去掉空对象
        //去掉空对象--注意：不能foreach删除
        HotBlogUtils.dealHotBlogList(hotBlogList);
        model.addAttribute("hotBlogList", hotBlogList);

        model.addAttribute("blogList", blogIndexList);
        model.addAttribute("page", page);

        return "index/index";
    }

    /**
     * 跳转到关于页面
     */
    @GetMapping("/to/about")
    public String toAbout(Model model, HttpServletRequest request) {
        UserUtils.setUserIndex(model, request);
        return "index/about";
    }

    /**
     * 跳转到联系页面
     */
    @GetMapping("/to/contact")
    public String toContact(Model model, HttpServletRequest request) {
        UserUtils.setUserIndex(model, request);
        return "index/contact";
    }

    /**
     * 跳转到登录页面
     */
    @GetMapping("/to/login")
    public String toLogin() {
        return "user/login";
    }

    /**
     * 跳转到编辑博客页面
     */
    @GetMapping("/to/blog/edit")
    public String toBlogEdit(Model model) {
        BlogEdit blogEdit = new BlogEdit(null, null, null, null, null, null, null);
        model.addAttribute("blog", blogEdit);
        return "blog/edit";
    }

    /**
     * 联系作者
     */
    @PostMapping("/contact/he")
    @ResponseBody
    public String contactHe(ContactInfo heInfo) {

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //数据库中邮箱不存在，则给注册的邮件发送验证码
                    SimpleMailMessage message = new SimpleMailMessage();
                    //发件人
                    message.setFrom(fromEmail);

                    //设置邮件的信息
                    message.setTo(ADMIN_EMAIL);
                    message.setSubject("来自" + heInfo.getUsername() + "的留言");
                    message.setText("用户邮箱:" + heInfo.getEmail() + ".\n留言内容如下：\n" + heInfo.getContent());
                    //发送
                    mailSender.send(message);
                }
            }).start();
        } catch (Exception e) {
            return "contactFail";
        }

        return "contactSuccess";
    }

    /**
     * 跳转到用户管理界面
     */
    @GetMapping("/to/user/manage")
    public String toUserManage(Integer userId, Model model) {

        User user = userService.findUserById(userId);
        model.addAttribute("user", user);

        return "user/user-manage";
    }

    /**
     * 跳转到错误提示页面
     */
    @GetMapping("/to/error")
    public String toError(){
        return "index/error";
    }

}

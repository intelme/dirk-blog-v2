package com.zlw.blog.utils;

import com.zlw.blog.po.User;
import com.zlw.blog.vo.UserIndex;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ranger
 * @create 2019-06-04 21:43
 */
public class UserUtils {

    public static void setUserIndex(Model model, HttpServletRequest request){
        //从session中获取author，判断是否登录
        User user = (User) request.getSession().getAttribute("sessionUser");
        UserIndex userIndex = null;
        if(user != null){
            userIndex = new UserIndex(user.getUserId(), user.getHeadImgUrl());
        }else{
            userIndex = new UserIndex(null, null);
        }
        model.addAttribute("user", userIndex);
    }

}

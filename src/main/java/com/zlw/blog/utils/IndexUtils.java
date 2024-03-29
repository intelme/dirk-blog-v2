package com.zlw.blog.utils;

import com.zlw.blog.po.Blog;
import com.zlw.blog.po.es.EsBlog;
import com.zlw.blog.vo.BlogIndex;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ranger
 * @create 2019-06-10 10:09
 */
public class IndexUtils {

    /**
     * 解析博客集合
     * @param blogList
     * @return
     */
    public static List<BlogIndex> getIndexList(List<Blog> blogList){
        List<BlogIndex> blogIndexList = new ArrayList<>();
        /**
         * 注意：对象里内嵌对象，解决一行两篇博文的问题
         */
        for (int i = 0; i < blogList.size(); i += 2) {
            Blog blog = blogList.get(i);
            BlogIndex blogIndex = new BlogIndex(blog.getBlogId(), blog.getCoverImgUrl(), blog.getBlogTitle(), blog.getBlogIntro(), blog.getCreateTime(), blog.getAuthor());
            /*if (blogIndex.getBlogTitle().length() > 9) {
                blogIndex.setBlogTitle(blogIndex.getBlogTitle().substring(0, 9) + "...");
            }*/
            if (blogIndex.getBlogIntro().length() > 20) {
                blogIndex.setBlogIntro(blogIndex.getBlogIntro().substring(0, 20) + "...");
            }
            //解决单数博客数据越界
            if (i + 1 < blogList.size()) {
                Blog blog2 = blogList.get(i + 1);
                BlogIndex blogIndex2 = new BlogIndex(blog2.getBlogId(), blog2.getCoverImgUrl(), blog2.getBlogTitle(), blog2.getBlogIntro(), blog2.getCreateTime(), blog2.getAuthor());
                /*if (blogIndex2.getBlogTitle().length() > 9) {
                    blogIndex2.setBlogTitle(blogIndex2.getBlogTitle().substring(0, 9) + "...");
                }*/
                if (blogIndex2.getBlogIntro().length() > 20) {
                    blogIndex2.setBlogIntro(blogIndex2.getBlogIntro().substring(0, 20) + "...");
                }
                blogIndex.setBlogIndex(blogIndex2);
            }
            blogIndexList.add(blogIndex);
        }
        return blogIndexList;
    }

    /**
     * 解析es中博客集合
     * @param blogList
     * @return
     */
    public static List<BlogIndex> getEsIndexList(List<EsBlog> blogList){
        List<BlogIndex> blogIndexList = new ArrayList<>();
        /**
         * 注意：对象里内嵌对象，解决一行两篇博文的问题
         */
        for (int i = 0; i < blogList.size(); i += 2) {
            EsBlog blog = blogList.get(i);
            BlogIndex blogIndex = new BlogIndex(blog.getBlogId(), blog.getCoverImgUrl(), blog.getBlogTitle(), blog.getBlogIntro(), blog.getCreateTime(), blog.getAuthor());
            /*if (blogIndex.getBlogTitle().length() > 9) {
                blogIndex.setBlogTitle(blogIndex.getBlogTitle().substring(0, 9) + "...");
            }*/
            if (blogIndex.getBlogIntro().length() > 20) {
                blogIndex.setBlogIntro(blogIndex.getBlogIntro().substring(0, 20) + "...");
            }
            //解决单数博客数据越界
            if (i + 1 < blogList.size()) {
                EsBlog blog2 = blogList.get(i + 1);
                BlogIndex blogIndex2 = new BlogIndex(blog2.getBlogId(), blog2.getCoverImgUrl(), blog2.getBlogTitle(), blog2.getBlogIntro(), blog2.getCreateTime(), blog2.getAuthor());
                /*if (blogIndex2.getBlogTitle().length() > 9) {
                    blogIndex2.setBlogTitle(blogIndex2.getBlogTitle().substring(0, 9) + "...");
                }*/
                if (blogIndex2.getBlogIntro().length() > 20) {
                    blogIndex2.setBlogIntro(blogIndex2.getBlogIntro().substring(0, 20) + "...");
                }
                blogIndex.setBlogIndex(blogIndex2);
            }
            blogIndexList.add(blogIndex);
        }
        return blogIndexList;
    }
}

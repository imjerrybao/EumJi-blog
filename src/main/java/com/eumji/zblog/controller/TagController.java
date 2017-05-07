package com.eumji.zblog.controller;

import com.eumji.zblog.service.TagService;
import com.eumji.zblog.vo.ArticleCustom;
import com.eumji.zblog.vo.Pager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Do
 * @package com.eumji.zblog.controller.admin
 * @name TagController
 * @date 2017/4/13
 * @time 18:54
 */
@Controller
@RequestMapping("/tags")
public class TagController {

   @Resource
    private TagService tagService;

    /**
     * 通过tag获取文章列表
     * @param pager 分页信息
     * @param tagId 标签id
     * @param model 数据视图
     * @return
     */
   @RequestMapping("/load/{tagId}")
    public String loadArticleByTag(Pager pager, @PathVariable Integer tagId, Model model){
       List<ArticleCustom> articleList = tagService.loadArticleByTag(pager,tagId);
       if (!articleList.isEmpty()){
           model.addAttribute("articleList",articleList);
           model.addAttribute("pager",pager);
           //2017-05-07修复获取tag名称错误的问题,不应该从articlelist中取,因为每篇文章可能有多个tag
           model.addAttribute("tagName",tagService.getTagById(tagId).getTagName());
       }

       return "blog/part/tagSummary";
   }

    /**
     * 获取分页信息
     * @param pager 分页对象
     * @param tagId 标签id
     * @return
     */
   @RequestMapping("/pager/{tagId}")
   @ResponseBody
   public Pager initPage(Pager pager,@PathVariable int tagId){
        tagService.ArticleTagPage(pager,tagId);
        return pager;
   }
}


package ua.com.blogengine.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ua.com.blogengine.domain.Article;

@Controller
public class FeedsController {
 
    @RequestMapping(value="/feed/rss", method = RequestMethod.GET)
    public ModelAndView getFeedInRss() {

        // TODO: add some filters 
        List<Article> items = Article.findAllArticles();
 
        ModelAndView mav = new ModelAndView();
        mav.setViewName("rssViewer");
        mav.addObject("feedContent", items);
 
        return mav;
 
    }
 
}
package ua.com.blogengine.web;

import java.util.List;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.blogengine.domain.Article;

@RequestMapping("/")
@Controller
@RooWebScaffold(path = "articles", formBackingObject = Article.class, delete = false)
public class ArticleController {

    @ModelAttribute("allArticles")
    public List<Article> populateSeedStarters() {
        return Article.findAllArticles();
    }

    @RequestMapping(/*produces = "text/html", */method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size,
            Model model) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            model.addAttribute("articles", Article.findArticleEntries(firstResult, sizeNo));
            float nrOfPages = (float) Article.countArticles() / sizeNo;
            model.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            model.addAttribute("articles", Article.findAllArticles());
        }
        
        addDateTimeFormatPatterns(model);
        return "articles/list";
    }

    @RequestMapping(value = "/{section}/{id}/{title}", /*produces = "text/html", */ method = RequestMethod.GET)
    public String show(@PathVariable String section,
            @PathVariable("id") Long id, 
            @PathVariable String title,
            Model model) {
        addDateTimeFormatPatterns(model);
        model.addAttribute("article", Article.findArticle(id));
        model.addAttribute("itemId", id);
        return "articles/view";
    }

}

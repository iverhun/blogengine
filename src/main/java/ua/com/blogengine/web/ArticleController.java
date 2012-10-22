package ua.com.blogengine.web;

import java.util.List;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import ua.com.blogengine.domain.Article;
import ua.com.blogengine.domain.Section;

@RequestMapping("/")
@Controller
@RooWebScaffold(path = "articles", formBackingObject = Article.class, delete = false)
@SessionAttributes({"article"})
public class ArticleController {

    @ModelAttribute("allArticles")
    public List<Article> populateArticles() {
        return Article.findAllArticles();
    }

    @ModelAttribute("allSections")
    public List<Section> populateSections() {
        return Section.findAllSections();
    }

    @RequestMapping(produces = "text/html", method = RequestMethod.GET)
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

    @RequestMapping(value = "/{section}/{id}/{title}", produces = "text/html",  method = RequestMethod.GET)
    public String show(@PathVariable String section,
            @PathVariable("id") Long id, 
            @PathVariable String title, Model model) {
        addDateTimeFormatPatterns(model);
        
        Article article = Article.findArticle(id);
        if (!article.getUrlTitle().equals(title + Article.PAGE_TITLE_SUFFIX) || !article.getSection().getUrlName().equals(section)) {
            model.asMap().clear();
            return "redirect:" + getViewArticleUrl(article);
        } else {
            model.addAttribute("article", Article.findArticle(id));
            return "articles/view";
        }
    }

    @RequestMapping(value = "/{id}", produces = "text/html",  method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, 
            Model model) {
        Article article = Article.findArticle(id);
        return "redirect:" + getViewArticleUrl(article);
    }

    @RequestMapping(value = "/admin/edit", produces = "text/html", method = RequestMethod.GET)
    public String edit(@RequestParam(value="articleId") Long id, 
            Model model) {
        addDateTimeFormatPatterns(model);
        model.addAttribute("article", Article.findArticle(id));
        return "articles/edit";
    }


    @RequestMapping(value = "/admin/new", produces = "text/html", method = RequestMethod.GET)
    public String newArticle(Model model) {
        addDateTimeFormatPatterns(model);
        model.addAttribute("article", new Article());
        return "articles/edit";
    }

    @RequestMapping(value = "/admin/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("article") Article article,
            BindingResult bindingResult,
            Model model) {
        Article savedArticle = article.saveOrUpdate();
        return "redirect:" + getViewArticleUrl(savedArticle);
    }

    private String getViewArticleUrl(Article article) {
        return "/" + article.getSection().getUrlName() + "/" + article.getId() + "/" + article.getUrlTitle();
    }
}

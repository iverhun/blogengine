// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ua.com.blogengine.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import ua.com.blogengine.domain.Article;
import ua.com.blogengine.domain.Section;
import ua.com.blogengine.web.ArticleController;

privileged aspect ArticleController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String ArticleController.create(@Valid Article article, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, article);
            return "articles/create";
        }
        uiModel.asMap().clear();
        article.persist();
        return "redirect:/articles/" + encodeUrlPathSegment(article.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String ArticleController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Article());
        return "articles/create";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String ArticleController.update(@Valid Article article, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, article);
            return "articles/update";
        }
        uiModel.asMap().clear();
        article.merge();
        return "redirect:/articles/" + encodeUrlPathSegment(article.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String ArticleController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Article.findArticle(id));
        return "articles/update";
    }
    
    void ArticleController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("article_submissiondate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("article_publicationdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    void ArticleController.populateEditForm(Model uiModel, Article article) {
        uiModel.addAttribute("article", article);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("sections", Section.findAllSections());
    }
    
    String ArticleController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
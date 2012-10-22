package ua.com.blogengine.util;

import java.text.MessageFormat;

import ua.com.blogengine.domain.Article;

public class UrlUtils {
    // TODO: move this to properties file
    public static final String BASE_URL = "http://www.samvydav.com.ua";
    // TODO: move this to properties file
    public static final String ARTICLE_URL_PATTERN = BASE_URL + "/{0}/{1}/{2}";

    public static String getArticleUrl(Article article) {
        return MessageFormat.format(ARTICLE_URL_PATTERN, article.getSection().getUrlName(), article.getId(), article.getUrlTitle());
    }
}

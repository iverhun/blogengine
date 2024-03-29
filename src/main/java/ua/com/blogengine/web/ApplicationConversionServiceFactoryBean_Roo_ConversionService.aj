// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ua.com.blogengine.web;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import ua.com.blogengine.domain.Article;
import ua.com.blogengine.web.ApplicationConversionServiceFactoryBean;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    public Converter<Article, String> ApplicationConversionServiceFactoryBean.getArticleToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ua.com.blogengine.domain.Article, java.lang.String>() {
            public String convert(Article article) {
                return new StringBuilder().append(article.getTitle()).append(' ').append(article.getUrlTitle()).append(' ').append(article.getContent()).append(' ').append(article.getHtmlPreamble()).toString();
            }
        };
    }
    
    public Converter<Long, Article> ApplicationConversionServiceFactoryBean.getIdToArticleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ua.com.blogengine.domain.Article>() {
            public ua.com.blogengine.domain.Article convert(java.lang.Long id) {
                return Article.findArticle(id);
            }
        };
    }
    
    public Converter<String, Article> ApplicationConversionServiceFactoryBean.getStringToArticleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ua.com.blogengine.domain.Article>() {
            public ua.com.blogengine.domain.Article convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Article.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getArticleToStringConverter());
        registry.addConverter(getIdToArticleConverter());
        registry.addConverter(getStringToArticleConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}

package ua.com.blogengine.web.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import ua.com.blogengine.domain.Article;
import ua.com.blogengine.util.UrlUtils;

import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Content;
import com.sun.syndication.feed.rss.Item;

public class RssFeedView extends AbstractRssFeedView {
    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed, HttpServletRequest request) {

        //TODO: move these strings to properties file
        feed.setTitle("SamВидав");
        feed.setDescription("Жодної цензури");
        feed.setLink("http://www.samvydav.com.ua");

        super.buildFeedMetadata(model, feed, request);
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<Article> articles = (List<Article>) model.get("feedContent");
        List<Item> items = new ArrayList<Item>(articles.size());

        for (Article article : articles) {

            Item item = new Item();

            Content content = new Content();
            content.setValue(article.getHtmlPreamble());
            item.setContent(content);

            item.setTitle(article.getTitle());

            item.setLink(UrlUtils.getArticleUrl(article));
            item.setPubDate(article.getPublicationDate());

            items.add(item);
        }

        return items;
    }
}

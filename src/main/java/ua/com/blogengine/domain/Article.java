package ua.com.blogengine.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import ua.com.blogengine.util.TransliterationUtil;
import ua.com.blogengine.util.WikiUtils;
import ua.com.blogengine.util.WikiUtils.HtmlArticleEdit;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "article")
@RooSolrSearchable
public class Article {

    public static final String PAGE_TITLE_SUFFIX = ".html";

    @NotNull
    @Column
    private String title;

    @Column
    private String urlTitle;

    @NotNull
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(columnDefinition = "LONGTEXT")
    private String htmlPreamble;

    @Column(columnDefinition = "LONGTEXT")
    private String htmlContent;

    @Column
    @DateTimeFormat(style = "M-")
    private Date submissionDate;

    @Column
    @DateTimeFormat(style = "M-")
    private Date publicationDate;

    @Column
    private boolean published;

    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    private Section section;

    public void setTitle(String title) {
        this.title = title;
        this.urlTitle = TransliterationUtil.toLatin(title) + PAGE_TITLE_SUFFIX;
    }

    public void setContent(String content) {
        this.content = content;
        HtmlArticleEdit articleEdit = WikiUtils.wikiToHtml(content);
        this.htmlPreamble = articleEdit.getPreamble();
        this.htmlContent = articleEdit.getFullText();
    }

    @Transactional
    public ua.com.blogengine.domain.Article merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Article merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public ua.com.blogengine.domain.Article saveOrUpdate() {
        if (getId() == null) {
            persist();
            return this;
        } else {
            return merge();
        }
    }

    public static List<ua.com.blogengine.domain.Article> findArticlesBySection(String sectionUrlName, Integer firstResult, Integer maxResults) {
        if (StringUtils.isBlank(sectionUrlName)) {
            throw new IllegalArgumentException("The section argument is required");
        }
        CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        CriteriaQuery<Article> criteria = criteriaBuilder.createQuery(Article.class);
        Root<Article> entityRoot = criteria.from(Article.class);
        Order order = criteriaBuilder.desc(entityRoot.get("publicationDate"));
        criteria.select(entityRoot).where(criteriaBuilder.equal(entityRoot.get("section").get("urlName"), sectionUrlName)).orderBy(order);
        TypedQuery<Article> q = entityManager().createQuery(criteria);
        if (firstResult != null) {
            q.setFirstResult(firstResult);
        }
        if (maxResults != null) {
            q.setMaxResults(maxResults);
        }
        return q.getResultList();
    }
}

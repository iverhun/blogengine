package ua.com.blogengine.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import ua.com.blogengine.util.TransliterationUtil;
import ua.com.blogengine.util.WikiUtils;
import ua.com.blogengine.util.WikiUtils.HtmlArticleEdit;

//import org.springframework.beans.factory.annotation.Required;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "article")
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
	// TODO ##PLAY## @Formats.DateTime(pattern = "dd/MM/yyyy")
	@DateTimeFormat(style = "M-")
	private Date submissionDate;

	@Column
	// TODO ##PLAY## @Formats.DateTime(pattern = "dd/MM/yyyy")
	@DateTimeFormat(style = "M-")
	private Date publicationDate;

	@Column
	private boolean published;

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

	// @Column
	@ManyToOne
	@JoinColumn(name = "section_id", referencedColumnName = "id")
	private Section section;

    @Transactional
    public Article merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Article merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public Article saveOrUpdate() {
	    if (getId() == null) {
	        persist();
	        return this;
	    } else {
	        return merge();
	    }
	}
}

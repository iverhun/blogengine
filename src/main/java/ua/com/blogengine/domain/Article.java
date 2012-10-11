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

//import org.springframework.beans.factory.annotation.Required;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "article")
public class Article {

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

	// @Column
	@ManyToOne
	@JoinColumn(name = "section_id", referencedColumnName = "id")
	private Section section;

}

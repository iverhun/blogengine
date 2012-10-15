package ua.com.blogengine.util;

import java.io.StringWriter;

import org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder;

import ua.com.blogengine.wiki.MediaWikiLanguage;


public class WikiUtils {

    public static HtmlArticleEdit wikiToHtml(String wiki) {

        MarkupParser markupParser = new MarkupParser(new MediaWikiLanguage());
        //markupParser = new MarkupParser(new TextileLanguage());

        int readMorePosition = wiki.indexOf(MediaWikiLanguage.READ_MORE_TAG);
        String wikiPreamble = readMorePosition >= 0 ? wiki.substring(0, readMorePosition) : wiki;
        String htmlPreamble = parseToHtml(markupParser, wikiPreamble);

        String fullText = parseToHtml(markupParser, wiki);

        return new HtmlArticleEdit(htmlPreamble, fullText);
    }

    public static class HtmlArticleEdit {
        private String preamble;

        private String fullText;

        public HtmlArticleEdit(String preamble, String fullText) {
            this.preamble = preamble;
            this.fullText = fullText;
        }

        public String getPreamble() {
            return preamble;
        }

        public void setPreamble(String preamble) {
            this.preamble = preamble;
        }

        public String getFullText() {
            return fullText;
        }

        public void setFullText(String fullText) {
            this.fullText = fullText;
        }
    }


    private static String parseToHtml(MarkupParser markupParser, String wiki) {
        StringWriter writer = new StringWriter();
        HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
        // avoid the <html> and <body> tags
        builder.setEmitAsDocument(false);

        markupParser.setBuilder(builder);
        markupParser.parse(wiki);
        markupParser.setBuilder(null);

        return writer.toString();
    }
}

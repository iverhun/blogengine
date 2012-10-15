package ua.com.blogengine.wiki;

import java.util.regex.Pattern;

import org.eclipse.mylyn.wikitext.core.parser.markup.token.PatternLiteralReplacementToken;

public class MediaWikiLanguage extends org.eclipse.mylyn.wikitext.mediawiki.core.MediaWikiLanguage {

    public static final String READ_MORE_TAG = "{==}";

    @Override
    protected void addStandardTokens(PatternBasedSyntax tokenSyntax) {
        super.addStandardTokens(tokenSyntax);
        tokenSyntax.add(new PatternLiteralReplacementToken("(" + Pattern.quote(READ_MORE_TAG) + ")", "<p/>"));
    }
}

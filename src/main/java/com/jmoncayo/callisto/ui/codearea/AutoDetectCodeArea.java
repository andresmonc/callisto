package com.jmoncayo.callisto.ui.codearea;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoDetectCodeArea extends CodeArea {

    private static final Pattern JSON_PATTERN = Pattern.compile(
            "(\"[^\"]*\"(?=\\s*:))" +  // JSON keys
                    "|(\"[^\"]*\")" +         // JSON string values
                    "|(-?\\b\\d+\\.?\\d*\\b)" + // Numbers
                    "|\\b(true|false|null)\\b" // Booleans and null
    );

    private static final Pattern HTML_PATTERN = Pattern.compile(
            "(<[^>]+>)" +  // HTML tags
                    "|(\\b\\w+\\b)" // Words (for attributes, etc.)
    );

    private static final Pattern XML_PATTERN = Pattern.compile(
            "(<\\?xml[^>]*\\?>)" + // XML declaration
                    "|(<[^>]+>)" + // XML tags
                    "|(\\b\\w+\\b)" // XML elements
    );

    private static final Pattern JS_PATTERN = Pattern.compile(
            "(//.*$)" + // Single-line comments
                    "|(/\\*[^*]*\\*/)" + // Multi-line comments
                    "|(\"[^\"]*\")" + // String literals
                    "|(-?\\b\\d+\\.?\\d*\\b)" + // Numbers
                    "|\\b(var|let|const|function|return|if|else|while|for|class|new|import|export|true|false|null)\\b" // Keywords
    );

    public AutoDetectCodeArea() {
        textProperty().addListener((obs, oldText, newText) -> applyHighlighting());
        setStyle("-fx-font-family: 'monospace'; -fx-text-fill: white;");
        applyHighlighting();
    }

    private void applyHighlighting() {
        String text = getText();
        if (text.startsWith("{") || text.startsWith("[")) {
            // Likely JSON
            setStyleSpans(0, computeHighlighting(text, JSON_PATTERN));
        } else if (text.contains("<html>") || text.contains("<!DOCTYPE html>") || text.contains("</html>")) {
            // Likely HTML
            setStyleSpans(0, computeHighlighting(text, HTML_PATTERN));
        } else if (text.contains("<xml>") || text.contains("<!DOCTYPE xml>") || text.contains("</xml>")) {
            // Likely XML
            setStyleSpans(0, computeHighlighting(text, XML_PATTERN));
        } else if (text.contains("function") || text.contains("let") || text.contains("const")) {
            // Likely JavaScript
            setStyleSpans(0, computeHighlighting(text, JS_PATTERN));
        } else {
            // Default, you can add more logic or highlight text differently
            setStyleSpans(0, computeHighlighting(text, HTML_PATTERN));
        }
    }

    private StyleSpans<Collection<String>> computeHighlighting(String text, Pattern pattern) {
        Matcher matcher = pattern.matcher(text);
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        int lastEnd = 0;

        while (matcher.find()) {
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastEnd);

            if (matcher.group(1) != null) { // Matched group for a specific language feature
                spansBuilder.add(Collections.singleton("json-key"), matcher.end() - matcher.start());
            } else if (matcher.group(2) != null) {
                spansBuilder.add(Collections.singleton("json-string"), matcher.end() - matcher.start());
            } else if (matcher.group(3) != null) {
                spansBuilder.add(Collections.singleton("json-number"), matcher.end() - matcher.start());
            } else if (matcher.group(4) != null) {
                spansBuilder.add(Collections.singleton("json-boolean"), matcher.end() - matcher.start());
            } else if (matcher.group(5) != null) { // Matched HTML/XML tags
                spansBuilder.add(Collections.singleton("html-tag"), matcher.end() - matcher.start());
            } else if (matcher.group(6) != null) { // Matched keywords
                spansBuilder.add(Collections.singleton("js-keyword"), matcher.end() - matcher.start());
            }

            lastEnd = matcher.end();
        }

        spansBuilder.add(Collections.emptyList(), text.length() - lastEnd);
        return spansBuilder.create();
    }
}

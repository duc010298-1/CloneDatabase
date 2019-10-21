package com.github.duc0102981.clone_database.service;

import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class CSSInline {

    public String convert(String html) {
        final String style = "style";
        Document doc = Jsoup.parse(html);
        Elements els = doc.select(style);
        for (Element e : els) {
            String styleRules = e.getAllElements().get(0).data().replaceAll("\n", "").trim();
            String delims = "{}";
            StringTokenizer st = new StringTokenizer(styleRules, delims);
            while (st.countTokens() > 1) {
                String selector = st.nextToken();
                String properties = st.nextToken();
                Elements selectedElements;
                try {
                    selectedElements = doc.select(selector);
                } catch (Exception ex) {
                    continue;
                }
                for (Element selElem : selectedElements) {
                    String oldProperties = selElem.attr(style);
                    if (selElem.tagName().equals("pre")) {
                        oldProperties = oldProperties.replace("height", "z");
                        properties = properties.replace("height", "z");
                    }
                    if (selElem.className().equals("page-container")) {
                        oldProperties = oldProperties.replace("margin", "z");
                        properties = properties.replace("margin", "z");
                    }
                    selElem.attr(style, oldProperties.length() > 0 ?
                            concatenateProperties(oldProperties, properties) : properties);
                }
            }
            e.remove();
        }
        els = doc.getAllElements();
        for (Element e : els) {
            e.removeAttr("id");
            e.removeAttr("rows");
            e.removeAttr("class");
            e.removeAttr("disabled");
            e.removeAttr("placeholder");
        }
        return doc.html();
    }

    private String concatenateProperties(String oldProp, String newProp) {
        oldProp = oldProp.trim();
        if (!newProp.endsWith(";"))
            newProp += ";";
        return newProp + oldProp;
    }
}

package se.proxus.kanon.utils.templates;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public final class PageModel {

    private Document document;
    private String url;
    private String title;
    private String[] content;

    public PageModel(final String url) {
        setUrl(url);
        try {
            loadPage();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPage() throws Exception {
        final UrlValidator urlValidator = new UrlValidator();

        if (urlValidator.isValid(getUrl())) {
            setDocument(Jsoup.connect(getUrl()).get());
        } else {
            setDocument(Jsoup.parse(new File(getUrl()), "UTF-8"));
        }

        setTitle(getDocument().title());
        setContent(getDocument().outerHtml().split("\n"));
    }

    public Elements getElements(final String cssQuery) {
        return getDocument().select(cssQuery);
    }

    public List<String> getImages(final String baseUrl) {
        final List<String> tempList = new LinkedList<String>();
        getElements("img").stream()
                .filter(imageElement -> !tempList.contains(baseUrl + imageElement.attr("src")))
                .forEach(imageElement -> {
                    tempList.add(baseUrl + imageElement.attr("src"));
                });
        return tempList;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(final Document document) {
        this.document = document;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String[] getContent() {
        return content;
    }

    public void setContent(final String[] content) {
        this.content = content;
    }
}
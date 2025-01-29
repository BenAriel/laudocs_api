package br.api.laudocs.laudocs_api.domain.entities;

import java.time.LocalDateTime;

public class NewsItem {
    private String title;
    private String content;
    private LocalDateTime dateTime;

    public NewsItem(String title, String content, LocalDateTime dateTime) {
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
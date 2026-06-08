package com.mock.blog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mock.blog.model.BlogCard;

import java.time.LocalDate;
import java.util.List;

public record BlogCardResponse(
        String title,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        int duration,
        String description,
        List<String> tags,
        String link,
        String imageSrc,
        String imageAlt,
        String urlRawMarkDown
) {
    public static BlogCardResponse from(BlogCard card) {
        return new BlogCardResponse(
                card.title(),
                card.date(),
                card.duration(),
                card.description(),
                card.tags(),
                card.link(),
                card.imageSrc(),
                card.imageAlt(),
                card.urlRawMarkDown()
        );
    }
}

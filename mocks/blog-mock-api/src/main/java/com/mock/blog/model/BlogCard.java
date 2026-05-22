package com.mock.blog.model;

import java.time.LocalDate;
import java.util.List;

public record BlogCard(
        String title,
        LocalDate date,
        int duration,
        String description,
        List<String> tags,
        String link,
        String imageSrc,
        String imageAlt
) {}

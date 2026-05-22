package com.mock.blog.controller;

import com.mock.blog.dto.BlogCardResponse;
import com.mock.blog.service.BlogCardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blog-cards")
public class BlogCardController {

    private final BlogCardService blogCardService;

    public BlogCardController(BlogCardService blogCardService) {
        this.blogCardService = blogCardService;
    }

    /**
     * Returns a paginated list of blog cards.
     *
     * <p>Examples:
     * <ul>
     *   <li>GET /api/blog-cards?page=0&size=10&sort=date,desc</li>
     *   <li>GET /api/blog-cards?page=1&size=5&sort=title,asc&tag=java</li>
     *   <li>GET /api/blog-cards?page=0&size=20&sort=tag,desc&title=spring</li>
     * </ul>
     *
     * @param pageable Spring auto-resolved Pageable from query params (page, size, sort)
     * @param tag      Optional filter by tag (case-insensitive partial match)
     * @param title    Optional filter by title (case-insensitive partial match)
     * @return Paginated response with blog card data
     */
    @GetMapping
    public ResponseEntity<Page<BlogCardResponse>> getBlogCards(
            @PageableDefault(size = 10, sort = "date", direction = Sort.Direction.DESC)
            Pageable pageable,

            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String title
    ) {
        Page<BlogCardResponse> result = blogCardService.getBlogCards(pageable, tag, title);
        return ResponseEntity.ok(result);
    }
}

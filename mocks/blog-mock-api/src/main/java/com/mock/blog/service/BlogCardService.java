package com.mock.blog.service;

import com.mock.blog.dto.BlogCardResponse;
import com.mock.blog.model.BlogCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

@Service
public class BlogCardService {

    private static final List<BlogCard> MOCK_DATA = List.of(
            new BlogCard(
                    "Getting Started with Java 21 Virtual Threads",
                    LocalDate.of(2024, 1, 10),
                    8,
                    "Explore the power of Project Loom and virtual threads introduced in Java 21 for high-concurrency applications.",
                    List.of("java", "concurrency", "virtual-threads", "jvm"),
                    "/blog/java-21-virtual-threads",
                    "https://images.unsplash.com/photo-1461749280684-dccba630e2f6?w=800",
                    "Java code on a laptop screen"
            ),
            new BlogCard(
                    "Spring Boot 3.x Migration Guide",
                    LocalDate.of(2024, 2, 5),
                    12,
                    "A comprehensive guide to migrating your Spring Boot 2.x applications to the new Spring Boot 3.x with Jakarta EE support.",
                    List.of("spring", "spring-boot", "migration", "jakarta-ee"),
                    "/blog/spring-boot-3-migration",
                    "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=800",
                    "Spring Boot logo on a monitor"
            ),
            new BlogCard(
                    "Reactive Programming with Spring WebFlux",
                    LocalDate.of(2024, 2, 20),
                    15,
                    "Deep dive into reactive programming paradigms with Spring WebFlux, Project Reactor, and non-blocking I/O.",
                    List.of("spring", "webflux", "reactive", "java"),
                    "/blog/reactive-spring-webflux",
                    "https://images.unsplash.com/photo-1518770660439-4636190af475?w=800",
                    "Abstract data flow visualization"
            ),
            new BlogCard(
                    "Building REST APIs with Spring Data JPA",
                    LocalDate.of(2024, 3, 1),
                    10,
                    "Learn how to build robust and scalable REST APIs using Spring Data JPA with best practices and performance tips.",
                    List.of("spring", "jpa", "rest", "database"),
                    "/blog/rest-apis-spring-data-jpa",
                    "https://images.unsplash.com/photo-1544383835-bda2bc66a55d?w=800",
                    "Database schema diagram"
            ),
            new BlogCard(
                    "Docker and Kubernetes for Java Developers",
                    LocalDate.of(2024, 3, 15),
                    20,
                    "Containerize your Spring Boot applications with Docker and orchestrate them with Kubernetes for production-grade deployments.",
                    List.of("docker", "kubernetes", "devops", "java"),
                    "/blog/docker-kubernetes-java",
                    "https://images.unsplash.com/photo-1605745341112-85968b19335b?w=800",
                    "Container ship representing Docker containers"
            ),
            new BlogCard(
                    "Securing Spring Boot Apps with OAuth2",
                    LocalDate.of(2024, 3, 28),
                    18,
                    "Implement OAuth2 and OpenID Connect authentication in your Spring Boot applications using Spring Security 6.",
                    List.of("spring", "security", "oauth2", "authentication"),
                    "/blog/spring-boot-oauth2",
                    "https://images.unsplash.com/photo-1555949963-ff9fe0c870eb?w=800",
                    "Lock and key representing security"
            ),
            new BlogCard(
                    "Mastering Java Streams API",
                    LocalDate.of(2024, 4, 5),
                    9,
                    "Master the Java Streams API with practical examples covering collect, filter, map, reduce and parallel processing.",
                    List.of("java", "streams", "functional-programming", "collections"),
                    "/blog/java-streams-api-mastery",
                    "https://images.unsplash.com/photo-1504639725590-34d0984388bd?w=800",
                    "Stream of data flowing through pipes"
            ),
            new BlogCard(
                    "Event-Driven Architecture with Apache Kafka",
                    LocalDate.of(2024, 4, 18),
                    22,
                    "Design and implement event-driven microservices using Apache Kafka as your message broker with Spring Kafka integration.",
                    List.of("kafka", "microservices", "event-driven", "architecture"),
                    "/blog/event-driven-kafka",
                    "https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=800",
                    "Network topology diagram"
            ),
            new BlogCard(
                    "Clean Architecture in Spring Boot",
                    LocalDate.of(2024, 5, 2),
                    14,
                    "Applying Uncle Bob's Clean Architecture principles to Spring Boot projects for maximum maintainability and testability.",
                    List.of("architecture", "spring-boot", "clean-code", "design-patterns"),
                    "/blog/clean-architecture-spring-boot",
                    "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=800",
                    "Blueprint drawing representing clean architecture"
            ),
            new BlogCard(
                    "Testing Spring Boot Applications",
                    LocalDate.of(2024, 5, 20),
                    11,
                    "A complete guide to testing Spring Boot applications with JUnit 5, Mockito, TestContainers and integration test strategies.",
                    List.of("testing", "spring-boot", "junit", "mockito"),
                    "/blog/testing-spring-boot",
                    "https://images.unsplash.com/photo-1516116216624-53e697fedbea?w=800",
                    "Checklist representing test cases"
            ),
            new BlogCard(
                    "GraphQL with Spring Boot",
                    LocalDate.of(2024, 6, 3),
                    13,
                    "Build flexible and efficient APIs using GraphQL with Spring for GraphQL, replacing or complementing your REST endpoints.",
                    List.of("graphql", "spring-boot", "api", "java"),
                    "/blog/graphql-spring-boot",
                    "https://images.unsplash.com/photo-1504711434969-e33886168f5c?w=800",
                    "GraphQL schema visualization"
            ),
            new BlogCard(
                    "Observability with Micrometer and Prometheus",
                    LocalDate.of(2024, 6, 17),
                    16,
                    "Set up full observability for your Spring Boot microservices using Micrometer, Prometheus, Grafana and distributed tracing.",
                    List.of("observability", "monitoring", "spring-boot", "devops"),
                    "/blog/observability-micrometer-prometheus",
                    "https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800",
                    "Dashboard with metrics and charts"
            ),
            new BlogCard(
                    "Java Records and Sealed Classes",
                    LocalDate.of(2024, 7, 1),
                    7,
                    "Explore modern Java features like Records for immutable data classes and Sealed Classes for exhaustive type hierarchies.",
                    List.of("java", "records", "sealed-classes", "modern-java"),
                    "/blog/java-records-sealed-classes",
                    "https://images.unsplash.com/photo-1456406644174-8ddd4cd52a06?w=800",
                    "Java programming concepts visualization"
            ),
            new BlogCard(
                    "Microservices Communication Patterns",
                    LocalDate.of(2024, 7, 15),
                    19,
                    "Explore synchronous and asynchronous communication patterns for microservices including REST, gRPC, message queues and sagas.",
                    List.of("microservices", "architecture", "grpc", "messaging"),
                    "/blog/microservices-communication-patterns",
                    "https://images.unsplash.com/photo-1573164713988-8665fc963095?w=800",
                    "Network nodes communicating with each other"
            ),
            new BlogCard(
                    "Spring Cloud Gateway Deep Dive",
                    LocalDate.of(2024, 7, 29),
                    17,
                    "Configure and customize Spring Cloud Gateway as your API Gateway with routing, filters, rate limiting and circuit breakers.",
                    List.of("spring", "cloud", "gateway", "microservices"),
                    "/blog/spring-cloud-gateway",
                    "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=800",
                    "Gateway representing API routing"
            ),
            new BlogCard(
                    "Redis Caching Strategies with Spring",
                    LocalDate.of(2024, 8, 10),
                    12,
                    "Implement effective caching strategies using Redis and Spring Cache abstraction to boost your application performance.",
                    List.of("redis", "caching", "spring", "performance"),
                    "/blog/redis-caching-spring",
                    "https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=800",
                    "Redis cache architecture diagram"
            ),
            new BlogCard(
                    "Pattern Matching in Java",
                    LocalDate.of(2024, 8, 25),
                    8,
                    "Leverage pattern matching for instanceof and switch expressions introduced in recent Java versions for cleaner code.",
                    List.of("java", "pattern-matching", "modern-java", "switch"),
                    "/blog/java-pattern-matching",
                    "https://images.unsplash.com/photo-1516259762381-22954d7d3ad2?w=800",
                    "Code patterns on screen"
            ),
            new BlogCard(
                    "Database Migrations with Flyway",
                    LocalDate.of(2024, 9, 5),
                    10,
                    "Manage your database schema evolution reliably with Flyway migrations integrated in your Spring Boot application lifecycle.",
                    List.of("database", "flyway", "spring-boot", "migrations"),
                    "/blog/database-migrations-flyway",
                    "https://images.unsplash.com/photo-1544383835-bda2bc66a55d?w=800",
                    "Database migration flow chart"
            ),
            new BlogCard(
                    "CI/CD Pipelines for Spring Boot with GitHub Actions",
                    LocalDate.of(2024, 9, 20),
                    14,
                    "Build automated CI/CD pipelines for your Spring Boot applications using GitHub Actions with Docker and cloud deployments.",
                    List.of("cicd", "github-actions", "devops", "spring-boot"),
                    "/blog/cicd-github-actions-spring",
                    "https://images.unsplash.com/photo-1618401471353-b98afee0b2eb?w=800",
                    "Pipeline flow diagram"
            ),
            new BlogCard(
                    "Functional Interfaces and Lambdas in Java",
                    LocalDate.of(2024, 10, 3),
                    9,
                    "Master Java's functional programming features including lambdas, method references, and the built-in functional interfaces.",
                    List.of("java", "functional-programming", "lambdas", "streams"),
                    "/blog/java-functional-interfaces",
                    "https://images.unsplash.com/photo-1510915361894-db8b60106cb1?w=800",
                    "Lambda symbol on a chalkboard"
            ),
            new BlogCard(
                    "Spring Batch for Large-Scale Data Processing",
                    LocalDate.of(2024, 10, 18),
                    21,
                    "Process millions of records efficiently using Spring Batch with chunk-oriented processing, job scheduling and error handling.",
                    List.of("spring", "batch", "data-processing", "java"),
                    "/blog/spring-batch-processing",
                    "https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=800",
                    "Data processing pipeline visualization"
            ),
            new BlogCard(
                    "Building CLI Tools with Spring Shell",
                    LocalDate.of(2024, 11, 1),
                    11,
                    "Create interactive command-line applications using Spring Shell with auto-completion, help commands and rich terminal output.",
                    List.of("spring", "cli", "spring-shell", "tools"),
                    "/blog/cli-tools-spring-shell",
                    "https://images.unsplash.com/photo-1629654297299-c8506221ca97?w=800",
                    "Terminal window with CLI commands"
            ),
            new BlogCard(
                    "Hexagonal Architecture with Spring Boot",
                    LocalDate.of(2024, 11, 15),
                    16,
                    "Implement ports and adapters pattern (Hexagonal Architecture) in Spring Boot to decouple your domain logic from infrastructure.",
                    List.of("architecture", "hexagonal", "spring-boot", "design-patterns"),
                    "/blog/hexagonal-architecture-spring",
                    "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=800",
                    "Hexagonal architecture diagram"
            ),
            new BlogCard(
                    "Native Images with Spring Boot and GraalVM",
                    LocalDate.of(2024, 11, 30),
                    18,
                    "Compile your Spring Boot applications to native executables using GraalVM for blazing-fast startup times and lower memory usage.",
                    List.of("spring-boot", "graalvm", "native-image", "performance"),
                    "/blog/native-images-graalvm-spring",
                    "https://images.unsplash.com/photo-1518770660439-4636190af475?w=800",
                    "Rocket representing fast startup times"
            ),
            new BlogCard(
                    "Exception Handling Best Practices in Spring",
                    LocalDate.of(2024, 12, 10),
                    8,
                    "Design robust error handling in Spring Boot using @ControllerAdvice, ProblemDetail (RFC 9457) and custom exception hierarchies.",
                    List.of("spring", "exception-handling", "rest", "best-practices"),
                    "/blog/exception-handling-spring",
                    "https://images.unsplash.com/photo-1555949963-ff9fe0c870eb?w=800",
                    "Error handling flow diagram"
            ),
            new BlogCard(
                    "Reactive Streams with RxJava and Spring",
                    LocalDate.of(2025, 1, 7),
                    13,
                    "Integrate RxJava reactive streams into Spring applications for complex asynchronous workflows and data transformation pipelines.",
                    List.of("reactive", "rxjava", "spring", "async"),
                    "/blog/rxjava-spring-integration",
                    "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?w=800",
                    "Stream processing visualization"
            ),
            new BlogCard(
                    "Distributed Tracing with OpenTelemetry",
                    LocalDate.of(2025, 1, 22),
                    15,
                    "Implement distributed tracing across your microservices using OpenTelemetry, Jaeger and Spring Boot Actuator for end-to-end visibility.",
                    List.of("observability", "tracing", "opentelemetry", "microservices"),
                    "/blog/distributed-tracing-opentelemetry",
                    "https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800",
                    "Distributed trace visualization"
            ),
            new BlogCard(
                    "Advanced Spring Security: JWT Authentication",
                    LocalDate.of(2025, 2, 5),
                    16,
                    "Implement stateless JWT authentication with refresh tokens, role-based access control and method-level security in Spring Boot.",
                    List.of("spring", "security", "jwt", "authentication"),
                    "/blog/spring-security-jwt",
                    "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?w=800",
                    "JWT token structure visualization"
            ),
            new BlogCard(
                    "Zero Downtime Deployments with Spring Boot",
                    LocalDate.of(2025, 2, 20),
                    12,
                    "Achieve zero downtime deployments for Spring Boot applications using blue-green deployments, canary releases and health checks.",
                    List.of("devops", "deployment", "spring-boot", "kubernetes"),
                    "/blog/zero-downtime-deployments",
                    "https://images.unsplash.com/photo-1629654297299-c8506221ca97?w=800",
                    "Blue green deployment diagram"
            ),
            new BlogCard(
                    "Structured Concurrency in Java 21",
                    LocalDate.of(2025, 3, 8),
                    14,
                    "Use Java 21's Structured Concurrency API to manage complex concurrent tasks with better readability and error propagation.",
                    List.of("java", "concurrency", "virtual-threads", "modern-java"),
                    "/blog/java-21-structured-concurrency",
                    "https://images.unsplash.com/photo-1461749280684-dccba630e2f6?w=800",
                    "Concurrent threads visualization"
            ),
            new BlogCard(
                    "API Versioning Strategies in Spring Boot",
                    LocalDate.of(2025, 3, 25),
                    10,
                    "Compare URI versioning, header versioning and content negotiation strategies for evolving REST APIs without breaking clients.",
                    List.of("rest", "api", "spring-boot", "versioning"),
                    "/blog/api-versioning-spring",
                    "https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=800",
                    "API version branching diagram"
            ),
            new BlogCard(
                    "Spring AI: Building LLM-powered Applications",
                    LocalDate.of(2025, 4, 10),
                    20,
                    "Integrate large language models into your Spring Boot applications using Spring AI with OpenAI, Ollama and vector store support.",
                    List.of("spring", "ai", "llm", "spring-ai"),
                    "/blog/spring-ai-llm-applications",
                    "https://images.unsplash.com/photo-1677442135703-1787eea5ce01?w=800",
                    "Neural network representing AI"
            ),
            new BlogCard(
                    "Idempotency in REST APIs",
                    LocalDate.of(2025, 4, 28),
                    9,
                    "Design idempotent REST APIs to safely handle retries and network failures using idempotency keys and distributed locking.",
                    List.of("rest", "api", "architecture", "best-practices"),
                    "/blog/idempotency-rest-apis",
                    "https://images.unsplash.com/photo-1504711434969-e33886168f5c?w=800",
                    "Retry mechanism flow diagram"
            )
    );

    public Page<BlogCardResponse> getBlogCards(Pageable pageable, String tag, String title) {
        Stream<BlogCard> stream = MOCK_DATA.stream();

        // Filter by tag (case-insensitive)
        if (tag != null && !tag.isBlank()) {
            final String tagLower = tag.toLowerCase(Locale.ROOT);
            stream = stream.filter(card ->
                    card.tags().stream().anyMatch(t -> t.toLowerCase(Locale.ROOT).contains(tagLower))
            );
        }

        // Filter by title (case-insensitive contains)
        if (title != null && !title.isBlank()) {
            final String titleLower = title.toLowerCase(Locale.ROOT);
            stream = stream.filter(card ->
                    card.title().toLowerCase(Locale.ROOT).contains(titleLower)
            );
        }

        List<BlogCard> filtered = stream.toList();

        // Apply sorting
        List<BlogCard> sorted = applySorting(filtered, pageable.getSort());

        // Apply pagination
        int totalElements = sorted.size();
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int fromIndex = Math.min(pageNumber * pageSize, totalElements);
        int toIndex = Math.min(fromIndex + pageSize, totalElements);

        List<BlogCardResponse> pageContent = sorted
                .subList(fromIndex, toIndex)
                .stream()
                .map(BlogCardResponse::from)
                .toList();

        return new PageImpl<>(pageContent, pageable, totalElements);
    }

    private List<BlogCard> applySorting(List<BlogCard> cards, Sort sort) {
        if (sort.isUnsorted()) {
            return cards;
        }

        Comparator<BlogCard> comparator = null;

        for (Sort.Order order : sort) {
            Comparator<BlogCard> fieldComparator = switch (order.getProperty().toLowerCase(Locale.ROOT)) {
                case "title"    -> Comparator.comparing(BlogCard::title, String.CASE_INSENSITIVE_ORDER);
                case "date"     -> Comparator.comparing(BlogCard::date);
                case "duration" -> Comparator.comparingInt(BlogCard::duration);
                case "tag"      -> Comparator.comparing(c -> c.tags().isEmpty() ? "" : c.tags().getFirst(),
                                        String.CASE_INSENSITIVE_ORDER);
                default         -> Comparator.comparing(BlogCard::date);
            };

            if (order.isDescending()) {
                fieldComparator = fieldComparator.reversed();
            }

            comparator = (comparator == null) ? fieldComparator : comparator.thenComparing(fieldComparator);
        }

        return (comparator != null) ? cards.stream().sorted(comparator).toList() : cards;
    }
}

# Middlewares Setup Guide (Spring Boot API)

This guide covers common middleware-style setup for Spring Boot APIs:

- CORS
- Rate limiting
- Request/response logging
- Global exception handling
- Security headers
- Request tracing/correlation ID

Spring Boot does not use the term "middleware" exactly like Node.js, but the equivalent concepts are implemented via:

- `Filter` / `OncePerRequestFilter`
- `HandlerInterceptor`
- `@ControllerAdvice`
- Spring Security filter chain

## 1) CORS (Cross-Origin Resource Sharing)

Use a centralized CORS config so frontend origins are explicit and controlled.

Example:

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
}
```

Recommended:

- Restrict origins per environment (dev/staging/prod)
- Avoid `*` in production when credentials are enabled

## 2) Rate Limiting

Rate limiting is usually added through:

- API gateway / reverse proxy (recommended first layer)
- Application-level limiter (per IP, API key, or user ID)

Common Java options:

- Bucket4j
- Resilience4j rate limiter

Typical policy example:

- Public endpoints: 60 req/min per IP
- Authenticated endpoints: 300 req/min per user
- Burst allowance with short refill windows

## 3) Request Logging

Use a request logging filter to capture method, path, status, duration, and correlation ID.

Recommended log fields:

- `traceId` / `correlationId`
- HTTP method
- Request path
- Status code
- Latency (ms)
- Client IP / user agent (if needed)

Tips:

- Do not log sensitive data (tokens, passwords, PII)
- Keep body logging disabled by default in production

## 4) Global Exception Handling

Use `@RestControllerAdvice` to standardize error responses.

Example response shape:

```json
{
  "timestamp": "2026-03-30T20:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/items"
}
```

Handle common exceptions:

- Validation exceptions
- Entity not found
- Illegal argument / bad input
- Fallback `Exception` handler for unexpected errors

## 5) Security Headers

Add secure defaults (usually via Spring Security or reverse proxy):

- `X-Content-Type-Options: nosniff`
- `X-Frame-Options: DENY`
- `Referrer-Policy: no-referrer`
- `Content-Security-Policy` (if serving browser-facing content)
- `Strict-Transport-Security` (HTTPS only)

## 6) Correlation ID / Trace ID

Generate or propagate a request ID for each request and include it in logs and response headers.

Pattern:

- Read incoming header (e.g., `X-Request-Id`) if present
- Otherwise generate UUID
- Put in MDC for logging
- Return it in response header

This is critical for debugging across services.

## 7) Interceptor vs Filter

Use a **Filter** when you need:

- Lowest-level request/response handling
- Header mutation
- CORS/security/logging at servlet layer

Use an **Interceptor** when you need:

- Handler-level logic around controller execution
- Route-aware pre/post handling

Use `@ControllerAdvice` for centralized error mapping.

## 8) Suggested Starter Baseline

For this template, a practical baseline is:

1. CORS config for `/api/**`
2. Request logging filter with correlation ID
3. Global exception handler with consistent error JSON
4. Rate limiting (gateway first, optional app-level)
5. Security headers via Spring Security

## 9) Production Checklist

- CORS origins locked down by environment
- Rate limits defined and monitored
- Structured JSON logging enabled
- Sensitive headers/body fields redacted
- Standard error response contract documented
- Correlation ID present in all logs
- HTTPS + HSTS enabled

## 10) Next Step

If you want, we can add actual code files next:

- `config/CorsConfig.java`
- `filters/RequestLoggingFilter.java`
- `advice/GlobalExceptionHandler.java`
- optional rate-limiter integration and test coverage

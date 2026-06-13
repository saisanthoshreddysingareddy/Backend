package com.example.shortenurl.repositories;

import com.example.shortenurl.models.ShortenedUrl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ShortenedUrlRepositoryImpl implements ShortenedUrlRepository {

    private final Map<String, ShortenedUrl> shortenedUrls = new HashMap<>();
    private int nextId = 1;

    public Optional<ShortenedUrl> findByShortUrl(String shortUrl) {
        return Optional.ofNullable(shortenedUrls.get(shortUrl));
    }

    public ShortenedUrl save(ShortenedUrl shortenedUrl) {
        if (shortenedUrl.getId() == 0) {
            shortenedUrl.setId(nextId++);
        }

        shortenedUrls.put(shortenedUrl.getShortUrl(), shortenedUrl);
        return shortenedUrl;
    }

    public void deleteAll() {
        shortenedUrls.clear();
    }
}

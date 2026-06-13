package com.example.shortenurl.repositories;

import com.example.shortenurl.models.ShortenedUrl;

import java.util.Optional;

public interface ShortenedUrlRepository  {
    public void deleteAll();
    public Optional<ShortenedUrl> findByShortUrl(String shortUrl);
    public ShortenedUrl save(ShortenedUrl shortenedUrl);
}

package com.example.shortenurl.services;

import com.example.shortenurl.exceptions.UrlNotFoundException;
import com.example.shortenurl.exceptions.UserNotFoundException;
import com.example.shortenurl.models.*;
import com.example.shortenurl.repositories.*;
import com.example.shortenurl.utils.ShortUrlGenerator;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService {

    private final UserRepository userRepository;
    private final ShortenedUrlRepository shortenedUrlRepository;
    private final UrlAccessLogRepository urlAccessLogRepository;

    public UrlServiceImpl(UserRepository userRepository,
                          ShortenedUrlRepository shortenedUrlRepository,
                          UrlAccessLogRepository urlAccessLogRepository) {

        this.userRepository = userRepository;
        this.shortenedUrlRepository = shortenedUrlRepository;
        this.urlAccessLogRepository = urlAccessLogRepository;
    }

    @Override
    public ShortenedUrl shortenUrl(String originalUrl, int userId)
            throws UserNotFoundException {

        User user = userRepository
                .findUserById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        String shortUrl;

        do {
            shortUrl = ShortUrlGenerator.generateShortUrl();
        } while (shortenedUrlRepository.findByShortUrl(shortUrl).isPresent());

        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setOriginalUrl(originalUrl);
        shortenedUrl.setShortUrl(shortUrl);
        shortenedUrl.setUser(user);

        long now = System.currentTimeMillis();

        switch (user.getUserPlan()) {
            case FREE:
                shortenedUrl.setExpiresAt(now + 1L * 24 * 60 * 60 * 1000);
                break;

            case TEAM:
                shortenedUrl.setExpiresAt(now + 7L * 24 * 60 * 60 * 1000);
                break;

            case BUSINESS:
                shortenedUrl.setExpiresAt(now + 30L * 24 * 60 * 60 * 1000);
                break;

            case ENTERPRISE:
                shortenedUrl.setExpiresAt(now + 365L * 24 * 60 * 60 * 1000);
                break;
        }

        return shortenedUrlRepository.save(shortenedUrl);
    }

    @Override
    public String resolveShortenedUrl(String shortUrl)
            throws UrlNotFoundException {

        ShortenedUrl shortenedUrl = shortenedUrlRepository
                .findByShortUrl(shortUrl)
                .orElseThrow(() ->
                        new UrlNotFoundException("URL not found"));

        if (shortenedUrl.getExpiresAt() < System.currentTimeMillis()) {
            throw new UrlNotFoundException("URL has expired");
        }

        UrlAccessLog accessLog = new UrlAccessLog();
        accessLog.setShortenedUrl(shortenedUrl);
        accessLog.setAccessedAt(System.currentTimeMillis());

        urlAccessLogRepository.save(accessLog);

        return shortenedUrl.getOriginalUrl();
    }
}
package com.example.shortenurl.repositories;

import com.example.shortenurl.models.UrlAccessLog;

import java.util.List;

public interface UrlAccessLogRepository  {
    UrlAccessLog save(UrlAccessLog log);

    List<UrlAccessLog> findAllByUrlId(int urlId);

    long countByUrlId(int urlId);
    
    List<UrlAccessLog> findAll();

    void deleteAll();

}

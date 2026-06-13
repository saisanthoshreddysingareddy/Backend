package com.example.shortenurl.repositories;

import com.example.shortenurl.models.UrlAccessLog;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UrlAccessLogRepositoryImpl implements UrlAccessLogRepository {

    private final Map<Integer, UrlAccessLog> urlAccessLogs = new HashMap<>();
    private int nextId = 1;

    @Override
    public UrlAccessLog save(UrlAccessLog log) {

        if (log.getId() == 0) {
            log.setId(nextId++);
        }

        urlAccessLogs.put(log.getId(), log);
        return log;
    }

    @Override
    public List<UrlAccessLog> findAllByUrlId(int urlId) {

        List<UrlAccessLog> logs = new ArrayList<>();

        for (UrlAccessLog log : urlAccessLogs.values()) {
            if (log.getShortenedUrl().getId() == urlId) {
                logs.add(log);
            }
        }

        return logs;
    }

    @Override
    public List<UrlAccessLog> findAll() {
        return new ArrayList<>(urlAccessLogs.values());
    }
    
    @Override
    public long countByUrlId(int urlId) {

        long count = 0;

        for (UrlAccessLog log : urlAccessLogs.values()) {
            if (log.getShortenedUrl().getId() == urlId) {
                count++;
            }
        }

        return count;
    }

    @Override
    public void deleteAll() {
        urlAccessLogs.clear();
    }
}
package com.youtube.jwt.service;

import com.youtube.jwt.dao.UrlCountDao;
import com.youtube.jwt.entity.UrlCount;
import com.youtube.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class UrlCountService {
    private final UrlCountDao urlCountRepository;

    @Autowired
    public UrlCountService(UrlCountDao urlCountRepository) {
        this.urlCountRepository = urlCountRepository;
    }

    public void incrementUrlCount(String url) {
        UrlCount urlCount = urlCountRepository.findByUrl(url);

        if (urlCount == null) {
            urlCount = new UrlCount(url, 1);
        } else {
            urlCount.setCount(urlCount.getCount() + 1);
        }

        urlCountRepository.save(urlCount);
    }
    public List<UrlCount> getAllUrls() {
        return urlCountRepository.findAll();
    }
}

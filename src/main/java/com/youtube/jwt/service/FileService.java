package com.youtube.jwt.service;

import com.youtube.jwt.dao.File;
import com.youtube.jwt.entity.ProvidersFile;
import com.youtube.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private File file;

    @GetMapping("/getAllProviderList")
    public List<ProvidersFile> getAllProviders() {
        return file.findAll();
    }

}

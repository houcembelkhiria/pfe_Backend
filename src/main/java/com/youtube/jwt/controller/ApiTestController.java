package com.youtube.jwt.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiTestController {

    private MongoCollection<Document> collection;

    private final MongoTemplate mongoTemplate;

    public ApiTestController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.collection = mongoTemplate.getCollection("apiTestResults");
    }

    @PostMapping("/storeTestResults")
    public ResponseEntity<String> storeResult(@RequestBody String result) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(result);

            if (rootNode.isArray()) {
                ArrayNode arrayNode = (ArrayNode) rootNode;

                for (JsonNode jsonNode : arrayNode) {
                    Document document = Document.parse(jsonNode.toString());
                    collection.insertOne(document);
                }

                return ResponseEntity.ok("Results stored in database");
            } else {
                Document document = Document.parse(result);
                collection.insertOne(document);
                return ResponseEntity.ok("Result stored in database");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error storing result in database: " + e.getMessage());
        }
    }


    @GetMapping("/testResults")
    public List<Document> getApiTestResults() {
        return collection.find().into(new ArrayList<>());
    }

}


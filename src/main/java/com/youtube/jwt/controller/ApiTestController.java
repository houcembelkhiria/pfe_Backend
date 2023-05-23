package com.youtube.jwt.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mongodb.client.*;
import com.youtube.jwt.entity.ApiTest;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ApiTestController {

    private MongoCollection<Document> collection;

    private final MongoTemplate mongoTemplate;

    public ApiTestController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.collection = mongoTemplate.getCollection("apiTestResults");
    }

   /* @PostMapping("/storeTestResults")
    public ResponseEntity<String> storeResult(@RequestBody String result, HttpServletRequest request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(result);

            Document document = new Document();

            // Store data in its own document
            JsonNode dataNode = rootNode.get("data");
            if (dataNode != null && dataNode.isArray() && dataNode.size() > 0) {
                ArrayNode arrayNode = (ArrayNode) dataNode;
                List<Document> dataList = new ArrayList<>();
                for (JsonNode jsonNode : arrayNode) {
                    Document dataDoc = Document.parse(jsonNode.toString());
                    dataList.add(dataDoc);
                }
                document.put("data", dataList);
            }

            // Store headers in its own document
            Enumeration<String> headerNames = request.getHeaderNames();
            Document headersDoc = new Document();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headersDoc.put(headerName, request.getHeader(headerName));
            }
            document.put("headers", headersDoc);

            // Store URL in its own document
            String requestURL = request.getRequestURL().toString();
            document.put("url", requestURL);

            collection.insertOne(document);

            return ResponseEntity.ok("Result stored in database");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error storing result in database: " + e.getMessage());
        }
    }
*/
   /*@PostMapping("/storeTestResults")
   public ResponseEntity<String> storeResult(@RequestBody String result, @RequestHeader HttpHeaders headers, HttpServletRequest request) {
       try {
           ObjectMapper objectMapper = new ObjectMapper();
           JsonNode rootNode = objectMapper.readTree(result);

           if (rootNode.isArray()) {
               ArrayNode arrayNode = (ArrayNode) rootNode;

               for (JsonNode jsonNode : arrayNode) {
                   Document document = new Document();
                   document.append("data", jsonNode.toString());
                   document.append("headers", headers.toString());
                   document.append("url", request.getRequestURI());
                   collection.insertOne(document);
               }

               return ResponseEntity.ok("Results stored in database");
           } else {
               Document document = new Document();
               document.append("data", result);
               document.append("headers", headers.toString());
               document.append("url", request.getRequestURI());
               collection.insertOne(document);
               return ResponseEntity.ok("Result stored in database");
           }
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Error storing result in database: " + e.getMessage());
       }
   }
*/
   @PostMapping("/storeTestResults")
   public ResponseEntity<String> storeResult(@RequestBody String result, @RequestHeader HttpHeaders headers, HttpServletRequest request) {
       try {
           ObjectMapper objectMapper = new ObjectMapper();
           JsonNode rootNode = objectMapper.readTree(result);

           String url = rootNode.path("config").path("url").asText();
           if (rootNode.isArray()) {
               ArrayNode arrayNode = (ArrayNode) rootNode;

               for (JsonNode jsonNode : arrayNode) {
                   Document document = Document.parse(jsonNode.toString());
                   document.append("headers", headers.toString());
                   document.append("api",rootNode.path("config").path("url").asText());
                   collection.insertOne(document);
               }

               return ResponseEntity.ok("Results stored in database");
           } else {
               Document document = Document.parse(result);
               document.append("headers", headers.toString());
               document.append("api", rootNode.path("config").path("url").asText());
               collection.insertOne(document);
               return ResponseEntity.ok("Result stored in database");
           }
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Error storing result in database: " + e.getMessage());
       }
   }
    private MongoCollection<Document> getMongoCollection() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("jwtRoleBasedAuth");
        MongoCollection<Document> collection = database.getCollection("apiTestResults");
        return collection;
    }

 


    @GetMapping("/testResults")
    public List<Document> getApiTestResults() {
        return collection.find().into(new ArrayList<>());
    }

}


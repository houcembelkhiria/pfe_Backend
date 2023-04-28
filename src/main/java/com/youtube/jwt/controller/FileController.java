package com.youtube.jwt.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.youtube.jwt.entity.ProvidersFile;
import com.youtube.jwt.service.FileService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FileController {

    @Autowired
    public MongoTemplate mongoTemplate;



    @Autowired
    private FileService fileService;

    @PostMapping("/uploadProviderList")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Read the Excel file
            Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(file.getBytes()));
            Sheet sheet = workbook.getSheetAt(0);

            // Convert the Excel data to a list of maps
            List<DBObject> data = new ArrayList<>();
            String lastProvider = "";
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Skip the header row
                    continue;
                }
                DBObject doc = new BasicDBObject();
                for (Cell cell : row) {
                    String value = cell.getStringCellValue();
                    String title = sheet.getRow(0).getCell(cell.getColumnIndex()).getStringCellValue();
                    if (row.getRowNum() == 1 && title.equals("Provider") && !value.isEmpty()) {
                        // Set the initial value of lastProvider to the first non-empty cell of the "Provider" column in the second row
                        lastProvider = value;
                    }
                    if (value.isEmpty()) {
                        // Use the value of the last non-empty cell in the "Provider" column
                        if (title.equals("Provider")) {
                            doc.put("provider", lastProvider);
                        } else if (title.equals("Name")) {
                            doc.put("name", lastProvider);
                        } else if (title.equals("PROD")) {
                            doc.put("prod", lastProvider);
                        } else if (title.equals("URL")) {
                            doc.put("url", lastProvider);
                        }
                    } else {
                        if (title.equals("Provider")) {
                            doc.put("provider", value);
                            lastProvider = value;
                        } else if (title.equals("Name")) {
                            doc.put("name", value);
                        } else if (title.equals("PROD")) {
                            doc.put("prod", value);
                        } else if (title.equals("URL")) {
                            doc.put("url", value);
                        }
                    }
                }
                data.add(doc);
            }

            // Save the data to MongoDB
            mongoTemplate.insert(data, "providerList");

            return ResponseEntity.ok("File uploaded and data saved to MongoDB.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }


    @DeleteMapping("/deleteProviderList/{id}")
    public ResponseEntity<String> deleteProvider(@PathVariable String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        try {
            mongoTemplate.remove(query, "providerList");
            return ResponseEntity.ok("Provider deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete provider.");
        }
    }

    @GetMapping("/getAllProviderList")
    public List<ProvidersFile> getAllProviders() {
        return fileService.getAllProviders();
    }

    @PostMapping("/addNewProviderList")
    public ResponseEntity<String> addProvider(@RequestBody Map<String, String> provider) {
        try {
            mongoTemplate.insert(provider, "providerList");
            return ResponseEntity.ok("Provider added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add provider.");
        }
    }

    @PutMapping("/updateProviderList/{id}")
    public ResponseEntity<String> updateProvider(@PathVariable String id, @RequestBody Map<String, String> provider) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        for (Map.Entry<String, String> entry : provider.entrySet()) {
            update.set(entry.getKey(), entry.getValue());
        }
        try {
            mongoTemplate.updateFirst(query, update, "providerList");
            return ResponseEntity.ok("Provider updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update provider.");
        }
    }


}
package com.youtube.jwt.controller;
import java.nio.charset.StandardCharsets;
import com.youtube.jwt.dao.DocumentDao;
import com.youtube.jwt.entity.Documents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@RestController
public class DocumentController {
    @Autowired
    private DocumentDao documentDao;

    @PostMapping("/uploadCrm")
    public String uploadDocument(@RequestParam("file") MultipartFile file) throws IOException {
        // Read file content as bytes
        byte[] fileContent = file.getBytes();

        // Convert bytes to Base64 string
        String base64Content = Base64.getEncoder().encodeToString(fileContent);

        Documents document = new Documents();
        document.setFileName(file.getOriginalFilename());
        document.setBase64Content(base64Content);
        documentDao.save(document);

        return "File uploaded successfully.";
    }


    @GetMapping("/downloadCrm/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable("id") String id) {
        // Retrieve the document from MongoDB using the given ID
        Optional<Documents> documentOptional = documentDao.findById(id);
        if (documentOptional.isPresent()) {
            Documents document = documentOptional.get();

            // Decode the Base64 content to bytes with specified character encoding
            byte[] fileContent = Base64.getDecoder().decode(document.getBase64Content().getBytes(StandardCharsets.UTF_8));

            // Prepare the response with the file content and headers
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"");
            headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileContent.length));

            return ResponseEntity.ok()
                    .body(fileContent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}

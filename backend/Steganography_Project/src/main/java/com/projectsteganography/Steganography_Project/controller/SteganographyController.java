package com.projectsteganography.Steganography_Project.controller;

import com.projectsteganography.Steganography_Project.service.SteganographyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/steganography")
@CrossOrigin(origins = "http://localhost:4200")
public class SteganographyController {


    @Autowired
    private SteganographyService service;


    @PostMapping(value = "/encrypt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> encrypt(
            @RequestParam("text") String text,
            @RequestParam("image") MultipartFile image) {

        Map<String, Object> response = new HashMap<>();

        if (image == null || image.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Image is required");
            return ResponseEntity.badRequest().body(response);
        }

        if (text == null || text.trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "Text is required");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            File input = File.createTempFile("input-", ".png");
            image.transferTo(input);

            File output = File.createTempFile("encrypted-", ".png");
            service.encrypt(text, input, output);

            service.saveToDB(text, image.getOriginalFilename());

            response.put("status", "success");
            response.put("message", "Encrypted successfully & saved to DB");
            response.put("imageName", image.getOriginalFilename());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }




    @PostMapping(value = "/decrypt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> decrypt(
            @RequestParam("image") MultipartFile image) {

        try {
            if (image == null || image.isEmpty()) {
                return ResponseEntity.badRequest().body("Image is required");
            }

            File input = File.createTempFile("encrypted-", ".png");
            image.transferTo(input);

            String secret = service.decrypt(input);
            return ResponseEntity.ok(secret);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Decryption failed: " + e.getMessage());
        }
    }


    @PostMapping(value = "/upload-test", consumes = "multipart/form-data")
    public String uploadTest(@RequestParam ("image") MultipartFile image,
                             @RequestParam("secretText") String secretText) {
        if (image.isEmpty()) {
            return "Image is empty";
        }
        return "Uploaded:"+ image.getOriginalFilename()
                + " | Secret: " + secretText;
    }

    @PostMapping(value = "/ping", consumes = "multipart/form-data")
    public String ping(@RequestParam("image") MultipartFile image) {
        if (image == null) return "No file received!";
        return "File received: " + image.getOriginalFilename();
    }

    @GetMapping("/test")
    public String test() {
        return "API working";
    }
}

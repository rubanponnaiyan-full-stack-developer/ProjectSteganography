package com.projectsteganography.Steganography_Project.controller;

import com.projectsteganography.Steganography_Project.service.SteganographyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/steganography")
@CrossOrigin(origins = "http://localhost:4200")
public class SteganographyController {

    @Autowired
    private SteganographyService service;

    // ================= ENCRYPT (JSON RESPONSE) =================
    @PostMapping(value = "/encrypt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
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
            service.encryptAndReturnFile(text, input, output);

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

    // ================= 🔽 ENCRYPT + DOWNLOAD =================
    @PostMapping(
            value = "/encrypt-download",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<byte[]> encryptAndDownload(
            @RequestParam("text") String text,
            @RequestParam("image") MultipartFile image) {

        try {
            File input = File.createTempFile("input-", ".png");
            image.transferTo(input);

            File output = File.createTempFile("encrypted-", ".png");
            service.encryptAndReturnFile(text, input, output);

            byte[] fileBytes = Files.readAllBytes(output.toPath());

            return ResponseEntity.ok()
                    .header("Content-Disposition",
                            "attachment; filename=encrypted-image.png")
                    .contentType(MediaType.IMAGE_PNG)
                    .body(fileBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // ================= DECRYPT =================
    @PostMapping(value = "/decrypt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> decrypt(
            @RequestParam("image") MultipartFile image) {

        try {
            if (image == null || image.isEmpty()) {
                return ResponseEntity.badRequest().body("Image is required");
            }

            BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
            if (bufferedImage == null) {
                return ResponseEntity.badRequest().body("invalid this file");
            }

            File input = File.createTempFile("encrypted-", ".png");
            image.transferTo(input);

            String secret;
            try {
                secret = service.decrypt(input);
            } finally {
                input.delete(); // cleanup
            }
            return ResponseEntity.ok(secret);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Decryption failed: " + e.getMessage());
        }
    }

    // ================= TEST APIs =================
    @PostMapping(value = "/upload-test", consumes = "multipart/form-data")
    public String uploadTest(@RequestParam("image") MultipartFile image,
                             @RequestParam("secretText") String secretText) {
        if (image.isEmpty()) return "Image is empty";
        return "Uploaded: " + image.getOriginalFilename()
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

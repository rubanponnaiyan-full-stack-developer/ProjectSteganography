package com.projectsteganography.Steganography_Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

@SpringBootApplication
public class SteganographyProjectApplication {

	public static void main(String[] args) {

        SpringApplication.run(SteganographyProjectApplication.class, args);
        System.out.println("Application started..");

    }

}

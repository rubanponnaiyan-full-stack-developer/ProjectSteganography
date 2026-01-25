package com.projectsteganography.Steganography_Project.service;

import com.projectsteganography.Steganography_Project.entity.SteganographyModel;
import com.projectsteganography.Steganography_Project.repository.SteganographyRepository;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@Service
public class SteganographyService {

    private final SteganographyRepository repository;

    public SteganographyService(SteganographyRepository repository) {
        this.repository = repository;
    }


    public File encryptAndReturnFile(String text, File input, File output) throws Exception {

        BufferedImage image = ImageIO.read(input);

        byte[] bytes = text.getBytes();
        int index = 0;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (index < bytes.length) {
                    int pixel = image.getRGB(x, y);
                    int newPixel = (pixel & 0xFFFFFF00) | (bytes[index] & 0xFF);
                    image.setRGB(x, y, newPixel);
                    index++;
                }
            }
        }
        File out = File.createTempFile("encrypted-",".png");
        ImageIO.write(image, "png", output);

        return output;
    }

    public String decrypt(File imageFile) throws Exception {

        BufferedImage image = ImageIO.read(imageFile);
        StringBuilder text = new StringBuilder();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);
                char c = (char) (pixel & 0xFF);
                if (c == 0) break;
                text.append(c);
            }
        }
        return text.toString();
    }

    public void saveToDB(String text, String imageName) {
        SteganographyModel model = new SteganographyModel();
        model.setSecretText(text);
        model.setImageName(imageName);
        repository.save(model);
    }

    public void encrypt(String text, File input, File output) {
    }
}

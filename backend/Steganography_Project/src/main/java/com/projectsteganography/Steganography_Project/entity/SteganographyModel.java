package com.projectsteganography.Steganography_Project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "steganography")
public class SteganographyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String secretText;
    private String imageName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSecretText() { return secretText; }
    public void setSecretText(String secretText) { this.secretText = secretText; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

}

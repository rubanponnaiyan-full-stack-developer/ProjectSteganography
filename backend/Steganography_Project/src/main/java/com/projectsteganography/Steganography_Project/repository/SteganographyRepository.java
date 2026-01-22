package com.projectsteganography.Steganography_Project.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projectsteganography.Steganography_Project.entity.SteganographyModel;

public interface SteganographyRepository  extends JpaRepository<SteganographyModel, Long> {

}
package com.alexduzi.shoppingcart.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.alexduzi.shoppingcart.dto.ImageDto;
import com.alexduzi.shoppingcart.model.Image;

public interface IImageService {
	Image getImageById(Long id);

	void deleteImageById(Long id);

	List<ImageDto> saveImages(List<MultipartFile> files, Long productId);

	void updateImage(MultipartFile file, Long imageId);
}

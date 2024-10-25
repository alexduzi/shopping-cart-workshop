package com.alexduzi.shoppingcart.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alexduzi.shoppingcart.dto.ImageDto;
import com.alexduzi.shoppingcart.exceptions.ResourceNotFoundException;
import com.alexduzi.shoppingcart.model.Image;
import com.alexduzi.shoppingcart.model.Product;
import com.alexduzi.shoppingcart.repository.ImageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageService implements IImageService {

	private final ImageRepository imageRepository;

	private final IProductService productService;

	@Override
	public Image getImageById(Long id) {
		return imageRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
	}

	@Override
	public void deleteImageById(Long id) {
		imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
			throw new ResourceNotFoundException("No image found with id: " + id);
		});

	}

	@Override
	public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
		Product product = productService.getProductById(productId);

		List<ImageDto> savedImageDto = new ArrayList<>();

		for (MultipartFile file : files) {
			try {
				Image image = new Image();
				image.setFileName(file.getOriginalFilename());
				image.setFileType(file.getContentType());
				image.setImage(new SerialBlob(file.getBytes()));
				image.setProduct(product);

				String buildDownloadUrl = "/api/v1/images/image/download/";

				String downloadUrl = buildDownloadUrl + image.getId();

				image.setDownloadUrl(downloadUrl);

				Image savedImg = imageRepository.save(image);

				image.setDownloadUrl(buildDownloadUrl + savedImg.getId());

				imageRepository.save(savedImg);

				ImageDto imageDto = new ImageDto();
				imageDto.setId(savedImg.getId());
				imageDto.setFileName(savedImg.getFileName());
				imageDto.setDownloadUrl(savedImg.getDownloadUrl());

				savedImageDto.add(imageDto);

			} catch (IOException | SQLException ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return savedImageDto;
	}

	@Override
	public void updateImage(MultipartFile file, Long imageId) {
		Image image = getImageById(imageId);
		try {
			image.setFileName(file.getOriginalFilename());
			image.setFileType(file.getContentType());
			image.setImage(new SerialBlob(file.getBytes()));
			imageRepository.save(image);
		} catch (IOException | SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

}

package com.example.employeemanagement.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.employeemanagement.service.S3Service;

@RestController
@RequestMapping("/api/employees")
public class EmployeePhotoController {

	private final S3Service s3Service;

	public EmployeePhotoController(S3Service s3Service) {
		this.s3Service = s3Service;
	}

	@PostMapping(value = "/upload-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Map<String, String> uploadPhoto(@RequestParam("file") MultipartFile file) {

		String imageUrl = s3Service.uploadFile(file);
		System.out.println(imageUrl.toString());

		return Map.of("message", "Employee photo uploaded successfully", "imageUrl", imageUrl);
	}
}
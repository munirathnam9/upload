package com.sym.upload.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sym.upload.service.FileUploadService;

@RestController
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	FileUploadService fileUploadService;

	@PostMapping("/upload")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			// STEP 1 : Get the file from pre-defined path
			fileUploadService.handleFileUpload(file);
		} catch (Exception e) {
			logger.error("Error while loading File : " + e.getMessage());
			return new ResponseEntity<>("Error while loading File : " + fileName, HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<>("File Uploaded Successfully : " + fileName, HttpStatus.OK);
	}

}

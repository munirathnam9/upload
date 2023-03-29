package com.sym.upload.service;

import org.springframework.web.multipart.MultipartFile;

import com.sym.upload.common.FileUploadException;

public interface FileUploadService {

	public String handleFileUpload(MultipartFile file) throws FileUploadException;
}

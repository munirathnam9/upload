package com.sym.upload.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sym.upload.common.FileUploadException;
import com.sym.upload.repository.FileUploadRepository;
import com.sym.upload.service.FileUploadService;
import com.sym.upload.util.BeanUtilsNew;
import com.sym.upload.util.Employee;
import com.sym.upload.util.FileUploadContstants;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	FileUploadRepository fileUploadRepository;

	 @Value("${local.filepath}")
	    private String filePath;
	
	public String handleFileUpload(MultipartFile file)throws FileUploadException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		// STEP 1 : Get the file from pre-defined path
		try {
			File newFile = new File(filePath + fileName);
			file.transferTo(newFile);

			System.out.println("newFile exists -->" + newFile.exists());

			if (newFile.exists()) {

				List<String> readAllLines = Files.readAllLines(Paths.get(filePath + fileName));

				System.out.println(readAllLines);

				readAllLines.parallelStream().filter(line -> !line.contains("emp")).forEach(line -> {

					// to push data to topic

					CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(FileUploadContstants.TOPIC_NAME, line);

					future.whenComplete((result, ex) -> {

						if (ex == null) {
							System.out.println("Sent message=[" + line + "] with offset=["
									+ result.getRecordMetadata().offset() + "]");
						} else {
							System.out.println("Unable to send message=[" + line + "] due to : " + ex.getMessage());
						}

					});

				});

				logger.info("File uploaded successfully: " + fileName);
				return fileName;
			}

		} catch (IOException e) {
			logger.info(" Error while loading file : " + e.getMessage());
			throw new FileUploadException("Error while loading file :"+ e.getMessage());
		}
		return fileName;
	}

	@KafkaListener(topics = FileUploadContstants.TOPIC_NAME, groupId = FileUploadContstants.GROUP_ID)
	public void listenGroupFoo(String message) {
		logger.info("Received Message in group  " + message);
		// to be inserted to DB
		Employee employee = BeanUtilsNew.convertToBean(message);
		// employeeRepository.save(employee);
		fileUploadRepository.saveEmployee(employee);

	}

}

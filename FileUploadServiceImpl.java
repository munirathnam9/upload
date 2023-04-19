package com.sym.upload.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sym.upload.common.FileUploadException;
import com.sym.upload.repository.FileUploadRepository;
import com.sym.upload.service.FileUploadService;
import com.sym.upload.util.BeanUtilsNew;
import com.sym.upload.util.Employee;
import com.sym.upload.util.FileUploadContstants;

import jakarta.transaction.Transactional;

@Service
//@Transactional
public class FileUploadServiceImpl implements FileUploadService {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	FileUploadRepository fileUploadRepository;

	 @Value("${local.filepath}")
	 private String filePath;
	 
	 private ExecutorService pushService = null;
	 
	 private ExecutorService listenService = null;
	
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
				
				int noOfThreads = readAllLines.size()/1000;
				
				
				pushService = Executors.newFixedThreadPool(10);
				
				listenService = Executors.newFixedThreadPool(10);
				
				 StopWatch watch = new StopWatch();
			        watch.start();

				readAllLines.parallelStream().filter(line -> !line.contains("emp")).forEach(line -> {

					// to push data to topic

					pushService.execute(() -> {
						pushMessageToTopic(line);
					  }
					);
					
				});
				
				watch.stop();
		        System.out.println("Total execution time to push messages to topic in millis: "
		                + watch.getTotalTimeMillis());

				

				logger.info(readAllLines.size() + " file records processed successfully. Total execution time to push messages to topic in seconds: " + (watch.getTotalTimeMillis()/1000));
				return fileName;
			}

		} catch (IOException e) {
			logger.info(" Error while loading file : " + e.getMessage());
			throw new FileUploadException("Error while loading file :"+ e.getMessage());
		}
		return fileName;
	}

	public void pushMessageToTopic(String line) {
		CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(FileUploadContstants.TOPIC_NAME, line);

		future.whenComplete((result, ex) -> {

			if (ex == null) {
				//System.out.println("Sent message=[" + line + "] with offset=["
						//+ result.getRecordMetadata().offset() + "]" +"Thread-" + Thread.currentThread().getName());
			} else {
				System.out.println("Unable to send message=[" + line + "] due to : " + ex.getMessage());
			}

		});
	}
	
	/*
	 * class PushThread implements Runnable{
	 * 
	 * private String line = null;
	 * 
	 * public PushThread(String line){ this.line = line; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * //this.pushMessageToTopic(null);
	 * 
	 * CompletableFuture<SendResult<String, String>> future =
	 * kafkaTemplate.send(FileUploadContstants.TOPIC_NAME, line);
	 * 
	 * future.whenComplete((result, ex) -> {
	 * 
	 * if (ex == null) { System.out.println("Sent message=[" + line +
	 * "] with offset=[" + result.getRecordMetadata().offset() + "]"); } else {
	 * System.out.println("Unable to send message=[" + line + "] due to : " +
	 * ex.getMessage()); }
	 * 
	 * });
	 * 
	 * }
	 * 
	 * }
	 */

	@KafkaListener(topics = FileUploadContstants.TOPIC_NAME, groupId = FileUploadContstants.GROUP_ID)
	public void listenGroupFoo(String message) {
		//logger.info("Received Message in group  " + message);
		// to be inserted to DB
		 StopWatch watch = new StopWatch();
	        watch.start();
		
		 listenService.execute(() -> {
			Employee employee = BeanUtilsNew.convertToBean(message);
			// employeeRepository.save(employee);
			//fileUploadRepository.saveEmployee(employee);
			fileUploadRepository.saveEmployee3(employee);
			
		}
		 
	);
		 watch.stop();
	        System.out.println("Total execution time to push messages to topic in millis: "
	                + watch.getTotalTimeMillis());	
		/*
		 * try { submit.get(); } catch (InterruptedException e) { e.printStackTrace(); }
		 * catch (ExecutionException e) { e.printStackTrace(); }
		 */

		
		
		

	}

}

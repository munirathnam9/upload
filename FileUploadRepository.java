package com.sym.upload.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sym.upload.controller.FileUploadController;
import com.sym.upload.util.Employee;
import com.sym.upload.util.FileUploadContstants;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

@Repository
public class FileUploadRepository {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	private static EntityManagerFactory entityManagerFactory = null;
	private static EntityManager entityManager = null;

	static {
		entityManagerFactory = Persistence.createEntityManagerFactory(FileUploadContstants.JPA_DEMO_LOCAL);
		entityManager = entityManagerFactory.createEntityManager();
	}

	public synchronized void saveEmployee3(Employee employee) {
		entityManager.getTransaction().begin();
		entityManager.persist(employee);
		entityManager.getTransaction().commit();
	}

}

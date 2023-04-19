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
	
	private   static EntityManagerFactory entityManagerFactory = null;
	private  static  EntityManager entityManager = null;

	
	static {
		entityManagerFactory = Persistence.createEntityManagerFactory(FileUploadContstants.JPA_DEMO_LOCAL);
		entityManager = entityManagerFactory.createEntityManager();
	}
	 
	
	/*
	 * static { entityManagerFactory =
	 * Persistence.createEntityManagerFactory(FileUploadContstants.JPA_DEMO_LOCAL);
	 * entityManager = entityManagerFactory.createEntityManager(); }
	 */

	
	
	public synchronized void saveEmployee3(Employee employee) {
		
		System.out.println( employee + " Thread Name " + Thread.currentThread().getName() );
		
		//entityManagerFactory = Persistence.createEntityManagerFactory(FileUploadContstants.JPA_DEMO_LOCAL);
		//entityManager = entityManagerFactory.createEntityManager(); 
		
		entityManager.getTransaction().begin();
	    entityManager.persist(employee);
	    entityManager.getTransaction().commit();
	}
	
	
	/*
	 * public void saveEmployee2(Employee employee) {
	 * 
	 * System.out.println( employee + " Thread Name " +
	 * Thread.currentThread().getName() );
	 * 
	 * entityManagerFactory =
	 * Persistence.createEntityManagerFactory(FileUploadContstants.JPA_DEMO_LOCAL);
	 * entityManager = entityManagerFactory.createEntityManager();
	 * 
	 * if (!entityManager.contains(employee)) { if
	 * (!entityManager.getTransaction().isActive()) {
	 * System.out.println("contains 3 " +entityManager.contains(employee) ); if
	 * (!entityManager.contains(employee)) {
	 * 
	 * System.out.println("contains 33 "); entityManager.getTransaction().begin();
	 * entityManager.persist(employee); System.out.println("contains 333 "); try {
	 * entityManager.getTransaction().commit(); } catch (Exception e) { // TODO
	 * Auto-generated catch block //e.printStackTrace();
	 * 
	 * System.out.println("contains 33333 "+
	 * entityManager.getTransaction().isActive()); if
	 * (!entityManager.getTransaction().isActive()) {
	 * System.out.println("contains 3333 "); entityManager.getTransaction().begin();
	 * entityManager.persist(employee); try {
	 * entityManager.getTransaction().commit(); } catch (Exception e1) {
	 * 
	 * } } } } }
	 */
	
	public void saveEmployee(Employee employee) {
		try {
			//System.out.println("Received Message in group  " +employee );
			// begin transaction
			
			entityManagerFactory = Persistence.createEntityManagerFactory(FileUploadContstants.JPA_DEMO_LOCAL);
			entityManager = entityManagerFactory.createEntityManager();
			
			
			if(!entityManager.getTransaction().isActive()) {
			//entityManager.getTransaction().begin();
				 
				System.out.println("contains 1 " +entityManager.contains(employee) );
			if (!entityManager.contains(employee)) {
				//entityManager.getTransaction().begin();
				
				
				//Employee find = entityManager.find(Employee.class, employee.getEmp_id());
				if(null != null) {
					
					System.out.println("contains 2 " +entityManager.contains(employee) );
					//entityManager.getTransaction().begin();
					// flush em - save to DB
					//entityManager.flush();
					//entityManager.getTransaction().commit();
					
				}else {
					
					System.out.println("contains 3 " +entityManager.contains(employee) );
					
					synchronized(entityManager.getTransaction()) {
					// persist object - add to entity manager
					Employee find = entityManager.find(Employee.class, employee.getEmp_id());
					
					System.out.println("contains 4 find " +find );
					if(find == null) {
						//synchronized(entityManager.getTransaction()) {
							entityManager.getTransaction().begin();
							entityManager.persist(employee);
							entityManager.getTransaction().commit();
						//}
					}
					}
					// flush em - save to DB
					//entityManager.flush();
					//entityManager.getTransaction().commit();
					
				}
				
				System.out.println("committed "  );
			}
			// commit transaction at all
			//entityManager.getTransaction().commit();
			}  else {
				
				
				synchronized(entityManager.getTransaction()) {
					// persist object - add to entity manager
					Employee find = entityManager.find(Employee.class, employee.getEmp_id());
					
					System.out.println("contains 4 find " +find );
					if(find == null) {
						//synchronized(entityManager.getTransaction()) {
							//entityManager.getTransaction().begin();
							entityManager.persist(employee);
							entityManager.getTransaction().commit();
						//}
					}
					}
				
				try {
					System.out.println("else111   sjdbcjsdcnds "  );
				}catch(Exception e) {
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			
			//synchronized(entityManager.getTransaction()) {
				// persist object - add to entity manager
				Employee find = entityManager.find(Employee.class, employee.getEmp_id());
				
				System.out.println("contains 5 find " +find );
				
				System.out.println("entityManager.getTransaction().isActive() " +entityManager.getTransaction().isActive() );
				
				if(entityManager.getTransaction().isActive()) {
				if(find == null) {
					//synchronized(entityManager.getTransaction()) {
						//entityManager.getTransaction().begin();
						entityManager.persist(employee);
						entityManager.getTransaction().commit();
					//}
				}
				}else {
					if(find == null) {
						//synchronized(entityManager.getTransaction()) {
							entityManager.getTransaction().begin();
							entityManager.persist(employee);
							entityManager.getTransaction().commit();
						//}
					}
				}
			//}
			
			
			System.out.println("catch    2e3r3 "  );
			//e.printStackTrace();
		}

	}
}

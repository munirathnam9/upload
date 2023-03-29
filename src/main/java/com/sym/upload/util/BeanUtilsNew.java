/**
 * 
 */
package com.sym.upload.util;

import org.springframework.util.StringUtils;

/**
 * @author munir
 *
 */
public class BeanUtilsNew {
	
	
	public static Employee convertToBean(String line){
		
	String[] split = line.split(","); 
	if(split.length >0) {
		
		Employee e = new Employee();
		if(!StringUtils.isEmpty(split[0])){
			e.setEmp_id(Integer.valueOf(split[0]));
		}if(!StringUtils.isEmpty(split[1])){
			e.setEmp_name(split[1]);
		}if(!StringUtils.isEmpty(split[2])){
			e.setDesignation(split[2]);
		}if(!StringUtils.isEmpty(split[3])){
			e.setEmp_salary(Double.valueOf(split[3]));
		}if(!StringUtils.isEmpty(split[4])){
			e.setEmp_address(split[4]);
		}
		return e;
	}
	return null;
	
	
		
		
	}
	

}

package com.sym.upload.common;

public class FileUploadException extends Exception{

	private static final long serialVersionUID = 1L;

	public FileUploadException() {
        super();
    }
    
    public FileUploadException(String message) {
        super(message);
    }
    
    public FileUploadException(Throwable cause) {
        super(cause);
    }
    
    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}

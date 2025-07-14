package com.go.assignment.exception;
 
public class SbdNotFoundException extends RuntimeException {
    public SbdNotFoundException(String sbd) {
        super("Không tìm thấy điểm thi cho số báo danh " + sbd);
    }
} 
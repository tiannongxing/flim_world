package com.video_master.video_master_backend.exception;

import jakarta.mail.Message;

public class UnableFileUpload extends RuntimeException{
    public UnableFileUpload(String message){
        super(message);
    }
}

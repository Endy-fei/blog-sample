package com.lrm.service;



import com.lrm.model.Email;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;


public interface SendmailService {
    void thymeleafEmail(Email email) throws MessagingException, UnsupportedEncodingException;
}

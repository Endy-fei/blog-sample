package com.lrm.model;

import lombok.Data;

@Data
public class Email {

    private String[] toUser;
    private String subject;
    private EmailParam content;

}

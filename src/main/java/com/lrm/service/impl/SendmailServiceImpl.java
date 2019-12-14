package com.lrm.service.impl;

import com.lrm.model.Email;
import com.lrm.service.SendmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class SendmailServiceImpl implements SendmailService {


    //从配置文件中读属性
    @Value("${mail.fromMail.addr}")
    private String from;
    private String nickName = "喀喀的小站";


    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public SendmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }


    /**
     * html模板邮件
     *
     * @param email 给模板的参数
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    @Async
    public void thymeleafEmail(Email email) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(from, nickName);
        mimeMessageHelper.setTo(email.getToUser());
        mimeMessageHelper.setSubject(email.getSubject());
        Context ctx = new Context();
        ctx.setVariable("emailParam", email.getContent());
        String emailText = templateEngine.process("mail/mail", ctx);
        mimeMessageHelper.setText(emailText, true);
        javaMailSender.send(mimeMessage);
    }

}

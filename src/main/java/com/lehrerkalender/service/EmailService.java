package com.lehrerkalender.service;

public interface EmailService {

    public void sendSimpleMessage(String to, String subject, String text);
}

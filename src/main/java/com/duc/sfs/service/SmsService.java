package com.duc.sfs.service;

public interface SmsService {

    boolean sendSms(String[] phoneNumberSet, String[] templateParamSet);

    void statistics();
}

package com.duc.sfs.service;

import java.util.Set;

public interface SmsService {

    boolean sendSms(String[] phoneNumberSet, String[] templateParamSet);

    void statistics();
}

package com.geiclient.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by cfzhu on 2017/4/27.
 */
@ConfigurationProperties(locations = "classpath:config/request.properties", prefix = "web")
@Component
public class SendRequestConfig {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

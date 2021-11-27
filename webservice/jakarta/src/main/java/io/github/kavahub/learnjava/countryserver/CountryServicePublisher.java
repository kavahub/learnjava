package io.github.kavahub.learnjava.countryserver;

import jakarta.xml.ws.Endpoint;
import lombok.extern.slf4j.Slf4j;


/**
 * 
 * 服务发布，程序入口
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class CountryServicePublisher {
    
    public static void main(String[] args) {
        final String countryUrl = "http://localhost:8888/ws/country";
        Endpoint endpoint = Endpoint.create(new CountryServiceImpl());
        endpoint.publish(countryUrl);
        
        log.info("Country web service ready to consume requests!");
        log.info("Open the browser and enter the address: {}", countryUrl);
    }
}
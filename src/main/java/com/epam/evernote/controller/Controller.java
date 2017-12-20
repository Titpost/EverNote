package com.epam.evernote.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

public class Controller {

    protected final Logger LOG = LoggerFactory.getLogger(PersonController.class);

    protected final static HttpHeaders responseHeaders = new HttpHeaders();

    static {
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        responseHeaders.set("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        responseHeaders.set("Access-Control-Allow-Headers", "*");
        responseHeaders.set("Access-Control-Max-Age", "3600");
    }
}

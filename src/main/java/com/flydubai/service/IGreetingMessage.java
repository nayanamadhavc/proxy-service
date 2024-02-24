package com.flydubai.service;

import com.flydubai.HelloRequest;
import com.flydubai.hellosoap.HelloSoapResponse;

/**
 * This interface represents a contract for retrieving greeting messages.
 */
public interface IGreetingMessage {
    /**
     * Retrieves a greeting message.
     *
     * @param request The request containing the client's name.
     * @return The greeting message response.
     */
    HelloSoapResponse getMessage(HelloRequest request);
}

package com.flydubai.client;

import com.flydubai.hellosoap.HelloSoapPortType;
import com.flydubai.hellosoap.HelloSoap;
import com.flydubai.hellosoap.HelloSoapResponse;
import io.quarkiverse.cxf.annotation.CXFClient;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * This class represents a client for the Hello SOAP service.
 *
 * @author nayanamadhav
 */
@ApplicationScoped
public class HelloSoapClient {

    @CXFClient("helloSoap")
    HelloSoapPortType helloSoapPortTypeService;

    /**
     * Fetches the response from the Hello SOAP service.
     *
     * @param clientName The name of the client.
     * @return The response from the SOAP service.
     */
    public HelloSoapResponse fetchResponse(String clientName) {
        // Step 1: Prepare request
        HelloSoap helloSoapRequest = buildHelloSoapRequest(clientName);

        // Step 2: Invoke the remote soap service
       return helloSoapPortTypeService.hello(helloSoapRequest);
    }

    private HelloSoap buildHelloSoapRequest(String clientName) {
        HelloSoap helloSoapRequest = new HelloSoap();
        helloSoapRequest.setClientName(clientName);

        return  helloSoapRequest;
    }
}

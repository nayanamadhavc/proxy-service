package com.flydubai.service;

import com.flydubai.HelloRequest;
import com.flydubai.client.HelloSoapClient;
import com.flydubai.hellosoap.HelloSoapResponse;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.xml.ws.soap.SOAPFaultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * GreetingService is an implementation of IGreetingMessage.
 *
 * @author nayanamadhav
 */
@ApplicationScoped
public class GreetingService implements IGreetingMessage {
    private static final Logger logger = LoggerFactory.getLogger(GreetingService.class);

    @Inject
    HelloSoapClient helloSoapClient;

    /**
     * Retrieves a greeting message from the Hello SOAP service.
     *
     * @param request The request containing the client's name.
     * @return The greeting message response from the SOAP service.
     */
    @Override
    public HelloSoapResponse getMessage(HelloRequest request) {
        try{
            return helloSoapClient.fetchResponse(request.getClientName());
        } catch (SOAPFaultException e) {
            logger.error("Error: Message {}, Fault: {} ", e.getMessage(), e.getFault().getFaultCode());
            throw new StatusRuntimeException(Status.fromCode(Status.Code.FAILED_PRECONDITION).withDescription("Error Message: %s StatusCode: %s ".format(e.getMessage(), e.getFault().getFaultCode())));
        }
        catch (Exception e) {
            logger.error("Error: ", e);
            return null;
        }
    }
}

package com.flydubai.grpc;

import com.flydubai.HelloRequest;
import com.flydubai.HelloResponse;
import com.flydubai.MutinyProxyGrpc;
import io.grpc.StatusRuntimeException;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProxyGrpcServiceTest {

    @GrpcClient
    MutinyProxyGrpc.MutinyProxyStub proxyClient;

    @Test
    @Order(1)
    void testGreetingMessage_givenClientName_shouldReturnGreetingMessage() throws ExecutionException, InterruptedException, TimeoutException {
        // Step 1: Arrange
        CompletableFuture<HelloResponse> future = new CompletableFuture<>();
        String TEST_INPUT = "Test Client";
        final String EXPECTED_MESSAGE = String.format("Welcome %s", TEST_INPUT);


        // Step 2: Act
        proxyClient.getGreetingMessage(HelloRequest
                        .newBuilder()
                        .setClientName(TEST_INPUT)
                        .build())
                .subscribe().with(res -> future.complete(res));

        // Step 3: Assert
        String actualMessage = future.get().getGreetingMessage();
        Assertions.assertEquals(EXPECTED_MESSAGE, actualMessage);
    }

    @Test
    @Order(2)
    void testGreetingMessage_missingClientName_shouldReturnErrorMessage() throws ExecutionException, InterruptedException, TimeoutException {
        // Step 1: Arrange
        CompletableFuture<HelloResponse> future = new CompletableFuture<>();
        String TEST_INPUT = "";

        // Step 2: Act
        proxyClient.getGreetingMessage(HelloRequest.newBuilder().setClientName(TEST_INPUT).build())
                .subscribe().with(
                        res -> future.complete(res),
                        error -> future.completeExceptionally(error)
                );

        // Step 3: Assert
        Assertions.assertThrows(StatusRuntimeException.class, () -> {
            try {
                future.get(); // Wait for the future to complete
            } catch (ExecutionException e) {
                throw e.getCause(); // Throw the actual cause of the exception
            }
        });
    }
}
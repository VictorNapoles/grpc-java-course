package com.github.simplesteph.grpc.calculator.client;

import com.google.common.util.concurrent.ListenableFuture;
import com.proto.calculator.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class CalculatorClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

        CalculatorServiceGrpc.CalculatorServiceBlockingStub service = CalculatorServiceGrpc.newBlockingStub(channel);

        /*
        Calculator calculator = Calculator.newBuilder()
                .setNumber1(3)
                .setNumber2(10)
                .build();

        CalculatorRequest request = CalculatorRequest.newBuilder()
                .setCalculator(calculator)
                .build();

        CalculatorResponse response = service.calculate(request);

        System.out.println(response.getResult());

        */
        PrimeNumberDecomposition number = PrimeNumberDecomposition.newBuilder().setNumber(9872621).build();
        PrimeNumberDecompositionRequest request = PrimeNumberDecompositionRequest.newBuilder().setRequest(number).build();
        Iterator<PrimeNumberDecompositionResponse> results = service.primeNumberDecomposition(request);
        results.forEachRemaining(res ->{
            System.out.println(res.getResult());
        });

        channel.shutdown();
    }
}

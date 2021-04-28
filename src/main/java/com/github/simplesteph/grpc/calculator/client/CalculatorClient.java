package com.github.simplesteph.grpc.calculator.client;

import com.proto.calculator.Calculator;
import com.proto.calculator.CalculatorRequest;
import com.proto.calculator.CalculatorResponse;
import com.proto.calculator.CalculatorServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();
        CalculatorServiceGrpc.CalculatorServiceBlockingStub service = CalculatorServiceGrpc.newBlockingStub(channel);
        Calculator calculator = Calculator.newBuilder()
                .setNumber1(3)
                .setNumber2(10)
                .build();

        CalculatorRequest request = CalculatorRequest.newBuilder()
                .setCalculator(calculator)
                .build();

        CalculatorResponse response = service.calculate(request);
        System.out.println(response.getResult());

    }
}

package com.github.simplesteph.grpc.calculator.client;

import com.proto.calculator.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CalculatorClient {

    private static Double result;
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

        /*
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

        PrimeNumberDecomposition number = PrimeNumberDecomposition.newBuilder().setNumber(9872621).build();
        PrimeNumberDecompositionRequest request = PrimeNumberDecompositionRequest.newBuilder().setRequest(number).build();
        Iterator<PrimeNumberDecompositionResponse> results = service.primeNumberDecomposition(request);
        results.forEachRemaining(res ->{
            System.out.println(res.getResult());
        });

         */

        CalculatorServiceGrpc.CalculatorServiceStub service = CalculatorServiceGrpc.newStub(channel);

        CountDownLatch count = new CountDownLatch(1);
        StreamObserver<ComputeAverageRequest> requestObserver = service.computeAverage(new StreamObserver<ComputeAverageResponse>() {

            @Override
            public void onNext(ComputeAverageResponse value) {
                result = value.getResult();
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                count.countDown();
                System.out.println("Resposnta enviada com sucesso!");

            }
        });

        List<Integer> integers = Arrays.asList(10, 15, 20, 50);
        integers.stream().map(i -> ComputeAverageRequest.newBuilder().setNumber(i).build()).forEach(requestObserver::onNext);

        requestObserver.onCompleted();
        count.await(3L, TimeUnit.SECONDS);

        System.out.println(result);

        channel.shutdown();
    }
}

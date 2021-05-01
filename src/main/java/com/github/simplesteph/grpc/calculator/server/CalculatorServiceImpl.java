package com.github.simplesteph.grpc.calculator.server;

import com.proto.calculator.*;
import io.grpc.stub.StreamObserver;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {
    @Override
    public void calculate(CalculatorRequest request, StreamObserver<CalculatorResponse> responseObserver) {
        Calculator calculator = request.getCalculator();
        Integer result = calculator.getNumber1() + calculator.getNumber2();

        CalculatorResponse response = CalculatorResponse.newBuilder().setResult(result).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void primeNumberDecomposition(PrimeNumberDecompositionRequest request, StreamObserver<PrimeNumberDecompositionResponse> responseObserver) {
        Integer number = request.getRequest().getNumber();
        
        boolean hasNext = true;
        
        while (hasNext){
            for (int i = 2; i <=number ; i++) {
                if(number % i ==0){
                    PrimeNumberDecompositionResponse response = PrimeNumberDecompositionResponse.newBuilder().setResult(i).build();
                    responseObserver.onNext(response);

                    number = number / i;

                    if(number == 1){
                        hasNext = false;
                    }
                    break;
                }
            }
        }
        
        responseObserver.onCompleted();
    }
}

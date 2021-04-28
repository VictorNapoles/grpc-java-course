package com.github.simplesteph.grpc.greeting.client;

import com.proto.dummy.DummyServiceGrpc;
import com.proto.greet.Greeting;
import com.proto.greet.GreetingRequest;
import com.proto.greet.GreetingResponse;
import com.proto.greet.GreetingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        //DummyServiceGrpc.DummyServiceBlockingStub client = DummyServiceGrpc.newBlockingStub(channel);

        GreetingServiceGrpc.GreetingServiceBlockingStub client = GreetingServiceGrpc.newBlockingStub(channel);
        Greeting greeting = Greeting.newBuilder().setFirstName("Victor").build();
        GreetingRequest request = GreetingRequest.newBuilder().setGreeting(greeting).build();
        GreetingResponse response = client.greet(request);
        System.out.println(response.getResult());


        channel.shutdown();
    }
}

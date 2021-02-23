package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.CommonUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    HelloWorldService helloWorldService = new HelloWorldService();
    CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(helloWorldService);

    @Test
    void helloWorld() {
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld();
        completableFuture
                .thenAccept(s -> assertEquals("HELLO WORLD", s))
                .join();
    }

    @Test
    void helloWorld_withSize() {
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld_withSize();
        completableFuture
                .thenAccept(s -> assertTrue(s.contains(String.valueOf("hello world".length()))))
                .join();
    }

    @Test
    void helloWorld_multiple_async_calls() {
        String helloWorld = completableFutureHelloWorld.helloWorld_multiple_async_calls();
        assertEquals("HELLO WORLD!", helloWorld);
    }

    @Test
    void helloWorld_3_async_calls() {
        String helloWorld = completableFutureHelloWorld.helloWorld_3_async_calls();
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", helloWorld);
    }

    @Test
    void helloWorld_3_async_calls_log() {
        String helloWorld = completableFutureHelloWorld.helloWorld_3_async_calls_log();
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", helloWorld);
    }

    @Test
    void helloWorld_3_async_calls_log_async() {
        String helloWorld = completableFutureHelloWorld.helloWorld_3_async_calls_log_async();
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", helloWorld);
    }

    @Test
    void helloWorld_3_async_calls_custom_threadpool() {
        String helloWorld = completableFutureHelloWorld.helloWorld_3_async_calls_custom_threadpool();
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", helloWorld);
    }

    @Test
    void helloWorld_3_async_calls_custom_threadpool_async() {
        String helloWorld = completableFutureHelloWorld.helloWorld_3_async_calls_custom_threadpool_async();
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", helloWorld);
    }

    @Test
    void helloWorld_4_async_calls() {
        String helloWorld = completableFutureHelloWorld.helloWorld_4_async_calls();
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE! BYE!", helloWorld);
    }

    @Test
    void helloWorld_thenCompose() {
        CommonUtil.startTimer();
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld_thenCompose();
        completableFuture
                .thenAccept(s -> assertEquals("hello world!", s))
                .join();
        CommonUtil.timeTaken();
    }

    @Test
    void anyOf() {
        String helloWorld = completableFutureHelloWorld.anyOf();
        assertEquals("hello world", helloWorld);
    }
}

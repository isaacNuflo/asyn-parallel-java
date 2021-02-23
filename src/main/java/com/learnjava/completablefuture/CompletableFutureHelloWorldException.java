package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.CommonUtil;
import com.learnjava.util.LoggerUtil;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureHelloWorldException {
    private final HelloWorldService helloWorldService;

    public CompletableFutureHelloWorldException(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public String helloWorld_3_async_calls_handle() {
        CommonUtil.startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return " Hi CompletableFuture!";
        });

        String helloWorld = hello
                .handle((result, exception) -> {
                    if (Objects.nonNull(exception)) {
                        LoggerUtil.log("Exception is: " + exception.getMessage());
                        return "";
                    }
                    return result;
                })
                .thenCombine(world, (h, w) -> h + w)
                .handle((result, exception) -> {
                    if (Objects.nonNull(exception)) {
                        LoggerUtil.log("Exception after world is: " + exception.getMessage());
                        return "";
                    }
                    return result;
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();
        CommonUtil.timeTaken();
        return helloWorld;
    }

    public String helloWorld_3_async_calls_exceptionally() {
        CommonUtil.startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return " Hi CompletableFuture!";
        });

        String helloWorld = hello
                .exceptionally((exception) -> {
                    LoggerUtil.log("Exception is: " + exception.getMessage());
                    return "";
                })
                .thenCombine(world, (h, w) -> h + w)
                .exceptionally((exception) -> {
                    LoggerUtil.log("Exception after world is: " + exception.getMessage());
                    return "";
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();
        CommonUtil.timeTaken();
        return helloWorld;
    }

    public String helloWorld_3_async_calls_whenHandle() {
        CommonUtil.startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return " Hi CompletableFuture!";
        });

        String helloWorld = hello
                .whenComplete((result, exception) -> {
                    if (Objects.nonNull(exception)) {
                        LoggerUtil.log("Exception is: " + exception.getMessage());
                    }
                })
                .thenCombine(world, (h, w) -> h + w)
                .whenComplete((result, exception) -> {
                    if (Objects.nonNull(exception)) {
                        LoggerUtil.log("Exception after world is: " + exception.getMessage());
                    }
                })
                .exceptionally((exception) -> {
                    LoggerUtil.log("Exception after thenCombine is: " + exception.getMessage());
                    return "";
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();
        CommonUtil.timeTaken();
        return helloWorld;
    }
}

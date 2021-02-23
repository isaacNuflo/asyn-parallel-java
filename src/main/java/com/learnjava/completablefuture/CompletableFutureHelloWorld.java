package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.CommonUtil;
import com.learnjava.util.LoggerUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureHelloWorld {

    private final HelloWorldService helloWorldService;

    public CompletableFutureHelloWorld(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public CompletableFuture<String> helloWorld() {
        return CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase);
    }

    public CompletableFuture<String> helloWorld_withSize() {
        return CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(this::addNameLengthTransform);
    }

    private String addNameLengthTransform(String name) {
        return name.length() + " - " + name;
    }

    public String helloWorld_multiple_async_calls() {
        CommonUtil.startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);

        String helloWorld = hello.thenCombine(world, (h, w) -> h + w)
                .thenApply(String::toUpperCase)
                .join();
        CommonUtil.timeTaken();
        return helloWorld;
    }

    public String helloWorld_3_async_calls() {
        CommonUtil.startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return " Hi CompletableFuture!";
        });

        String helloWorld = hello.thenCombine(world, (h, w) -> h + w)
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();
        CommonUtil.timeTaken();
        return helloWorld;
    }

    public String helloWorld_3_async_calls_log() {
        CommonUtil.startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return " Hi CompletableFuture!";
        });

        String helloWorld = hello
                .thenCombine(world, (h, w) -> {
                    LoggerUtil.log("thenCombine h/w");
                    return h + w;
                }).thenCombine(hiCompletableFuture, (previous, current) -> {
                    LoggerUtil.log("thenCombine previous/current");
                    return previous + current;
                })
                .thenApply(result -> {
                    LoggerUtil.log("thenApply");
                    return result.toUpperCase();
                })
                .join();
        CommonUtil.timeTaken();
        return helloWorld;
    }

    public String helloWorld_3_async_calls_log_async() {
        CommonUtil.startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return " Hi CompletableFuture!";
        });

        String helloWorld = hello
                .thenCombineAsync(world, (h, w) -> { //Le indica al API que cambie de hilo si está bloqueado
                    LoggerUtil.log("thenCombine h/w");
                    return h + w;
                }).thenCombineAsync(hiCompletableFuture, (previous, current) -> {
                    LoggerUtil.log("thenCombine previous/current");
                    return previous + current;
                })
                .thenApplyAsync(result -> {
                    LoggerUtil.log("thenApply");
                    return result.toUpperCase();
                })
                .join();
        CommonUtil.timeTaken();
        return helloWorld;
    }

    public String helloWorld_3_async_calls_custom_threadpool() {
        CommonUtil.startTimer();
        ExecutorService executorService = Executors.newFixedThreadPool(CommonUtil.noOfCores());
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello, executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world, executorService);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return " Hi CompletableFuture!";
        }, executorService);

        String helloWorld = hello
                .thenCombine(world, (h, w) -> {
                    LoggerUtil.log("thenCombine h/w");
                    return h + w;
                }).thenCombine(hiCompletableFuture, (previous, current) -> {
                    LoggerUtil.log("thenCombine previous/current");
                    return previous + current;
                })
                .thenApply(result -> {
                    LoggerUtil.log("thenApply");
                    return result.toUpperCase();
                })
                .join();
        CommonUtil.timeTaken();
        return helloWorld;
    }

    public String helloWorld_3_async_calls_custom_threadpool_async() {
        CommonUtil.startTimer();
        ExecutorService executorService = Executors.newFixedThreadPool(CommonUtil.noOfCores());
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello, executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world, executorService);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return " Hi CompletableFuture!";
        }, executorService);

        String helloWorld = hello
                .thenCombineAsync(world, (h, w) -> {
                    LoggerUtil.log("thenCombine h/w");
                    return h + w;
                }, executorService)
                .thenCombineAsync(hiCompletableFuture, (previous, current) -> {
                    LoggerUtil.log("thenCombine previous/current");
                    return previous + current;
                },executorService)
                .thenApplyAsync(result -> {
                    LoggerUtil.log("thenApply");
                    return result.toUpperCase();
                }, executorService)
                .join();
        CommonUtil.timeTaken();
        return helloWorld;
    }

    public String helloWorld_4_async_calls() {
        CommonUtil.startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return " Hi CompletableFuture!";
        });
        CompletableFuture<String> bye = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return " Bye!";
        });

        String helloWorld = hello.thenCombine(world, (h, w) -> h + w) //independant
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenCombine(bye, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();
        CommonUtil.timeTaken();
        return helloWorld;
    }

    public CompletableFuture<String> helloWorld_thenCompose() {
        return CompletableFuture.supplyAsync(helloWorldService::hello)
                .thenCompose(helloWorldService::worldFuture); //Dependant
    }

    public String anyOf() {
        CommonUtil.startTimer();
        //db
        CompletableFuture<String> db = CompletableFuture.supplyAsync(() -> {
           CommonUtil.delay(4000);
           LoggerUtil.log("response from db");
           return "hello world";
        });

        //restCall
        CompletableFuture<String> restCall = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(2000);
            LoggerUtil.log("response from restCall");
            return "hello world";
        });

        //soapCall
        CompletableFuture<String> soapCall = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(3000);
            LoggerUtil.log("response from soapCall");
            return "hello world";
        });

        CompletableFuture<Object> cfAnyOf = CompletableFuture.anyOf(db, restCall, soapCall);

        String result = (String) cfAnyOf.thenApply(response -> {
            if (response instanceof String) {
                return response;
            }
            return null;
        }).join();

        CommonUtil.timeTaken();
        return result;
    }

    public static void main(String[] args) {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase)
                .thenAccept(result -> {
                    LoggerUtil.log("Result is: " + result);
                })
                .join();
        LoggerUtil.log("Done!");
        //CommonUtil.delay(2000);
    }

}

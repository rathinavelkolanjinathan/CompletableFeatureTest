package com.java.async;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.async.dto.Employee;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
// runAsync --> if you want run some  background task asynchronously and do not want
// to return anything then use
// CompletableFuture  runasync() method

public class RunAsyncDemo {

    // this is not used for global thread pool
    public Void saveEmployees(File jsonFile) throws ExecutionException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(new Runnable() {

            @Override
            public void run() {
                try {
                    List<Employee> employees = mapper
                            .readValue(jsonFile, new TypeReference<List<Employee>>() {
                            });
                    //write logic t save list of employee to database
                    //repository.saveAll(employees);
                    System.out.println("Thread : " + Thread.currentThread().getName());
                    System.out.println(employees.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return runAsyncFuture.get();
    }


    //same method implemented With lambda expression

    public Void saveEmployeesWithCustomExecutor(File jsonFile) throws ExecutionException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        Executor executor = Executors.newFixedThreadPool(5); // own thread pool based on the data or core it will splitted more thread and execute concurrently we not implented this peace of code it will take global pool
        CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(
                () -> {
                    try {
                        List<Employee> employees = mapper
                                .readValue(jsonFile, new TypeReference<List<Employee>>() {
                                });
                        //write logic t save list of employee to database
                        //repository.saveAll(employees);
                        System.out.println("Thread : " + Thread.currentThread().getName());
                        System.out.println(employees.size());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, executor);

        return runAsyncFuture.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RunAsyncDemo runAsyncDemo = new RunAsyncDemo();
        runAsyncDemo.saveEmployees(new File("employees.json"));
        runAsyncDemo.saveEmployeesWithCustomExecutor(new File("employees.json"));

    }
}

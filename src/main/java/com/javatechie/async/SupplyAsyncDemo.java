package com.javatechie.async;

import com.javatechie.async.database.EmployeeDatabase;
import com.javatechie.async.dto.Employee;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
// supplyAsync()-> if we want to run  background task asynchronously and want to return any think from
// that task we should use completable feature
public class SupplyAsyncDemo {

    public List<Employee> getEmployees() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newCachedThreadPool();
        CompletableFuture<List<Employee>> listCompletableFuture = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("Executed by : " + Thread.currentThread().getName());
                    return EmployeeDatabase.fetchEmployees();
                },executor);
        return listCompletableFuture.get();
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SupplyAsyncDemo supplyAsyncDemo = new SupplyAsyncDemo();
        List<Employee> employees = supplyAsyncDemo.getEmployees();
        employees.stream().forEach(System.out::println);
    }
}

package com.java.async;

import com.java.async.database.EmployeeDatabase;
import com.java.async.dto.Employee;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class ThenApplyDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ThenApplyDemo service = new ThenApplyDemo();
        service.sendEmailNotification().get();
    }

    public CompletableFuture<Void> sendEmailNotification() {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
                    System.out.println("Fetch Employee........." + Thread.currentThread().getName());
                    return EmployeeDatabase.fetchEmployees();
                })
                .thenApply((employee) -> {
                    System.out.println("Filter new Joiner Employee........." + Thread.currentThread().getName());
                    return employee.stream()
                            .filter(employees -> "TRUE".equals(employees.getNewJoiner())).collect(Collectors.toList());
                }).thenApply((employee) -> {
                    System.out.println("Filter Training Not Completed Employee........." + Thread.currentThread().getName());
                    return employee
                            .stream()
                            .filter(employees -> "TRUE".equals(employees.getLearningPending()))
                            .collect(Collectors.toList());
                })
                .thenApply((employee) -> {
                    System.out.println("Filter Email ........." + Thread.currentThread().getName());
                    return employee.stream()
                            .map(Employee::getEmail)
                            .collect(Collectors.toList());
                })

                .thenAccept((email) -> {
                            System.out.println("Filter Email .........");
                            email.forEach(ThenApplyDemo::sendEmail);
                        }

                );
        return voidCompletableFuture;
    }

    public static void sendEmail(String email) {

        System.out.println("Sending Email........."+email);
    }
}

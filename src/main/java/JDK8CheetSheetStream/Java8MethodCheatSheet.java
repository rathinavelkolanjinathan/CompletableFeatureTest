package JDK8CheetSheetStream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8MethodCheatSheet {
    public static void main(String[] args) {
        List<Employee> empl = EmployeeDataBase.getAllEmployees();

        //1.For Each Demo
        //forEach
        // empl.forEach(e -> System.out.println(e.getName() + e.getDept()));
        // data manipulation means
        // empl.stream().forEach(e -> System.out.println(e.getId()));
        //Method reference
        //empl.stream().forEach(System.out::println);

        //2.Filter Method
        // if we need to check any if condition then use the file like below
        List<Employee> list = empl.stream()
                .filter(e -> e.getDept().equals("Development") && e.getSalary() > 50000)
                .collect(Collectors.toList());

        //   3.Collect  use for set ,map,list,map,minBy
        // if you want collect to set we can write like  collect method we can use set map and list also
        Set<Employee> set = empl.stream()
                .filter(e -> e.getDept().equals("Development") && e.getSalary() > 50000)
                .collect(Collectors.toSet());

        // if we want to display only Id and name means use map
        Map<Integer, String> map = empl.stream()
                .filter(e -> e.getDept().equals("Development") && e.getSalary() > 50000)
                .collect(Collectors.toMap(Employee::getId, Employee::getName));


        //4.Map If you want single attribute from the list then use the map
        List<String> map1 = empl.stream()
                .map(e -> e.getDept())
                .collect(Collectors.toList());

        //if you Want Method Reference Using Map
        List<String> mapRef = empl.stream()
                .map(Employee::getDept)
                .collect(Collectors.toList());

        //if you want to change the Set means  just change the collector.toSet()
        Set<String> mapRef1 = empl.stream()
                .map(Employee::getDept)
                .collect(Collectors.toSet());

        //5.Distinct  If you want to avoid duplicate using like below
        List<String> distinct = empl.stream()
                .map(Employee::getDept)
                .distinct()
                .collect(Collectors.toList());

        //6.FlatMap   below method values need from employee to project class values so below approach is not work
        // So we are use flatmap
        List<Stream<String>> em = empl.stream()
                .map(e -> e.getProjects().stream().map(ee -> ee.getName()))
                .collect(Collectors.toList());

        List<String> flatmap = empl.stream()
                .flatMap(e -> e.getProjects()
                        .stream())
                .map(ee -> ee.getName())
                .collect(Collectors.toList());
        //if you want to avoid duplicate
        List<String> flatmap1 = empl.stream()
                .flatMap(e -> e.getProjects()
                        .stream())
                .map(ee -> ee.getName())
                .distinct()
                .collect(Collectors.toList());
        // System.out.println("Flat Map" + flatmap1);

        //Sorted
        List emp = empl.stream()
                .sorted(Comparator.comparing(e -> e.getSalary()))
                .collect(Collectors.toList());

        // Ascending order
        List<Employee> sorted = empl.stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .collect(Collectors.toList());
        sorted.forEach(System.out::println);

        //Desc

        List<Employee> desc = empl.stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Employee::getSalary)))
                .collect(Collectors.toList());
        sorted.forEach(System.out::println);
        // System.out.println("desc " + desc);

        //Min and Max
        Optional<Employee> highPaidEmployee = empl.stream()
                .max(Comparator.comparingDouble(Employee::getSalary));
        //System.out.println("High Paid Employee.... " + highPaidEmployee);
        Optional<Employee> lowSalary = empl.stream()
                .min(Comparator.comparingDouble(Employee::getSalary));
        //System.out.println("Lowest Paid Employee.... " + lowSalary);

        //Grouping
        //scenario : I want male employee details and I want female employee details so how will get it .
        Map<String, List<Employee>> employeeGroup = empl.stream().collect(Collectors.groupingBy(Employee::getGender));
        // System.out.println("Employee Group .... " + employeeGroup);

        //Gender ->[names]
        Map<String, List<String>> employeeGroup1 = empl.stream()
                .collect(Collectors.groupingBy(Employee::getGender,
                        Collectors.mapping(Employee::getName, Collectors.toList())));
        //output: {Female=[Jane Smith, Lisa White, Sophia Brown, Olivia Harris, Emily Clark], Male=[John Doe, Robert Brown, Michael Green, James Wilson, William Lee]}
        //System.out.println("Employee Group .... " + employeeGroup1);

        //Gender ->[count] if you want counting
        Map<String, Long> count = empl.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
        //output: Group .... {Female=5, Male=5}
        //System.out.println("Group .... " + count);

        //FindFirst -- Find First element from the stream or do any filter
        // condition then need first element you can use FindFirst

        Optional<Employee> findFirst = empl.stream()
                .filter(e -> e.getDept()
                        .equals("Development"))
                .findFirst();
        //scenario 1
        System.out.println("findFirst .... " + findFirst.get()); //NPS(null pointer will come without check the condition
        //scenario 2
        if (findFirst.isPresent())  // this is return boolean values
        {
            System.out.println("findFirst .... " + findFirst.get());
        }
        //scenario 3
        findFirst.ifPresent(e -> System.out.println(e.getName())); // this  way also avoid null pointer

        // if you want to throw the exceptions we use like below .
        Employee exception = empl.stream()
                .filter(e -> e.getDept()
                        .equals("Development"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Department is present"));

        // findAny
        Employee findAny = empl.stream()
                .filter(e -> e.getDept()
                        .equals("Development"))
                .findAny().orElseThrow(() -> new IllegalArgumentException("Department is present"));
//anyMatch()
        boolean anyMatch = empl.stream().anyMatch(e -> e.getDept()
                .equals("Development"));

//allMatch
        boolean allMatch = empl.stream().allMatch(e -> e.getDept()
                .equals("opopi"));

//noneMatch
        boolean noneMatch = empl.stream().noneMatch(e -> e.getDept()
                .equals("HR"));
        //System.out.println("noneMatch" +noneMatch);

        //Limit  below method is print all employee But I want limit the top employee salary
      List<Employee> highSalary =   empl.stream()
              .sorted(Comparator.comparing(Employee::getSalary)
                      .reversed())
              .collect(Collectors.toList());

      // other way
        List<Employee> highSalary2 =   empl.stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Employee::getSalary)))

                .collect(Collectors.toList());
      //
        System.out.println("highSalary" +highSalary);
        List<Employee> highSalary1 =   empl.stream()
                .sorted(Comparator.comparing(Employee::getSalary)
                        .reversed()).limit(3)  //top 3 values
                .collect(Collectors.toList());

        //Skip
       List<Employee> skip =  empl.stream().skip(3).collect(Collectors.toList());

    }
}

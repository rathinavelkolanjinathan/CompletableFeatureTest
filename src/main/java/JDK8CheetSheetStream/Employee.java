package JDK8CheetSheetStream;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    private int id;
    private String name;
    private String dept;
    private List<Project> projects;
    private double salary;
    private String gender;
}

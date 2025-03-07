package JDK8CheetSheetStream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    private String projectCode;
    private String name ;
    private String client;
    private String buLeadName;
}

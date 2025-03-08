package JDK8CheetSheetStream;

import java.util.Collections;
import java.util.Set;

public class singletonCollection {
    public static void main(String[] args) {
        try {
            Set<String> set = Collections.singleton("java");
            set.add("test");
        } catch (UnsupportedOperationException ex) {

            System.out.println("Set is immutable is not able to modify");
        }

    }
}

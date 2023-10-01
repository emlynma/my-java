package emlyn.ma.my.java.base.jvm;

import java.util.ArrayList;
import java.util.List;

public class ArrivalAnalysis {

    public class Inner {
        int a = 9;
    }

    private int id = 1001;
    private String name = "hello";

    public ArrivalAnalysis() {
        id = 100;
        name = "h";
    }

    public void function() {
        int x = 4, y = 4;
        int z = getMax(x, y);
    }

    public int getMax(int a, int b) {
        int c = a + b;
        if (a > b) {
            return a;
        } else if (a < b) {
            return b;
        } else {
            List<String> stringList = new ArrayList<>();
            while (true) {
                stringList.add(new String("abc"));
            }
            //throw new OutOfMemoryError("test");
        }
    }

    public static void main(String[] args) {
        ArrivalAnalysis arrivalAnalysis = new ArrivalAnalysis();
        arrivalAnalysis.function();
    }

}

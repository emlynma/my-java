package emlyn.ma.my.java.base.verify;

import lombok.Data;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LombokTest {

    @Data
    private static class MyObjet {
        private Integer id;
        @NonNull
        private String name;
        public MyObjet(Integer id, @NonNull String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Test
    public void testNonNull() {
        MyObjet myObjet = new MyObjet(1, "test");
        System.out.println(myObjet);
        Assertions.assertThrows(NullPointerException.class, () -> {
            new MyObjet(1, null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            myObjet.setName(null);
        });
    }
}

package emlyn.ma.my.java.common.design.creational;

import lombok.Getter;
import lombok.Setter;

public class Prototype {

    @Getter
    @Setter
    static class PrototypeClass implements Cloneable {
        private String name;

        @Override
        public PrototypeClass clone() throws CloneNotSupportedException {
            PrototypeClass clone = (PrototypeClass) super.clone();
            clone.setName(new String(this.name));
            return clone;
        }
    }

    public static void main(String[] args) {
        PrototypeClass prototypeClass = new PrototypeClass();
        prototypeClass.setName("prototype");
        try {
            PrototypeClass clone = prototypeClass.clone();
            System.out.println(clone.getName());
            System.out.println(prototypeClass == clone);
            System.out.println(prototypeClass.getName() == clone.getName());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

}

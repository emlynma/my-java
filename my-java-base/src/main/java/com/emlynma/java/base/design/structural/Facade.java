package com.emlynma.java.base.design.structural;

public class Facade {

    // 子系统类
    static class SubsystemA {
        public void operationA() {
            System.out.println("Subsystem A operation");
        }
    }

     static class SubsystemB {
        public void operationB() {
            System.out.println("Subsystem B operation");
        }
    }

    static class SubsystemC {
        public void operationC() {
            System.out.println("Subsystem C operation");
        }
    }

    // 门面类
    static class SystemFacade {
        private SubsystemA subsystemA;
        private SubsystemB subsystemB;
        private SubsystemC subsystemC;

        public SystemFacade() {
            subsystemA = new SubsystemA();
            subsystemB = new SubsystemB();
            subsystemC = new SubsystemC();
        }

        public void operation() {
            subsystemA.operationA();
            subsystemB.operationB();
            subsystemC.operationC();
        }
    }

    // 客户端代码
    public static void main(String[] args) {
        SystemFacade facade = new SystemFacade();
        facade.operation();
    }
}

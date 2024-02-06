package com.emlynma.java.common.design.behavioral;

public class Mementos {

    // 备忘录类
    static class Memento {
        private String state;

        public Memento(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }
    }

    // 发起人类
    static class Originator {
        private String state;

        public void setState(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        public Memento createMemento() {
            return new Memento(state);
        }

        public void restoreMemento(Memento memento) {
            state = memento.getState();
        }
    }

    // 管理者类
    static class Caretaker {
        private Memento memento;

        public void saveMemento(Memento memento) {
            this.memento = memento;
        }

        public Memento getMemento() {
            return memento;
        }
    }

    public static void main(String[] args) {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();

        originator.setState("State 1");
        System.out.println("Current state: " + originator.getState());

        Memento memento = originator.createMemento();
        caretaker.saveMemento(memento);

        originator.setState("State 2");
        System.out.println("Current state: " + originator.getState());

        Memento savedMemento = caretaker.getMemento();
        originator.restoreMemento(savedMemento);
        System.out.println("Restored state: " + originator.getState());
    }

}

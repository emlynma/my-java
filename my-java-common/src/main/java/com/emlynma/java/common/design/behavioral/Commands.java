package com.emlynma.java.common.design.behavioral;

public class Commands {

    // 命令接口
    public interface Command {
        void execute();
    }

    // 具体命令类
    static class ConcreteCommand implements Command {
        private final Receiver receiver;

        public ConcreteCommand(Receiver receiver) {
            this.receiver = receiver;
        }

        @Override
        public void execute() {
            receiver.action();
        }
    }

    // 接收者类
    static class Receiver {
        public void action() {
            System.out.println("Receiver executes the command.");
        }
    }

    // 调用者类
    static class Invoker {
        private Command command;

        public void setCommand(Command command) {
            this.command = command;
        }

        public void executeCommand() {
            command.execute();
        }
    }

    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Command command = new ConcreteCommand(receiver);

        Invoker invoker = new Invoker();
        invoker.setCommand(command);
        invoker.executeCommand();
    }

}

package emlyn.ma.my.java.common.design.behavioral;

public class Mediators {

    // 中介者接口
    interface Mediator {
        void sendMessage(String message, Colleague colleague);
    }

    // 具体中介者类
    static class ConcreteMediator implements Mediator {
        private Colleague colleague1;
        private Colleague colleague2;

        public void setColleague1(Colleague colleague1) {
            this.colleague1 = colleague1;
        }

        public void setColleague2(Colleague colleague2) {
            this.colleague2 = colleague2;
        }

        @Override
        public void sendMessage(String message, Colleague colleague) {
            if (colleague == colleague1) {
                colleague2.receiveMessage(message);
            } else if (colleague == colleague2) {
                colleague1.receiveMessage(message);
            }
        }
    }

    // 同事类接口
    interface Colleague {
        void sendMessage(String message);

        void receiveMessage(String message);
    }

    // 具体同事类
    static class ConcreteColleague1 implements Colleague {
        private final Mediator mediator;

        public ConcreteColleague1(Mediator mediator) {
            this.mediator = mediator;
        }

        @Override
        public void sendMessage(String message) {
            mediator.sendMessage(message, this);
        }

        @Override
        public void receiveMessage(String message) {
            System.out.println("ConcreteColleague1 received message: " + message);
        }
    }

    static class ConcreteColleague2 implements Colleague {
        private final Mediator mediator;

        public ConcreteColleague2(Mediator mediator) {
            this.mediator = mediator;
        }

        @Override
        public void sendMessage(String message) {
            mediator.sendMessage(message, this);
        }

        @Override
        public void receiveMessage(String message) {
            System.out.println("ConcreteColleague2 received message: " + message);
        }
    }

    // 客户端代码
    public static void main(String[] args) {
        ConcreteMediator mediator = new ConcreteMediator();

        ConcreteColleague1 colleague1 = new ConcreteColleague1(mediator);
        ConcreteColleague2 colleague2 = new ConcreteColleague2(mediator);

        mediator.setColleague1(colleague1);
        mediator.setColleague2(colleague2);

        colleague1.sendMessage("Hello, I'm ConcreteColleague1.");
        colleague2.sendMessage("Hello, I'm ConcreteColleague2.");
    }

}

package emlyn.ma.my.java.common.design.behavioral;

public class States {

    // 环境类
    static class Context {
        private State state;
        public void setState(State state) {
            this.state = state;
        }
        public void request() {
            state.handle(this);
        }
    }

    // 抽象状态类
    interface State {
        void handle(Context context);
    }

    // 具体状态类
    static class ConcreteStateA implements State {
        @Override
        public void handle(Context context) {
            System.out.println("Handling in State A");
            // 切换到下一个状态
            context.setState(new ConcreteStateB());
        }
    }

    static class ConcreteStateB implements State {
        @Override
        public void handle(Context context) {
            System.out.println("Handling in State B");
            // 切换到下一个状态
            context.setState(new ConcreteStateC());
        }
    }

    static class ConcreteStateC implements State {
        @Override
        public void handle(Context context) {
            System.out.println("Handling in State C");
            // 切换到下一个状态
            context.setState(new ConcreteStateA());
        }
    }

    public static void main(String[] args) {
        Context context = new Context();

        // 初始状态为 State A
        context.setState(new ConcreteStateA());

        // 循环请求，触发状态切换
        for (int i = 0; i < 5; i++) {
            context.request();
        }
    }

}

package emlyn.ma.my.java.common.design.behavioral;

public class ResponsibilityChain {

    // 请求类
    static class Request {
        private final String type;
        private final String content;

        public Request(String type, String content) {
            this.type = type;
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public String getContent() {
            return content;
        }
    }

    // 处理者接口
    public interface Handler {
        void setNextHandler(Handler nextHandler);
        void handleRequest(Request request);
    }

    // 具体处理者
    static class ConcreteHandler1 implements Handler {
        private Handler nextHandler;

        @Override
        public void setNextHandler(Handler nextHandler) {
            this.nextHandler = nextHandler;
        }

        @Override
        public void handleRequest(Request request) {
            if (request.getType().equals("Type1")) {
                System.out.println("ConcreteHandler1 handles the request: " + request.getContent());
            } else if (nextHandler != null) {
                nextHandler.handleRequest(request);
            }
        }
    }

    static class ConcreteHandler2 implements Handler {
        private Handler nextHandler;

        @Override
        public void setNextHandler(Handler nextHandler) {
            this.nextHandler = nextHandler;
        }

        @Override
        public void handleRequest(Request request) {
            if (request.getType().equals("Type2")) {
                System.out.println("ConcreteHandler2 handles the request: " + request.getContent());
            } else if (nextHandler != null) {
                nextHandler.handleRequest(request);
            }
        }
    }

    static class ConcreteHandler3 implements Handler {
        private Handler nextHandler;

        @Override
        public void setNextHandler(Handler nextHandler) {
            this.nextHandler = nextHandler;
        }

        @Override
        public void handleRequest(Request request) {
            if (request.getType().equals("Type3")) {
                System.out.println("ConcreteHandler2 handles the request: " + request.getContent());
            } else if (nextHandler != null) {
                nextHandler.handleRequest(request);
            }
        }
    }

    public static void main(String[] args) {
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        Handler handler3 = new ConcreteHandler3();

        handler1.setNextHandler(handler2);
        handler2.setNextHandler(handler3);

        Request request1 = new Request("Type1", "Request 1");
        Request request2 = new Request("Type2", "Request 2");
        Request request3 = new Request("Type3", "Request 3");

        handler1.handleRequest(request1);
        handler1.handleRequest(request2);
        handler1.handleRequest(request3);
    }

}

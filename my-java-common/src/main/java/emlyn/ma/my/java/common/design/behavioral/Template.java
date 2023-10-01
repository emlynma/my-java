package emlyn.ma.my.java.common.design.behavioral;

public class Template {

    // 抽象类
    static abstract class Game {
        // 模板方法
        public final void play() {
            initialize();
            startGame();
            endGame();
        }

        // 抽象方法，由子类实现
        abstract void initialize();

        // 抽象方法，由子类实现
        abstract void startGame();

        // 钩子方法，子类可选择性地覆盖
        void endGame() {
            System.out.println("Game ended.");
        }
    }

    // 具体类
    static class FootballGame extends Game {
        @Override
        void initialize() {
            System.out.println("Football game initialized.");
        }

        @Override
        void startGame() {
            System.out.println("Football game started.");
        }
    }

    static class BasketballGame extends Game {
        @Override
        void initialize() {
            System.out.println("Basketball game initialized.");
        }

        @Override
        void startGame() {
            System.out.println("Basketball game started.");
        }

        @Override
        void endGame() {
            System.out.println("Basketball game ended.");
        }
    }

    public static void main(String[] args) {
        Game footballGame = new FootballGame();
        footballGame.play();

        System.out.println();

        Game basketballGame = new BasketballGame();
        basketballGame.play();
    }

}

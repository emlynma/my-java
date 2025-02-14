package com.emlynma.java.base.design.structural;

public class Decorator {

    // 咖啡接口
    public interface Coffee {
        double getCost();
        String getDescription();
    }

    // 基础咖啡类
    static class SimpleCoffee implements Coffee {
        @Override
        public double getCost() {
            return 1;
        }
        @Override
        public String getDescription() {
            return "Simple coffee";
        }
    }

    static abstract class CoffeeDecorator implements Coffee {
        protected Coffee decoratedCoffee;

        public CoffeeDecorator(Coffee decoratedCoffee) {
            this.decoratedCoffee = decoratedCoffee;
        }

        @Override
        public double getCost() {
            return decoratedCoffee.getCost();
        }

        @Override
        public String getDescription() {
            return decoratedCoffee.getDescription();
        }
    }

    // 牛奶咖啡类
    static class MilkDecorator extends CoffeeDecorator {
        public MilkDecorator(Coffee decoratedCoffee) {
            super(decoratedCoffee);
        }

        @Override
        public double getCost() {
            return super.getCost() + 0.5;
        }

        @Override
        public String getDescription() {
            return super.getDescription() + ", Milk";
        }
    }

    // 糖浆咖啡类
    static class SyrupDecorator extends CoffeeDecorator {
        public SyrupDecorator(Coffee decoratedCoffee) {
            super(decoratedCoffee);
        }

        @Override
        public double getCost() {
            return super.getCost() + 0.3;
        }

        @Override
        public String getDescription() {
            return super.getDescription() + ", Syrup";
        }
    }

    public static void main(String[] args) {
        Coffee coffee = new SimpleCoffee();
        System.out.println("Cost: " + coffee.getCost() + "; Description: " + coffee.getDescription());

        coffee = new MilkDecorator(coffee);
        System.out.println("Cost: " + coffee.getCost() + "; Description: " + coffee.getDescription());

        coffee = new SyrupDecorator(coffee);
        System.out.println("Cost: " + coffee.getCost() + "; Description: " + coffee.getDescription());
    }

}

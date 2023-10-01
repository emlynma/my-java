package emlyn.ma.my.java.common.design.behavioral;

import java.util.ArrayList;
import java.util.List;

public class Observers {

    // 被观察者接口
    interface Observable {
        void addObserver(Observer observer);
        void removeObserver(Observer observer);
        void notifyObservers();
    }

    // 观察者接口
    interface Observer {
        void update();
    }

    // 具体被观察者类
    static class WeatherStation implements Observable {
        private final List<Observer> observers;
        private String weather;

        public WeatherStation() {
            observers = new ArrayList<>();
        }

        @Override
        public void addObserver(Observer observer) {
            observers.add(observer);
        }

        @Override
        public void removeObserver(Observer observer) {
            observers.remove(observer);
        }

        @Override
        public void notifyObservers() {
            for (Observer observer : observers) {
                observer.update();
            }
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
            notifyObservers();
        }
    }

    // 具体观察者类
    static class WeatherObserver implements Observer {
        private final String name;
        private final WeatherStation weatherStation;

        public WeatherObserver(String name, WeatherStation weatherStation) {
            this.name = name;
            this.weatherStation = weatherStation;
            weatherStation.addObserver(this);
        }

        @Override
        public void update() {
            System.out.println(name + " received weather update: " + weatherStation.getWeather());
        }
    }

    public static void main(String[] args) {
        WeatherStation weatherStation = new WeatherStation();

        WeatherObserver observer1 = new WeatherObserver("Observer 1", weatherStation);
        WeatherObserver observer2 = new WeatherObserver("Observer 2", weatherStation);

        weatherStation.setWeather("Sunny");
        weatherStation.setWeather("Rainy");

        weatherStation.removeObserver(observer2);

        weatherStation.setWeather("Cloudy");
    }

}

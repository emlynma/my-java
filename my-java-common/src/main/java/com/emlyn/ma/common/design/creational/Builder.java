package com.emlyn.ma.common.design.creational;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Builder {

    @Data
    @AllArgsConstructor
    public static class Person {
        private Integer id;
        private String name;
        private Integer age;
        private Integer sex;
        public static PersonBuilder builder() {
            return new PersonBuilder();
        }

        public static class PersonBuilder {
            private Integer id;
            private String name;
            private Integer age;
            private Integer sex;
            PersonBuilder() {
            }
            public PersonBuilder id(Integer id) {
                this.id = id;
                return this;
            }
            public PersonBuilder name(String name) {
                this.name = name;
                return this;
            }
            public PersonBuilder age(Integer age) {
                this.age = age;
                return this;
            }
            public PersonBuilder sex(Integer sex) {
                this.sex = sex;
                return this;
            }
            public Person build() {
                return new Person(this.id, this.name, this.age, this.sex);
            }
        }
    }

    public static void main(String[] args) {
        Person person = Person.builder()
                .age(24)
                .id(1001)
                .name("Haoran Ma")
                .build();
        System.out.println(person);
    }

}

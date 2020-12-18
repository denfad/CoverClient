package ru.denfad.cover.models;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private int id;
    private String login;
    private String password;
    private String name;
    private Integer age;
    private String address;
    private String status;
    private List<Place> places = new ArrayList<>();

    public Person() {
    }

    public Person(String login, String password, String name, Integer age) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public void addPlace(Place p){
        this.places.add(p);
    }
}

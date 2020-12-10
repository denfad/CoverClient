package ru.denfad.cover.models;

public class Place {
    private int placeId;
    private String name;
    private String type;
    private double coefficient;
    private double x_cor;
    private double y_cor;

    public Place(int placeId, String name, String type, double coefficient, double x, double y) {
        this.placeId = placeId;
        this.name = name;
        this.type = type;
        this.coefficient = coefficient;
        this.x_cor = x;
        this.y_cor = y;
    }

    public Place() {
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX_cor() {
        return x_cor;
    }

    public void setX_cor(double x_cor) {
        this.x_cor = x_cor;
    }

    public double getY_cor() {
        return y_cor;
    }

    public void setY_cor(double y_cor) {
        this.y_cor = y_cor;
    }
}

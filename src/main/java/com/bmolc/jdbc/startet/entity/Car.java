package com.bmolc.jdbc.startet.entity;

public class Car {

    private Integer carId;
    private String vin;
    private String make;
    private String mo;
    private String color;
    private Integer year;
    private Double price;

    public Car() {
    }

    public Car(Integer carId, String vin, String make, String mo, String color, Integer year, Double price) {
        this.carId = carId;
        this.vin = vin;
        this.make = make;
        this.mo = mo;
        this.color = color;
        this.year = year;
        this.price = price;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setMo(String mo) {
        this.mo = mo;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getVin() {
        return vin;
    }

    public String getMake() {
        return make;
    }

    public String getMo() {
        return mo;
    }

    public String getColor() {
        return color;
    }

    public Integer getYear() {
        return year;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", vin='" + vin + '\'' +
                ", make='" + make + '\'' +
                ", mo='" + mo + '\'' +
                ", color='" + color + '\'' +
                ", year=" + year +
                ", price=" + price +
                '}';
    }
}

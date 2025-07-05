package com.grupo7.jd20.reservas.core.service;

public class RoomService implements IService {
    private String roomNumber;
    private double price;

    public RoomService(String roomNumber, double price) {
        this.roomNumber = roomNumber;
        this.price = price;
    }

    @Override
    public String description() {
        return "Servicio a la habitación " + roomNumber;
    }

    @Override
    public double price() {
        return price;
    }
}



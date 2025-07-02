package com.grupo7.jd20.reservas.core.room;

public interface IRooms {
    String getRoomNumber();
    String setRoomNumber(String roomNumber);
    String getType();
    int getCapacity();
    int setCapacity(int newCapacity);
    double getPrice();
    double setPrice(double newPrice);
    boolean isAvailable();
    boolean setAvailable(boolean available);
}
package com.grupo7.jd20.reservas.core.room;

public class Suite implements IRooms {

    private String roomNumber;
    private boolean available;
    private int capacity;
    private double price;

    public Suite(String roomNumber){
        this.roomNumber = roomNumber;
        this.available = true;
        this.capacity = 7;
        this.price = 1000.00;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public String setRoomNumber(String roomNumber) {
        return this.roomNumber = roomNumber;
    }

    @Override
    public String getType() {
        return "Suite de lujo";
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int setCapacity(int newCapacity){
        return this.capacity = newCapacity;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double setPrice(double newPrice){
        return this.price = newPrice;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public boolean setAvailable(boolean available) {
        return this.available = available;
    }

    @Override
    public String toString() {
        return ">"+ getType() +": \n"+
                "\tnúmero de habitación " + roomNumber + ",\n" +
                "\tdisponible " + available + ",\n" +
                "\tcapacidad " + capacity + " personas,\n" +
                "\tprecio: $" + price + ",\n" +
                '}';
    }
}

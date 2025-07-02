package com.grupo7.jd20.reservas.core;

import com.grupo7.jd20.reservas.core.room.IRooms;
import com.grupo7.jd20.reservas.core.service.IService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {
    private final String reservationId;
    private Custormer customer;
    private IRooms room;
    private IService service;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private ReservationStatus status;
    private double totalPrice;
    private final LocalDateTime createdAt;
    private String notes;

    public Reservation(Custormer customer, IRooms room, IService service,
                       LocalDate checkIn, LocalDate checkOut) {
        this.reservationId = java.util.UUID.randomUUID().toString();
        this.customer = customer;
        this.room = room;
        this.service = service;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = ReservationStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.totalPrice = calculateTotalPrice();
        this.notes = "";
    }

    private double calculateTotalPrice() {
        long nights = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);
        return service.price() * nights;
    }

    public boolean isValid(){
        return isCustomerValid() &&
                isRoomAvailable() &&
                isServicePresent() &&
                areDatesPresent() &&
                isCheckInBeforeCheckOut() &&
                isCheckInNotInPast();
    }
    private boolean isCustomerValid() {
        return customer != null && customer.isValid();
    }

    private boolean isRoomAvailable() {
        return room != null && room.isAvailable();
    }

    private boolean isServicePresent() {
        return service != null;
    }

    private boolean areDatesPresent() {
        return checkIn != null && checkOut != null;
    }

    private boolean isCheckInBeforeCheckOut() {
        return checkIn.isBefore(checkOut);
    }

    private boolean isCheckInNotInPast() {
        return !checkIn.isBefore(LocalDate.now());
    }

    // Getters
    public String getReservationId() { return reservationId; }
    public Custormer getCustomer() { return customer; }
    public IRooms getRoom() { return room; }
    public IService getService() { return service; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public ReservationStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public double getTotalPrice() { return totalPrice; }
    public String getNotes() { return notes; }

    //Setters
    public void setCustomer(Custormer customer){this.customer = customer;}
    public void setRoom(IRooms room) {this.room = room;}
    public void setService(IService service) {this.service = service;}
    public void setCheckIn(LocalDate checkIn) {this.checkIn = checkIn;}
    public void setCheckOut(LocalDate checkOut) {this.checkOut = checkOut;}
    public void setStatus(ReservationStatus status) { this.status = status; }
    public void setNotes(String notes) { this.notes = notes; }


    @Override
    public String toString() {
        return "🏠 Reserva " + reservationId + ": {\n" +
                "\tcliente: " + customer.getName() + " (" + customer.getEmail() + ")\n" +
                "\thabitación: " + room.getType() + " - " + room.getRoomNumber() + "\n" +
                "\tservicio: " + service.description() + "\n" +
                "\tCheck-in: " + checkIn + "\n" +
                "\tCheck-out: " + checkOut + "\n" +
                "\testado: " + status.getDisplayName() + "\n" +
                "\tprecio total: $" + totalPrice + "\n" +
                "\tnotas: " + notes + "\n" +
                '}';
    }
}

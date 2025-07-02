package com.grupo7.jd20.reservas.events.listeners;

import com.grupo7.jd20.reservas.core.Reservation;
import com.grupo7.jd20.reservas.events.observer.IReservationObserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SystemLogger implements IReservationObserver {
    private static final DateTimeFormatter formatDataLog = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void reservationCreated(Reservation reservation) {
        loggerString("RESERVATION_CREATED", reservation);
    }

    @Override
    public void reservationCancelled(Reservation reservation) {
        loggerString("RESERVATION_CANCELLED", reservation);
    }

    @Override
    public void reservationConfirmed(Reservation reservation) {
        loggerString("RESERVATION_CONFIRMED", reservation);
    }

    private void loggerString(String action, Reservation reservation) {

        String timestamp = LocalDateTime.now().format(formatDataLog);

        String logEntry = "[" + timestamp + "] " + action +
                " - id reserva: " + reservation.getReservationId() +
                ", cliente: " + reservation.getCustomer().getName() +
                ", habitación: " + reservation.getRoom().getRoomNumber();

        System.out.println("📜 LOGGER: " + logEntry);
    }
}

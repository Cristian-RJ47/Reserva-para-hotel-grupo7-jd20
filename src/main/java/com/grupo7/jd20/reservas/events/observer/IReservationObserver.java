package com.grupo7.jd20.reservas.events.observer;

import com.grupo7.jd20.reservas.core.Reservation;

public interface IReservationObserver {
    void reservationCreated(Reservation reservation);
    void reservationCancelled(Reservation reservation);
    void reservationConfirmed(Reservation reservation);
}

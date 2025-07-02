package com.grupo7.jd20.reservas.events.listeners;

import com.grupo7.jd20.reservas.core.Reservation;
import com.grupo7.jd20.reservas.events.observer.IReservationObserver;

public class EmailNotifier implements IReservationObserver {
    private String systemEmail;

    public EmailNotifier(String systemEmail) {
        this.systemEmail = systemEmail;
    }

    @Override
    public void reservationCreated(Reservation reservation) {
        sendEmail(reservation.getCustomer().getEmail(),
                "Reservación creada. Id: " + reservation.getReservationId(),
                "Su reservación ha sido creada exitosamente.\n" + reservation.toString());
        System.out.println("✅ Email enviado: reservación creada para " + reservation.getCustomer().getName());
    }

    @Override
    public void reservationCancelled(Reservation reservation) {
        sendEmail(reservation.getCustomer().getEmail(),
                "Reservación cancelada. Id: " + reservation.getReservationId(),
                "Su reservación ha sido cancelada.\n" + reservation.toString());
        System.out.println("✅ Email enviado: reservación cancelada para " + reservation.getCustomer().getName());
    }

    @Override
    public void reservationConfirmed(Reservation reservation) {
        sendEmail(reservation.getCustomer().getEmail(),
                "Reservación confirmada. Id: " + reservation.getReservationId(),
                "Su reservación ha sido confirmada.\n" + reservation.toString());
        System.out.println("✅ Email enviado: reservación confirmada para " + reservation.getCustomer().getName());
    }

    private void sendEmail(String to, String subject, String body) {
        System.out.println("🔄 Enviando email de " + systemEmail + " a " + to);
        System.out.println("Asunto: " + subject);
        System.out.println("Cuerpo: " + body);
        System.out.println("✅ Email enviado");
    }
}

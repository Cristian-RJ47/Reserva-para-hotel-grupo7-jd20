package com.grupo7.jd20.reservas.app;

import com.grupo7.jd20.reservas.core.factories.SuiteFactory;
import com.grupo7.jd20.reservas.core.Custormer;
import com.grupo7.jd20.reservas.core.Reservation;
import com.grupo7.jd20.reservas.core.room.IRooms;
import com.grupo7.jd20.reservas.core.service.IService;
import com.grupo7.jd20.reservas.core.service.RoomService;
import com.grupo7.jd20.reservas.core.service.decorators.BreakfastServiceDecorator;
import com.grupo7.jd20.reservas.core.service.decorators.SpaServiceDecorator;
import com.grupo7.jd20.reservas.events.listeners.EmailNotifier;
import com.grupo7.jd20.reservas.events.listeners.SystemLogger;
import com.grupo7.jd20.reservas.application.ReservationSystem;
import com.grupo7.jd20.reservas.shared.ValidationUtils;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        ReservationSystem system = ReservationSystem.getInstance();

        // Agregamos el observer
        system.addObserver(new EmailNotifier("myhotelxdxd@hotel.com"));
        system.addObserver(new SystemLogger());

        // Creamos las habitaciones
        SuiteFactory suiteFactory = new SuiteFactory();
        IRooms suite1 = suiteFactory.createRoom("S201");
        IRooms suite2 = suiteFactory.createRoom("S202");

        system.addRoom(suite1);
        system.addRoom(suite2);

        // Registramos al cliente
        Custormer cliente = new Custormer("Toreto", "lafamiliaesprimero@gmail.com", "25587365");
        System.out.println("Cliente creado: " + cliente.getName());
        System.out.println("Cliente válido: " + cliente.isValid());

        // Agregamos los servicios a la habitación seleccionada
        IService serviceSuite1 = new RoomService(suite1.getRoomNumber(), suite1.getPrice());
        serviceSuite1 = new SpaServiceDecorator(new BreakfastServiceDecorator(serviceSuite1));

        System.out.println("\nCREANDO RESERVA ✨\n");

        try {
            // Crear reservación
            LocalDate checkIn = ValidationUtils.parseDate("2025-07-01");
            LocalDate checkOut = ValidationUtils.parseDate("2025-07-05");

            String reservationId = system.createReservation(
                cliente, "S201", serviceSuite1, checkIn, checkOut
            );

            System.out.println("Reserva creada, Id: " + reservationId);

            // Obtener y mostrar reserva
            Reservation reservation = system.getReservation(reservationId);
            System.out.println("\nDETALLES DE LA RESERVA\n");
            System.out.println(reservation);

            System.out.println("\nCONFIRMANDO RESERVA\n");
            boolean confirmed = system.confirmReservation(reservationId);
            System.out.println("Reserva confirmada: " + confirmed + " ✔");

            // Mostrar estadísticas
            System.out.println("\nHABITACIONES DISPONIBLES\n");
            System.out.println("Habitaciones disponibles: " + system.getAvailableRooms().size());

            // Probar cancelación
            System.out.println("\nCANCELANDO RESERVA\n");
            boolean cancelled = system.cancelReservation(reservationId);
            System.out.println("Reserva cancelada: " + cancelled + " 📌");

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }

        // Demostrar que es Singleton
        ReservationSystem system2 = ReservationSystem.getInstance();
        System.out.println("\nVERIFICANDO SINGLETON ✨\n");
        System.out.println("Misma instancia: " + (system == system2));
        System.out.println("Total de reservas en system2: " + system2.getAllReservations().size());
    }
}
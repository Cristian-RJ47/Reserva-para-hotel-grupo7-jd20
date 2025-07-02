package com.grupo7.jd20.reservas.application;

import com.grupo7.jd20.reservas.core.Custormer;
import com.grupo7.jd20.reservas.core.Reservation;
import com.grupo7.jd20.reservas.core.ReservationStatus;
import com.grupo7.jd20.reservas.core.room.IRooms;
import com.grupo7.jd20.reservas.core.service.IService;
import com.grupo7.jd20.reservas.events.observer.IReservationObserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReservationSystem {
    private static volatile ReservationSystem instance;
    private final Map<String, Reservation> reservations;
    private final Map<String, IRooms> rooms;
    private final List<IReservationObserver> observers;

    private ReservationSystem() {
        this.reservations = new ConcurrentHashMap<>();
        this.rooms = new ConcurrentHashMap<>();
        this.observers = new ArrayList<>();
    }
    //Hace que solo exista una instancia de la clase.
    //Se llama double-checking locking, es para el patron singleton
    public static ReservationSystem getInstance() {
        if (instance == null) {
            synchronized (ReservationSystem.class) {
                if (instance == null) {
                    instance = new ReservationSystem();
                }
            }
        }
        return instance;
    }

    // Observer
    public void addObserver(IReservationObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IReservationObserver observer) {
        observers.remove(observer);
    }

    private void notifyReservationCreated(Reservation reservation) {
        for (IReservationObserver observer : observers) {
            observer.reservationCreated(reservation);
        }
    }

    private void notifyReservationCancelled(Reservation reservation) {
        for (IReservationObserver observer : observers) {
            observer.reservationCancelled(reservation);
        }
    }

    private void notifyReservationConfirmed(Reservation reservation) {
        for (IReservationObserver observer : observers) {
            observer.reservationConfirmed(reservation);
        }
    }

    // Manejo de Habitaciones
    public void addRoom(IRooms room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRooms getRoom(String roomNumber) {
        return rooms.get(roomNumber);
    }

    public List<IRooms> getAvailableRooms() {
        return rooms.values().stream().filter(IRooms::isAvailable)
        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    // Manejo de Reservaciones
    public String createReservation(Custormer customer, String roomNumber,
        IService service, LocalDate checkIn, LocalDate checkOut) {

        validateReservationInput(customer, roomNumber, service, checkIn, checkOut);

        IRooms room = getRoomOrThrow(roomNumber);
        validateRoomAvailability(room, roomNumber, checkIn, checkOut);

        Reservation reservation = new Reservation(customer, room, service, checkIn, checkOut);
        validateReservation(reservation);

        processReservation(reservation);

        return reservation.getReservationId();
    }

    public boolean confirmReservation(String reservationId) {
        return processReservationStatusChange(
            reservationId,
            ReservationStatus.PENDING,
            ReservationStatus.CONFIRMED,
            this::notifyReservationConfirmed
        );
    }

    public boolean cancelReservation(String reservationId) {
        Reservation reservation = findReservation(reservationId);
        if (reservation == null || reservation.getStatus() == ReservationStatus.CANCELLED) {
            return false;
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.getRoom().setAvailable(true);
        notifyReservationCancelled(reservation);
        return true;
    }

    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations.values());
    }

    // procesos privados
    private void processReservation(Reservation reservation) {
        reservations.put(reservation.getReservationId(), reservation);
        reservation.getRoom().setAvailable(false);
        notifyReservationCreated(reservation);
    }

    private boolean processReservationStatusChange(
        String reservationId,
        ReservationStatus expectedStatus,
        ReservationStatus newStatus,
        java.util.function.Consumer<Reservation> notificationAction) {
        Reservation reservation = findReservation(reservationId);
        if (reservation == null || reservation.getStatus() != expectedStatus) {
            return false;
        }

        reservation.setStatus(newStatus);
        notificationAction.accept(reservation);
        return true;
    }

    private Reservation findReservation(String reservationId) {
        if (reservationId == null || reservationId.trim().isEmpty()) {
            return null;
        }
        return reservations.get(reservationId);
    }

    // Verifica si la habitación está libre
    private boolean isRoomAvailableForDates(String roomNumber, LocalDate checkIn, LocalDate checkOut) {
        return reservations.values().stream()
            .filter(r -> r.getRoom().getRoomNumber().equals(roomNumber))
            .filter(r -> r.getStatus() == ReservationStatus.CONFIRMED ||
            r.getStatus() == ReservationStatus.PENDING)
            .noneMatch(r -> datesOverlap(r.getCheckIn(), r.getCheckOut(), checkIn, checkOut));
    }

    private boolean datesOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !start1.isAfter(end2) && !end1.isBefore(start2);
    }

    // Métodos para validar las reservaciones

    private void validateReservationInput(Custormer customer, String roomNumber,
        IService service, LocalDate checkIn, LocalDate checkOut) {

        if (customer == null) {
            throw new IllegalArgumentException("❌ Error, el cliente no existe");
        }
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Error, el número de habitación está vacío");
        }
        if (service == null) {
            throw new IllegalArgumentException("❌ Error, no hay ningún servicio");
        }
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Las fechas de llegada y salida son necesarias 👀");
        }
        if (checkIn.isAfter(checkOut) || checkIn.equals(checkOut)) {
            throw new IllegalArgumentException("La fecha de llegada debe ser anterior que la de salida 👀");
        }
    }

    private IRooms getRoomOrThrow(String roomNumber) {
        IRooms room = rooms.get(roomNumber);
        if (room == null) {
            throw new IllegalArgumentException("❌ Habitación " + roomNumber + " no encontrada");
        }
        return room;
    }

    private void validateRoomAvailability(IRooms room, String roomNumber,
                                          LocalDate checkIn, LocalDate checkOut) {
        if (!room.isAvailable()) {
            throw new IllegalStateException("❌ Habitación no disponible: " + roomNumber);
        }

        if (!isRoomAvailableForDates(roomNumber, checkIn, checkOut)) {
            throw new IllegalStateException("❌ Habitación no disponible para las fechas seleccionadas");
        }
    }

    private void validateReservation(Reservation reservation) {
        if (!reservation.isValid()) {
            throw new IllegalArgumentException("Datos de reserva no son válidos 😢");
        }
    }
}

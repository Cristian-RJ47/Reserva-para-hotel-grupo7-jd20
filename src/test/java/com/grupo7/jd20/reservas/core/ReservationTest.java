package com.grupo7.jd20.reservas.core;

import com.grupo7.jd20.reservas.core.room.IRooms;
import com.grupo7.jd20.reservas.core.service.IService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {

    @AfterEach
    void message() {
        System.out.println("👏 Test aplicado correctamente. Yay!");
    }

    // Creando simulación de habitación y servicio

    class TestRoom implements IRooms {
        private final String roomNumber;
        private final boolean available;
        public TestRoom(String roomNumber, boolean available) {
            this.roomNumber = roomNumber;
            this.available = available;
        }
        @Override public String getRoomNumber() { return roomNumber; }

        @Override
        public String setRoomNumber(String roomNumber) {
            return "";
        }

        @Override public boolean isAvailable() { return available; }
        @Override public double getPrice() { return 100.0; }

        @Override
        public double setPrice(double newPrice) {
            return 0;
        }

        @Override public String getType() { return "Suite"; }

        @Override
        public int getCapacity() {
            return 0;
        }

        @Override
        public int setCapacity(int newCapacity) {
            return 0;
        }

        @Override public boolean setAvailable(boolean b) {
            return b;
        }
    }


    class TestService implements IService {
        @Override public String description() { return "Room only"; }
        @Override public double price() { return 150.0; }
    }

    //Testeando validaciones de Reservación

    @Test
    void validReservationShouldBeValid() {
        Custormer customer = new Custormer("Juan", "juan@mail.com", "12345678");
        IRooms room = new TestRoom("101", true);
        IService service = new TestService();
        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now().plusDays(2);
        Reservation reservation = new Reservation(customer, room, service, checkIn, checkOut);

        assertTrue(reservation.isValid());
        assertEquals(150.0 * 2, reservation.getTotalPrice());
    }

    @Test
    void reservationShouldBeInvalidIfCustomerIsNull() {
        IRooms room = new TestRoom("101", true);
        IService service = new TestService();
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(3);

        Reservation reservation = new Reservation(null, room, service, checkIn, checkOut);

        assertFalse(reservation.isValid());
    }

    @Test
    void reservationShouldBeInvalidIfRoomIsUnavailable() {
        Custormer customer = new Custormer("Ana", "ana@mail.com", "87654321");
        IRooms room = new TestRoom("101", false);
        IService service = new TestService();
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(3);

        Reservation reservation = new Reservation(customer, room, service, checkIn, checkOut);

        assertFalse(reservation.isValid());
    }

    @Test
    void reservationShouldBeInvalidIfCheckInAfterCheckOut() {
        Custormer customer = new Custormer("Luis", "luis@mail.com", "98765432");
        IRooms room = new TestRoom("101", true);
        IService service = new TestService();
        LocalDate checkIn = LocalDate.now().plusDays(5);
        LocalDate checkOut = LocalDate.now().plusDays(2);

        Reservation reservation = new Reservation(customer, room, service, checkIn, checkOut);

        assertFalse(reservation.isValid());
    }

    @Test
    void reservationShouldBeInvalidIfCheckInBeforeToday() {
        Custormer customer = new Custormer("Luis", "luis@mail.com", "98765432");
        IRooms room = new TestRoom("101", true);
        IService service = new TestService();
        LocalDate checkIn = LocalDate.now().minusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(2);

        Reservation reservation = new Reservation(customer, room, service, checkIn, checkOut);

        assertFalse(reservation.isValid());
    }
}

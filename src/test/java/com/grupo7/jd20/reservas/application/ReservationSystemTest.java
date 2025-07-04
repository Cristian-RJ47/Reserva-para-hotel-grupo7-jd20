package com.grupo7.jd20.reservas;

import com.grupo7.jd20.reservas.application.ReservationSystem;
import com.grupo7.jd20.reservas.core.*;
import com.grupo7.jd20.reservas.core.room.IRooms;
import com.grupo7.jd20.reservas.core.room.Suite;
import com.grupo7.jd20.reservas.core.service.IService;

import com.grupo7.jd20.reservas.core.service.RoomService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ReservationSystemTest {

    private ReservationSystem system;

    @BeforeEach
    void setUp() {
        system = ReservationSystem.getInstance();
    }

    @AfterEach
    void message() {
        System.out.println("👏 Test aplicado correctamente. Yay!");
    }

    //Testeando creación valida e invalida de reservaciones

    @Test
    void shouldCreateReservationSuccessfully() {
        system.getAllReservations().clear(); // limpia estado previo

        IRooms room = new Suite("S01");
        Custormer customer = new Custormer("Ana", "ana@mail.com", "77777777");
        IService service = new RoomService(room.getRoomNumber(), room.getPrice());

        system.addRoom(room);

        String reservationId = system.createReservation(
                customer, "S01", service,
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)
        );

        assertNotNull(reservationId);
        assertNotNull(system.getReservation(reservationId));
    }

    @Test
    void shouldThrowWhenCreatingInvalidReservation() {
        system.getAllReservations().clear();

        assertThrows(IllegalArgumentException.class, () -> {
            system.createReservation(null, "", null,
                    LocalDate.now(), LocalDate.now());
        });
    }

    // Testeando confirmación y cancelación de reservas

@Test
void shouldConfirmReservationSuccessfully() {
    ReservationSystem system = ReservationSystem.getInstance();
    system.getAllReservations().clear();

    IRooms room = new Suite("S02");
    Custormer customer = new Custormer("Luis", "luis@mail.com", "12345678");
    IService service = new RoomService(room.getRoomNumber(), room.getPrice());

    system.addRoom(room);

    String reservationId = system.createReservation(
            customer, "S02", service,
            LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)
    );

    boolean confirmed = system.confirmReservation(reservationId);
    assertTrue(confirmed);
}

    @Test
    void shouldCancelReservationSuccessfully() {
        ReservationSystem system = ReservationSystem.getInstance();
        system.getAllReservations().clear();

        IRooms room = new Suite("S03");
        Custormer customer = new Custormer("Juana", "juana@mail.com", "88888888");
        IService service = new RoomService(room.getRoomNumber(), room.getPrice());

        system.addRoom(room);

        String reservationId = system.createReservation(
                customer, "S03", service,
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(4)
        );

        boolean cancelled = system.cancelReservation(reservationId);
        assertTrue(cancelled);
    }

    // Testeando que devuelva habitaciones disponibles

    @Test
    void shouldReturnOnlyAvailableRooms() {
        ReservationSystem system = ReservationSystem.getInstance();
        system.getAllReservations().clear();

        IRooms availableRoom = new Suite("A1");
        IRooms notAvailableRoom = new Suite("A2");
        notAvailableRoom.setAvailable(false);

        system.addRoom(availableRoom);
        system.addRoom(notAvailableRoom);

        List<IRooms> availableRooms = system.getAvailableRooms();
        assertEquals(1, availableRooms.size());
        assertEquals("A1", availableRooms.get(0).getRoomNumber());
    }


}





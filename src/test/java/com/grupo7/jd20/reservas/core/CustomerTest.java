package com.grupo7.jd20.reservas.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @AfterEach
    void message() {
        System.out.println("👏 Test aplicado correctamente. Yay!");
    }

    //Testeando métodos validos e invalidos en la creación de clientes

    @Test
    void shouldBeValidCustomerWithCorrectData() {
        Custormer customer = new Custormer("Cristian", "cristi@an.com", "12345678");
        assertTrue(customer.isValid());
    }

    @Test
    void shouldFailWhenEmailIsInvalid() {
        Custormer customer = new Custormer("Valeria", "wrongemail", "12345678");
        assertFalse(customer.isValid());
    }

    @Test
    void shouldFailWhenNameIsEmpty() {
        Custormer customer = new Custormer("   ", "otro@email.com", "12345678");
        assertFalse(customer.isValid());
    }

    @Test
    void shouldFailWhenPhoneHasWrongFormat() {
        Custormer customer = new Custormer("Jud", "hola@hi.com", "1234ups!");
        assertFalse(customer.isValid());
    }

    @Test
    void shouldFailWhenPhoneHasLessDigits() {
        Custormer customer = new Custormer("Reinaldo", "familia@si.com", "1234567");
        assertFalse(customer.isValid());
    }

    // Testeando getter y setters

    @Test
    void shouldAllowGettingAndSettingAttributes() {
        Custormer customer = new Custormer("Amber", "amber@mail.com", "12345678");

        customer.setName("Amby");
        customer.setEmail("amby@email.com");
        customer.setPhone("87654321");

        assertEquals("Amby", customer.getName());
        assertEquals("amby@email.com", customer.getEmail());
        assertEquals("87654321", customer.getPhone());
    }

    //Testeando que genere Ids unicos

    @Test
    void shouldGenerateUniqueId() {
        Custormer customer1 = new Custormer("Uno", "uno@email.com", "12345678");
        Custormer customer2 = new Custormer("Dos", "dos@email.com", "87654321");

        assertNotNull(customer1.getIdCustomer());
        assertNotNull(customer2.getIdCustomer());
        assertNotEquals(customer1.getIdCustomer(), customer2.getIdCustomer());
    }
}

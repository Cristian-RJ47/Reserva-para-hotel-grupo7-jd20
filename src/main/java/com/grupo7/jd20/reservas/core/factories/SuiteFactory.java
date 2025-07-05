package com.grupo7.jd20.reservas.core.factories;

import com.grupo7.jd20.reservas.core.room.IRooms;
import com.grupo7.jd20.reservas.core.room.Suite;

public class SuiteFactory extends AbstractRoomsFactory {
    @Override
    public IRooms createRoom(String roomNumber) {
        return new Suite(roomNumber);
    }
}



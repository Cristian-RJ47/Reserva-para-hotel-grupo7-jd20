package com.grupo7.jd20.reservas.core.factories;

import com.grupo7.jd20.reservas.core.room.IRooms;

public abstract class AbstractRoomsFactory {
    public abstract IRooms createRoom(String roomNumber);
}




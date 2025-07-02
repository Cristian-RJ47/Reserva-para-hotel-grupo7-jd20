package com.grupo7.jd20.reservas.core.service.decorators;

import com.grupo7.jd20.reservas.core.service.IService;
import com.grupo7.jd20.reservas.core.service.ServiceDecorator;

public class BreakfastServiceDecorator extends ServiceDecorator {

    public BreakfastServiceDecorator(IService roomService) {
        super(roomService);
    }

    @Override
    public String description() {
        return super.description() + "\n✅ servicio de desayuno";
    }

    @Override
    public double price() {
        return super.price() + 30.0;
    }
}

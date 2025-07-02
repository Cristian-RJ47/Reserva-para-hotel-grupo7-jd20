package com.grupo7.jd20.reservas.core.service.decorators;

import com.grupo7.jd20.reservas.core.service.IService;
import com.grupo7.jd20.reservas.core.service.ServiceDecorator;

public class SpaServiceDecorator extends ServiceDecorator {
    public SpaServiceDecorator(IService service) {
        super(service);
    }
    @Override
    public String description() {
        return service.description() + "\n✅ servicio de Spa";
    }
    @Override
    public double price() {
        return service.price() + 70.0;
    }
}

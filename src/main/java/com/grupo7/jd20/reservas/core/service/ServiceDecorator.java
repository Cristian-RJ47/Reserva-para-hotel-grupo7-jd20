package com.grupo7.jd20.reservas.core.service;

public abstract class ServiceDecorator implements IService {
    protected IService service;

    public ServiceDecorator(IService service) {
        this.service = service;
    }

    @Override
    public String description() {
        return service.description();
    }

    @Override
    public double price() {
        return service.price();
    }
}

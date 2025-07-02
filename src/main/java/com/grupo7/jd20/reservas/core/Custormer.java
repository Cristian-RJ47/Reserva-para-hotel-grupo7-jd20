package com.grupo7.jd20.reservas.core;

public class Custormer {
    private final String idCustomer;
    private String name;
    private String email;
    private String phone;

    public Custormer(String name, String email, String phone) {
        this.idCustomer = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public boolean isValid(){
        return emailIsValid() && nameIsValid() && phoneIsValid();
    }

    private boolean emailIsValid(){
        return email !=null && email.matches("^(?![.])[a-zA-Z0-9._%+-]{1,64}(?<![.])@[a-zA-Z0-9.-]{1,253}\\.[a-zA-Z]{2,63}$");
    }

    private boolean nameIsValid(){
        return name != null && !name.trim().isEmpty();
    }

    private  boolean phoneIsValid(){
        return phone != null && phone.matches("\\d{8}");
    }

    public String getIdCustomer() {return idCustomer;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getPhone() {return phone;}

    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setPhone(String phone) {this.phone = phone;}

    @Override
    public String toString() {
        return "🌟 Cliente "+name+": {" +
                "\n\tid: " + idCustomer + ',' +
                "\n\tnombre: " + name + ',' +
                "\n\tcorreo electrónico: " + email + ',' +
                "\n\tteléfono: " + phone + "\n"+
                '}';
    }
}

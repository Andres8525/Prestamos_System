package com.example.cobro.model;

import java.util.List;

public abstract class Usuario {

    private Long id;
    private String nombre;
    private String email;
    private String contrasena;
    private boolean activo;

    public Usuario() {}

    public Usuario(Long id, String nombre, String email, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.activo = true;
    }

    // Metodos abstractos — polimorfismo
    public abstract boolean autenticar();
    public abstract List<String> getPermisos();

    @Override
    public abstract String toString();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}

package com.example.cobro.model;

import java.util.Arrays;
import java.util.List;

public class Administrador extends Usuario {

    private String codigoAdmin;

    public Administrador() {}

    public Administrador(Long id, String nombre, String email, String contrasena, String codigoAdmin) {
        super(id, nombre, email, contrasena);
        this.codigoAdmin = codigoAdmin;
    }

    // Implementacion polimorfica de autenticar
    @Override
    public boolean autenticar() {
        return isActivo() && getEmail() != null && getContrasena() != null;
    }

    // Implementacion polimorfica de getPermisos
    @Override
    public List<String> getPermisos() {
        return Arrays.asList(
            "GESTIONAR_CLIENTES",
            "CREAR_PRESTAMOS",
            "REGISTRAR_PAGOS",
            "VER_PANEL",
            "GENERAR_REPORTES"
        );
    }

    // Implementacion polimorfica de toString
    @Override
    public String toString() {
        return "Administrador{id=" + getId() +
               ", nombre=" + getNombre() +
               ", email=" + getEmail() +
               ", codigoAdmin=" + codigoAdmin + "}";
    }

    public void gestionarClientes() {}

    public void generarReporte() {}

    public String getCodigoAdmin() { return codigoAdmin; }
    public void setCodigoAdmin(String codigoAdmin) { this.codigoAdmin = codigoAdmin; }
}

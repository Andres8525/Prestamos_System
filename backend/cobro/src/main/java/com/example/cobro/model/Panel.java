package com.example.cobro.model;

import java.util.ArrayList;
import java.util.List;

public class Panel {

    private Administrador usuario;
    private List<PanelItem> items = new ArrayList<>();

    public Panel() {}

    public Panel(Administrador usuario) {
        this.usuario = usuario;
    }

    public void cargarDatos() {
        items.forEach(PanelItem::calcularTotal);
    }

    public PanelItem getClientesAdeuda() {
        return items.stream()
                .filter(i -> "CLIENTES_ADEUDA".equals(i.getTitle()))
                .findFirst().orElse(null);
    }

    public PanelItem getClientesPendientes() {
        return items.stream()
                .filter(i -> "CLIENTES_PENDIENTES".equals(i.getTitle()))
                .findFirst().orElse(null);
    }

    public PanelItem getPagoPendientes() {
        return items.stream()
                .filter(i -> "PAGOS_PENDIENTES".equals(i.getTitle()))
                .findFirst().orElse(null);
    }

    public Double getResumen() {
        return items.stream()
                .mapToDouble(i -> i.getValor() != null ? i.getValor() : 0.0)
                .sum();
    }

    public Administrador getUsuario() { return usuario; }
    public void setUsuario(Administrador usuario) { this.usuario = usuario; }

    public List<PanelItem> getItems() { return items; }
    public void setItems(List<PanelItem> items) { this.items = items; }
}

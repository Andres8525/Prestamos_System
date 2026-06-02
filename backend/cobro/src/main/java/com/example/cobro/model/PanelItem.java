package com.example.cobro.model;

import java.util.ArrayList;
import java.util.List;

public class PanelItem {

    private Long id;
    private String title;
    private Double valor;
    private String color;
    private List<PanelItem> hijos = new ArrayList<>();

    public PanelItem() {}

    public PanelItem(Long id, String title, Double valor, String color) {
        this.id = id;
        this.title = title;
        this.valor = valor;
        this.color = color;
    }

    public void calcularTotal() {
        if (!hijos.isEmpty()) {
            this.valor = hijos.stream()
                    .mapToDouble(h -> h.getValor() != null ? h.getValor() : 0.0)
                    .sum();
        }
    }

    public PanelItem getHijo(int index) {
        return hijos.get(index);
    }

    public void calcHijo() {
        hijos.forEach(PanelItem::calcularTotal);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public List<PanelItem> getHijos() { return hijos; }
    public void setHijos(List<PanelItem> hijos) { this.hijos = hijos; }
}

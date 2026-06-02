package com.example.cobro.dto;

import com.example.cobro.model.EstadoPago;
import com.example.cobro.model.Pago;
import java.time.LocalDate;

public class PagoResponseDTO {

    private Long id;
    private Long prestamoId;
    private Integer numero;
    private Double monto;
    private LocalDate fechaProgramada;
    private LocalDate fechaPago;
    private EstadoPago estado;

    public PagoResponseDTO() {}

    public static PagoResponseDTO fromEntity(Pago pago) {
        PagoResponseDTO dto = new PagoResponseDTO();
        dto.setId(pago.getId());
        dto.setPrestamoId(pago.getPrestamoId());
        dto.setNumero(pago.getNumero());
        dto.setMonto(pago.getMonto());
        dto.setFechaProgramada(pago.getFechaProgramada());
        dto.setFechaPago(pago.getFechaPago());
        dto.setEstado(pago.getEstado());
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPrestamoId() { return prestamoId; }
    public void setPrestamoId(Long prestamoId) { this.prestamoId = prestamoId; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public LocalDate getFechaProgramada() { return fechaProgramada; }
    public void setFechaProgramada(LocalDate fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }

    public EstadoPago getEstado() { return estado; }
    public void setEstado(EstadoPago estado) { this.estado = estado; }
}

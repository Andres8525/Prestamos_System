package com.example.cobro.dto;

import com.example.cobro.model.EstadoPago;
import com.example.cobro.model.Prestamo;
import com.example.cobro.model.TipoLiquidacion;
import java.time.LocalDate;

public class PrestamoResponseDTO {

    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private Double monto;
    private Double tasaInteres;
    private Double montoTotal;
    private TipoLiquidacion tipoLiquidacion;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private EstadoPago estado;

    public PrestamoResponseDTO() {}

    public static PrestamoResponseDTO fromEntity(Prestamo prestamo) {
        PrestamoResponseDTO dto = new PrestamoResponseDTO();
        dto.setId(prestamo.getId());
        dto.setClienteId(prestamo.getClienteId());
        dto.setClienteNombre(prestamo.getCliente() != null ? prestamo.getCliente().getNombre() : null);
        dto.setMonto(prestamo.getMonto());
        dto.setTasaInteres(prestamo.getTasaInteres());
        dto.setMontoTotal(prestamo.getMontoTotal());
        dto.setTipoLiquidacion(prestamo.getTipoLiquidacion());
        dto.setFechaInicio(prestamo.getFechaInicio());
        dto.setFechaVencimiento(prestamo.getFechaVencimiento());
        dto.setEstado(prestamo.getEstado());
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public Double getTasaInteres() { return tasaInteres; }
    public void setTasaInteres(Double tasaInteres) { this.tasaInteres = tasaInteres; }

    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }

    public TipoLiquidacion getTipoLiquidacion() { return tipoLiquidacion; }
    public void setTipoLiquidacion(TipoLiquidacion tipoLiquidacion) {
        this.tipoLiquidacion = tipoLiquidacion;
    }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public EstadoPago getEstado() { return estado; }
    public void setEstado(EstadoPago estado) { this.estado = estado; }
}

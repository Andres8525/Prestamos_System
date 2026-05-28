package com.example.cobro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prestamo_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "pagos"})
    private Prestamo prestamo;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private LocalDate fechaProgramada;

    private LocalDate fechaPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado;

    public Pago() {}

    // Campo calculado para el frontend
    public Long getPrestamoId() {
        return prestamo != null ? prestamo.getId() : null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Prestamo getPrestamo() { return prestamo; }
    public void setPrestamo(Prestamo prestamo) { this.prestamo = prestamo; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public LocalDate getFechaProgramada() { return fechaProgramada; }
    public void setFechaProgramada(LocalDate fechaProgramada) { this.fechaProgramada = fechaProgramada; }

    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }

    public EstadoPago getEstado() { return estado; }
    public void setEstado(EstadoPago estado) { this.estado = estado; }
}

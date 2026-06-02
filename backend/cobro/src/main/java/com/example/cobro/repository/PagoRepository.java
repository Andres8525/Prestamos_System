package com.example.cobro.repository;

import com.example.cobro.model.EstadoPago;
import com.example.cobro.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByPrestamo_IdOrderByNumeroAsc(Long prestamoId);

    List<Pago> findByPrestamo_IdAndEstado(Long prestamoId, EstadoPago estado);

    Optional<Pago> findFirstByPrestamo_IdAndEstadoOrderByNumeroAsc(Long prestamoId, EstadoPago estado);

    List<Pago> findByEstado(EstadoPago estado);

    long countByEstado(EstadoPago estado);

    List<Pago> findByPrestamo_IdAndFechaPagoIsNotNull(Long prestamoId);

    @org.springframework.data.jpa.repository.Query(
        "SELECT COUNT(p) FROM Pago p WHERE p.fechaPago IS NOT NULL AND YEAR(p.fechaPago) = :year AND MONTH(p.fechaPago) = :month")
    long countRealizadosByMes(@org.springframework.data.repository.query.Param("year") int year,
                              @org.springframework.data.repository.query.Param("month") int month);
}

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
}

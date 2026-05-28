package com.example.cobro.repository;

import com.example.cobro.model.EstadoPago;
import com.example.cobro.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findByCliente_Id(Long clienteId);

    List<Prestamo> findByEstado(EstadoPago estado);

    @Query("SELECT COALESCE(SUM(p.montoTotal), 0) FROM Prestamo p " +
           "WHERE YEAR(p.fechaInicio) = :year AND MONTH(p.fechaInicio) = :month")
    Double sumMontoTotalByMes(@Param("year") int year, @Param("month") int month);
}

package com.example.cobro.repository;

import com.example.cobro.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCedula(String cedula);
    boolean existsByCedula(String cedula);
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
}

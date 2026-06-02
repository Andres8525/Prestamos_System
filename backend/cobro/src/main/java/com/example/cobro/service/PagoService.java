package com.example.cobro.service;

import com.example.cobro.model.EstadoPago;
import com.example.cobro.model.Pago;
import com.example.cobro.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Transactional(readOnly = true)
    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Pago> obtenerPago(Long id) {
        return pagoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Pago> listarPorPrestamo(Long prestamoId) {
        return pagoRepository.findByPrestamo_IdOrderByNumeroAsc(prestamoId);
    }

    @Transactional(readOnly = true)
    public List<Pago> listarPendientesPorPrestamo(Long prestamoId) {
        return pagoRepository.findByPrestamo_IdAndEstado(prestamoId, EstadoPago.AL_DIA);
    }

    @Transactional(readOnly = true)
    public List<Pago> listarRealizadosPorPrestamo(Long prestamoId) {
        return pagoRepository.findByPrestamo_IdAndFechaPagoIsNotNull(prestamoId);
    }

    @Transactional(readOnly = true)
    public List<Pago> listarAtrasados() {
        return pagoRepository.findByEstado(EstadoPago.ATRASADO);
    }

    @Transactional(readOnly = true)
    public long contarPendientes() {
        return pagoRepository.countByEstado(EstadoPago.AL_DIA);
    }

    public Pago marcarPagoRealizado(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));

        LocalDate hoy = LocalDate.now();
        long diasAtraso = ChronoUnit.DAYS.between(pago.getFechaProgramada(), hoy);

        pago.setFechaPago(hoy);

        if (diasAtraso <= 0) {
            pago.setEstado(EstadoPago.AL_DIA);
        } else if (diasAtraso <= 3) {
            pago.setEstado(EstadoPago.PENDIENTE);
        } else {
            pago.setEstado(EstadoPago.ATRASADO);
        }

        return pagoRepository.save(pago);
    }

    public Pago actualizarPago(Long id, Pago datos) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
        pago.setEstado(datos.getEstado());
        pago.setFechaPago(datos.getFechaPago());
        return pagoRepository.save(pago);
    }

    public void eliminarPago(Long id) {
        if (!pagoRepository.existsById(id)) {
            throw new RuntimeException("Pago no encontrado con ID: " + id);
        }
        pagoRepository.deleteById(id);
    }
}

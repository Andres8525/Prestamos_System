package com.example.cobro.service;

import com.example.cobro.model.*;
import com.example.cobro.repository.ClienteRepository;
import com.example.cobro.repository.PagoRepository;
import com.example.cobro.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrestamoService {

    private static final double TASA_INTERES = 20.0;
    private static final int CUOTAS_DIARIO = 40;
    private static final int CUOTAS_SEMANAL = 4;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PagoRepository pagoRepository;

    public Prestamo crearPrestamo(Long clienteId, Double monto, TipoLiquidacion tipo) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));

        double montoTotal = monto * (1 + TASA_INTERES / 100);
        LocalDate hoy = LocalDate.now();

        Prestamo prestamo = new Prestamo();
        prestamo.setCliente(cliente);
        prestamo.setMonto(monto);
        prestamo.setTasaInteres(TASA_INTERES);
        prestamo.setMontoTotal(montoTotal);
        prestamo.setTipoLiquidacion(tipo);
        prestamo.setFechaInicio(hoy);
        prestamo.setEstado(EstadoPago.AL_DIA);

        if (tipo == TipoLiquidacion.DIARIO) {
            prestamo.setFechaVencimiento(hoy.plusDays(CUOTAS_DIARIO));
        } else {
            prestamo.setFechaVencimiento(hoy.plusWeeks(CUOTAS_SEMANAL));
        }

        Prestamo guardado = prestamoRepository.save(prestamo);
        generarCuotas(guardado, montoTotal, tipo, hoy);

        return guardado;
    }

    private void generarCuotas(Prestamo prestamo, double montoTotal, TipoLiquidacion tipo, LocalDate inicio) {
        List<Pago> cuotas = new ArrayList<>();

        if (tipo == TipoLiquidacion.DIARIO) {
            double montoCuota = montoTotal / CUOTAS_DIARIO;
            for (int i = 1; i <= CUOTAS_DIARIO; i++) {
                Pago pago = new Pago();
                pago.setPrestamo(prestamo);
                pago.setNumero(i);
                pago.setMonto(montoCuota);
                pago.setFechaProgramada(inicio.plusDays(i));
                pago.setEstado(EstadoPago.AL_DIA);
                cuotas.add(pago);
            }
        } else {
            double montoCuota = montoTotal / CUOTAS_SEMANAL;
            for (int i = 1; i <= CUOTAS_SEMANAL; i++) {
                Pago pago = new Pago();
                pago.setPrestamo(prestamo);
                pago.setNumero(i);
                pago.setMonto(montoCuota);
                pago.setFechaProgramada(inicio.plusWeeks(i));
                pago.setEstado(EstadoPago.AL_DIA);
                cuotas.add(pago);
            }
        }

        pagoRepository.saveAll(cuotas);
    }

    @Transactional(readOnly = true)
    public Optional<Prestamo> obtenerPrestamo(Long id) {
        return prestamoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listarPorCliente(Long clienteId) {
        return prestamoRepository.findByCliente_Id(clienteId);
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listarPorEstado(EstadoPago estado) {
        return prestamoRepository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    public Double obtenerTotalMes(int year, int month) {
        return prestamoRepository.sumMontoTotalByMes(year, month);
    }

    public Prestamo actualizarPrestamo(Long id, Prestamo datos) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));
        prestamo.setEstado(datos.getEstado());
        return prestamoRepository.save(prestamo);
    }

    public void eliminarPrestamo(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));
        pagoRepository.deleteAll(pagoRepository.findByPrestamo_IdOrderByNumeroAsc(id));
        prestamoRepository.delete(prestamo);
    }

    /**
     * Recalcula el estado de todos los préstamos activos según los días de atraso
     * de la próxima cuota pendiente.
     */
    public void actualizarEstados() {
        List<Prestamo> prestamos = prestamoRepository.findAll();
        LocalDate hoy = LocalDate.now();

        for (Prestamo prestamo : prestamos) {
            Optional<Pago> proximoPago = pagoRepository
                    .findFirstByPrestamo_IdAndEstadoOrderByNumeroAsc(prestamo.getId(), EstadoPago.AL_DIA);

            if (proximoPago.isEmpty()) {
                // Todos los pagos realizados
                prestamo.setEstado(EstadoPago.AL_DIA);
                prestamoRepository.save(prestamo);
                continue;
            }

            long diasAtraso = ChronoUnit.DAYS.between(proximoPago.get().getFechaProgramada(), hoy);

            EstadoPago nuevoEstado;
            if (prestamo.getTipoLiquidacion() == TipoLiquidacion.DIARIO) {
                if (diasAtraso <= 0) {
                    nuevoEstado = EstadoPago.AL_DIA;
                } else if (diasAtraso <= 3) {
                    nuevoEstado = EstadoPago.PENDIENTE;
                } else {
                    nuevoEstado = EstadoPago.ATRASADO;
                }
            } else {
                // SEMANAL: rojo directamente si 7+ días
                if (diasAtraso <= 0) {
                    nuevoEstado = EstadoPago.AL_DIA;
                } else if (diasAtraso < 7) {
                    nuevoEstado = EstadoPago.PENDIENTE;
                } else {
                    nuevoEstado = EstadoPago.ATRASADO;
                }
            }

            prestamo.setEstado(nuevoEstado);
            prestamoRepository.save(prestamo);
        }
    }
}

package com.example.cobro.controller;

import com.example.cobro.model.Pago;
import com.example.cobro.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<Pago>> listar() {
        return ResponseEntity.ok(pagoService.listarPagos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtener(@PathVariable Long id) {
        return pagoService.obtenerPago(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/prestamo/{prestamoId}")
    public ResponseEntity<List<Pago>> listarPorPrestamo(@PathVariable Long prestamoId) {
        return ResponseEntity.ok(pagoService.listarPorPrestamo(prestamoId));
    }

    @GetMapping("/prestamo/{prestamoId}/pendientes")
    public ResponseEntity<List<Pago>> listarPendientes(@PathVariable Long prestamoId) {
        return ResponseEntity.ok(pagoService.listarPendientesPorPrestamo(prestamoId));
    }

    @GetMapping("/prestamo/{prestamoId}/realizados")
    public ResponseEntity<List<Pago>> listarRealizados(@PathVariable Long prestamoId) {
        return ResponseEntity.ok(pagoService.listarRealizadosPorPrestamo(prestamoId));
    }

    @GetMapping("/atrasados")
    public ResponseEntity<List<Pago>> listarAtrasados() {
        return ResponseEntity.ok(pagoService.listarAtrasados());
    }

    @GetMapping("/contar-pendientes")
    public ResponseEntity<Long> contarPendientes() {
        return ResponseEntity.ok(pagoService.contarPendientes());
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<?> marcarPagado(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pagoService.marcarPagoRealizado(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Pago pago) {
        try {
            return ResponseEntity.ok(pagoService.actualizarPago(id, pago));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            pagoService.eliminarPago(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

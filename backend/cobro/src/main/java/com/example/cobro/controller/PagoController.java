package com.example.cobro.controller;

import com.example.cobro.dto.PagoResponseDTO;
import com.example.cobro.model.Pago;
import com.example.cobro.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> listar() {
        return ResponseEntity.ok(pagoService.listarPagos().stream()
                .map(PagoResponseDTO::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> obtener(@PathVariable Long id) {
        return pagoService.obtenerPago(id)
                .map(p -> ResponseEntity.ok(PagoResponseDTO.fromEntity(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/prestamo/{prestamoId}")
    public ResponseEntity<List<PagoResponseDTO>> listarPorPrestamo(@PathVariable Long prestamoId) {
        return ResponseEntity.ok(pagoService.listarPorPrestamo(prestamoId).stream()
                .map(PagoResponseDTO::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/prestamo/{prestamoId}/pendientes")
    public ResponseEntity<List<PagoResponseDTO>> listarPendientes(@PathVariable Long prestamoId) {
        return ResponseEntity.ok(pagoService.listarPendientesPorPrestamo(prestamoId).stream()
                .map(PagoResponseDTO::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/prestamo/{prestamoId}/realizados")
    public ResponseEntity<List<PagoResponseDTO>> listarRealizados(@PathVariable Long prestamoId) {
        return ResponseEntity.ok(pagoService.listarRealizadosPorPrestamo(prestamoId).stream()
                .map(PagoResponseDTO::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/atrasados")
    public ResponseEntity<List<PagoResponseDTO>> listarAtrasados() {
        return ResponseEntity.ok(pagoService.listarAtrasados().stream()
                .map(PagoResponseDTO::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/contar-pendientes")
    public ResponseEntity<Long> contarPendientes() {
        return ResponseEntity.ok(pagoService.contarPendientes());
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<?> marcarPagado(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(PagoResponseDTO.fromEntity(pagoService.marcarPagoRealizado(id)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Pago pago) {
        try {
            return ResponseEntity.ok(PagoResponseDTO.fromEntity(pagoService.actualizarPago(id, pago)));
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

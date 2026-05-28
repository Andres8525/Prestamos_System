package com.example.cobro.controller;

import com.example.cobro.model.EstadoPago;
import com.example.cobro.model.Prestamo;
import com.example.cobro.model.TipoLiquidacion;
import com.example.cobro.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<?> crear(
            @RequestParam Long clienteId,
            @RequestParam Double monto,
            @RequestParam TipoLiquidacion tipoLiquidacion) {
        try {
            Prestamo prestamo = prestamoService.crearPrestamo(clienteId, monto, tipoLiquidacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(prestamo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Prestamo>> listar() {
        return ResponseEntity.ok(prestamoService.listarPrestamos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtener(@PathVariable Long id) {
        return prestamoService.obtenerPrestamo(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Prestamo>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(prestamoService.listarPorCliente(clienteId));
    }

    @GetMapping("/estado/al-dia")
    public ResponseEntity<List<Prestamo>> clientesAlDia() {
        return ResponseEntity.ok(prestamoService.listarPorEstado(EstadoPago.AL_DIA));
    }

    @GetMapping("/estado/pendientes")
    public ResponseEntity<List<Prestamo>> clientesPendientes() {
        return ResponseEntity.ok(prestamoService.listarPorEstado(EstadoPago.PENDIENTE));
    }

    @GetMapping("/estado/atrasados")
    public ResponseEntity<List<Prestamo>> clientesAtrasados() {
        return ResponseEntity.ok(prestamoService.listarPorEstado(EstadoPago.ATRASADO));
    }

    @GetMapping("/total-mes")
    public ResponseEntity<Double> totalMes(@RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(prestamoService.obtenerTotalMes(year, month));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Prestamo prestamo) {
        try {
            return ResponseEntity.ok(prestamoService.actualizarPrestamo(id, prestamo));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            prestamoService.eliminarPrestamo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/actualizar-estados")
    public ResponseEntity<Map<String, String>> actualizarEstados() {
        prestamoService.actualizarEstados();
        return ResponseEntity.ok(Map.of("mensaje", "Estados actualizados correctamente"));
    }
}

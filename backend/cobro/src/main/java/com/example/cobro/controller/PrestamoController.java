package com.example.cobro.controller;

import com.example.cobro.dto.PrestamoRequestDTO;
import com.example.cobro.dto.PrestamoResponseDTO;
import com.example.cobro.model.EstadoPago;
import com.example.cobro.model.Prestamo;
import com.example.cobro.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody PrestamoRequestDTO dto) {
        try {
            Prestamo prestamo = prestamoService.crearPrestamo(
                    dto.getClienteId(), dto.getMonto(), dto.getTipoLiquidacion());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(PrestamoResponseDTO.fromEntity(prestamo));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<PrestamoResponseDTO>> listar() {
        List<PrestamoResponseDTO> lista = prestamoService.listarPrestamos().stream()
                .map(PrestamoResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestamoResponseDTO> obtener(@PathVariable Long id) {
        return prestamoService.obtenerPrestamo(id)
                .map(p -> ResponseEntity.ok(PrestamoResponseDTO.fromEntity(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PrestamoResponseDTO>> listarPorCliente(@PathVariable Long clienteId) {
        List<PrestamoResponseDTO> lista = prestamoService.listarPorCliente(clienteId).stream()
                .map(PrestamoResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/estado/al-dia")
    public ResponseEntity<List<PrestamoResponseDTO>> clientesAlDia() {
        return ResponseEntity.ok(prestamoService.listarPorEstado(EstadoPago.AL_DIA).stream()
                .map(PrestamoResponseDTO::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/estado/pendientes")
    public ResponseEntity<List<PrestamoResponseDTO>> clientesPendientes() {
        return ResponseEntity.ok(prestamoService.listarPorEstado(EstadoPago.PENDIENTE).stream()
                .map(PrestamoResponseDTO::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/estado/atrasados")
    public ResponseEntity<List<PrestamoResponseDTO>> clientesAtrasados() {
        return ResponseEntity.ok(prestamoService.listarPorEstado(EstadoPago.ATRASADO).stream()
                .map(PrestamoResponseDTO::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/total-mes")
    public ResponseEntity<Double> totalMes(@RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(prestamoService.obtenerTotalMes(year, month));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Prestamo prestamo) {
        try {
            return ResponseEntity.ok(PrestamoResponseDTO.fromEntity(
                    prestamoService.actualizarPrestamo(id, prestamo)));
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

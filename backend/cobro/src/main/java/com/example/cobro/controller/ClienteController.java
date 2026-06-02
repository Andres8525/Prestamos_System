package com.example.cobro.controller;

import com.example.cobro.dto.ClienteRequestDTO;
import com.example.cobro.dto.ClienteResponseDTO;
import com.example.cobro.model.Cliente;
import com.example.cobro.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ClienteRequestDTO dto) {
        try {
            Cliente cliente = new Cliente();
            cliente.setCedula(dto.getCedula());
            cliente.setNombre(dto.getNombre());
            cliente.setDireccion(dto.getDireccion());
            cliente.setTelefono(dto.getTelefono());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ClienteResponseDTO.fromEntity(clienteService.crearCliente(cliente)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        List<ClienteResponseDTO> lista = clienteService.listarClientes().stream()
                .map(ClienteResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtener(@PathVariable Long id) {
        return clienteService.obtenerCliente(id)
                .map(c -> ResponseEntity.ok(ClienteResponseDTO.fromEntity(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<ClienteResponseDTO> obtenerPorCedula(@PathVariable String cedula) {
        return clienteService.obtenerClientePorCedula(cedula)
                .map(c -> ResponseEntity.ok(ClienteResponseDTO.fromEntity(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO dto) {
        try {
            Cliente datos = new Cliente();
            datos.setCedula(dto.getCedula());
            datos.setNombre(dto.getNombre());
            datos.setDireccion(dto.getDireccion());
            datos.setTelefono(dto.getTelefono());
            return ResponseEntity.ok(ClienteResponseDTO.fromEntity(clienteService.actualizarCliente(id, datos)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            clienteService.eliminarCliente(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> {
            String campo = ((FieldError) err).getField();
            errores.put(campo, err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}

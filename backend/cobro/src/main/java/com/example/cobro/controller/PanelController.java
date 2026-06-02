package com.example.cobro.controller;

import com.example.cobro.model.Panel;
import com.example.cobro.model.PanelItem;
import com.example.cobro.service.PanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/panel")
@CrossOrigin(origins = "*")
public class PanelController {

    @Autowired
    private PanelService panelService;

    @GetMapping
    public ResponseEntity<Panel> obtenerPanel() {
        return ResponseEntity.ok(panelService.generarPanel());
    }

    @GetMapping("/clientes-adeuda")
    public ResponseEntity<PanelItem> clientesAdeuda() {
        Panel panel = panelService.generarPanel();
        PanelItem item = panel.getClientesAdeuda();
        return item != null ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
    }

    @GetMapping("/clientes-pendientes")
    public ResponseEntity<PanelItem> clientesPendientes() {
        Panel panel = panelService.generarPanel();
        PanelItem item = panel.getClientesPendientes();
        return item != null ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
    }

    @GetMapping("/pagos-pendientes")
    public ResponseEntity<PanelItem> pagosPendientes() {
        Panel panel = panelService.generarPanel();
        PanelItem item = panel.getPagoPendientes();
        return item != null ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
    }

    @GetMapping("/resumen")
    public ResponseEntity<Double> resumen() {
        return ResponseEntity.ok(panelService.generarPanel().getResumen());
    }
}

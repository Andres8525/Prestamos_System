package com.example.cobro.service;

import com.example.cobro.model.*;
import com.example.cobro.repository.PagoRepository;
import com.example.cobro.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
public class PanelService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private PagoRepository pagoRepository;

    public Panel generarPanel() {
        Panel panel = new Panel();
        LocalDate hoy = LocalDate.now();

        long totalAtrasados = prestamoRepository.findByEstado(EstadoPago.ATRASADO).size();
        PanelItem itemAdeuda = new PanelItem(1L, "CLIENTES_ADEUDA", (double) totalAtrasados, "#FF5733");
        panel.getItems().add(itemAdeuda);

        long totalPendientes = prestamoRepository.findByEstado(EstadoPago.PENDIENTE).size();
        PanelItem itemPendientes = new PanelItem(2L, "CLIENTES_PENDIENTES", (double) totalPendientes, "#FFC107");
        panel.getItems().add(itemPendientes);

        long pagosPendientes = pagoRepository.countByEstado(EstadoPago.AL_DIA);
        PanelItem itemPagos = new PanelItem(3L, "PAGOS_PENDIENTES", (double) pagosPendientes, "#17A2B8");
        panel.getItems().add(itemPagos);

        Double totalMes = prestamoRepository.sumMontoTotalByMes(hoy.getYear(), hoy.getMonthValue());
        PanelItem itemTotal = new PanelItem(4L, "TOTAL_MES", totalMes != null ? totalMes : 0.0, "#28A745");
        panel.getItems().add(itemTotal);

        panel.cargarDatos();
        return panel;
    }
}

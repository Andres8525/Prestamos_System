package com.example.cobro.dto;

import com.example.cobro.model.TipoLiquidacion;

public class PrestamoRequestDTO {

    private Long clienteId;
    private Double monto;
    private TipoLiquidacion tipoLiquidacion;

    public PrestamoRequestDTO() {}

    public PrestamoRequestDTO(Long clienteId, Double monto, TipoLiquidacion tipoLiquidacion) {
        this.clienteId = clienteId;
        this.monto = monto;
        this.tipoLiquidacion = tipoLiquidacion;
    }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public TipoLiquidacion getTipoLiquidacion() { return tipoLiquidacion; }
    public void setTipoLiquidacion(TipoLiquidacion tipoLiquidacion) {
        this.tipoLiquidacion = tipoLiquidacion;
    }
}

export enum EstadoPago {
  AL_DIA = 'AL_DIA',
  PENDIENTE = 'PENDIENTE',
  ATRASADO = 'ATRASADO'
}

export enum TipoLiquidacion {
  DIARIO = 'DIARIO',
  SEMANAL = 'SEMANAL'
}

export interface Prestamo {
  id?: number;
  clienteId?: number;
  cliente?: any;
  monto: number;
  tasaInteres?: number;
  montoTotal?: number;
  tipoLiquidacion: TipoLiquidacion;
  fechaInicio?: string;
  fechaVencimiento?: string;
  estado: EstadoPago;
}

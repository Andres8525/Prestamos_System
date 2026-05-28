import { EstadoPago } from './prestamo.model';

export interface Pago {
  id?: number;
  prestamoId: number;
  monto: number;
  fechaProgramada: Date;
  fechaPago?: Date;
  estado: EstadoPago;
  numero: number;
}

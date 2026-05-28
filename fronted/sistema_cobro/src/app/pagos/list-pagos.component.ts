import { Component, OnInit } from '@angular/core';
import { PagoService } from '../services/pago.service';
import { PrestamoService } from '../services/prestamo.service';
import { Pago } from '../models/pago.model';
import { Prestamo } from '../models/prestamo.model';

@Component({
  selector: 'app-list-pagos',
  standalone: false,
  templateUrl: './list-pagos.component.html',
  styleUrls: ['./list-pagos.component.css']
})
export class ListPagosComponent implements OnInit {
  pagos: Pago[] = [];
  pagosAtrasados: Pago[] = [];
  prestamos: Prestamo[] = [];
  pagosPendientes: number = 0;
  mostraAtrasados: boolean = false;
  cargando: boolean = false;
  error: string = '';

  constructor(
    private pagoService: PagoService,
    private prestamoService: PrestamoService
  ) {}

  ngOnInit(): void {
    this.cargarPagos();
    this.cargarPagosAtrasados();
    this.cargarPagosPendientes();
    this.cargarPrestamos();
  }

  cargarPagos(): void {
    this.cargando = true;
    this.pagoService.obtenerPagos().subscribe({
      next: (data) => {
        this.pagos = data;
        this.cargando = false;
      },
      error: (err) => {
        this.error = 'Error al cargar los pagos';
        this.cargando = false;
        console.error(err);
      }
    });
  }

  cargarPagosAtrasados(): void {
    this.pagoService.obtenerPagosAtrasados().subscribe({
      next: (data) => {
        this.pagosAtrasados = data;
      },
      error: (err) => {
        console.error('Error al cargar pagos atrasados', err);
      }
    });
  }

  cargarPagosPendientes(): void {
    this.pagoService.contarPagosPendientes().subscribe({
      next: (data) => {
        this.pagosPendientes = data;
      },
      error: (err) => {
        console.error('Error al contar pagos pendientes', err);
      }
    });
  }

  cargarPrestamos(): void {
    this.prestamoService.obtenerPrestamos().subscribe({
      next: (data) => {
        this.prestamos = data;
      },
      error: (err) => {
        console.error('Error al cargar préstamos', err);
      }
    });
  }

  marcarPagado(id: number | undefined): void {
    if (!id) {
      return;
    }

    this.pagoService.marcarPagoRealizado(id).subscribe({
      next: () => {
        this.cargarPagos();
        this.cargarPagosAtrasados();
        this.cargarPagosPendientes();
      },
      error: (err) => {
        this.error = 'Error al marcar el pago';
        console.error(err);
      }
    });
  }

  eliminarPago(id: number | undefined): void {
    if (!id || !confirm('¿Está seguro de que desea eliminar este pago?')) {
      return;
    }

    this.pagoService.eliminarPago(id).subscribe({
      next: () => {
        this.cargarPagos();
        this.cargarPagosAtrasados();
        this.cargarPagosPendientes();
      },
      error: (err) => {
        this.error = 'Error al eliminar el pago';
        console.error(err);
      }
    });
  }

  toggleAtrasados(): void {
    this.mostraAtrasados = !this.mostraAtrasados;
  }

  getEstadoClass(estado: string): string {
    return `estado-${estado.toLowerCase()}`;
  }

  getNombrePrestamoCliente(prestamoId: number): string {
    const prestamo = this.prestamos.find(p => p.id === prestamoId);
    if (prestamo) {
      return `ID: ${prestamoId} - Monto: ${prestamo.monto}`;
    }
    return `ID: ${prestamoId}`;
  }
}

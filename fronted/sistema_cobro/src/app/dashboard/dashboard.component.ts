import { Component, OnInit } from '@angular/core';
import { PrestamoService } from '../services/prestamo.service';
import { PagoService } from '../services/pago.service';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  clientesAlDia: any[] = [];
  clientesPendientes: any[] = [];
  clientesAtrasados: any[] = [];
  pagosPendientes: number = 0;
  cargando: boolean = false;
  error: string = '';

  constructor(
    private prestamoService: PrestamoService,
    private pagoService: PagoService
  ) {}

  ngOnInit(): void {
    this.cargarDashboard();
  }

  cargarDashboard(): void {
    this.cargando = true;
    this.prestamoService.obtenerClientesAlDia().subscribe({
      next: (data) => {
        this.clientesAlDia = data;
      },
      error: (err) => {
        this.error = 'Error al cargar datos';
        console.error(err);
      }
    });

    this.prestamoService.obtenerClientesPendientes().subscribe({
      next: (data) => {
        this.clientesPendientes = data;
      },
      error: (err) => console.error(err)
    });

    this.prestamoService.obtenerClientesAtrasados().subscribe({
      next: (data) => {
        this.clientesAtrasados = data;
      },
      error: (err) => console.error(err)
    });

    this.pagoService.contarPagosPendientes().subscribe({
      next: (data) => {
        this.pagosPendientes = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error(err);
        this.cargando = false;
      }
    });
  }

  getTotalClientes(): number {
    return this.clientesAlDia.length + this.clientesPendientes.length + this.clientesAtrasados.length;
  }
}

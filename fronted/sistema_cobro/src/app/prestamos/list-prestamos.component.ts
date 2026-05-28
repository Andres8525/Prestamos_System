import { Component, OnInit } from '@angular/core';
import { PrestamoService } from '../services/prestamo.service';
import { ClienteService } from '../services/cliente.service';
import { Prestamo, TipoLiquidacion } from '../models/prestamo.model';
import { Cliente } from '../models/cliente.model';

@Component({
  selector: 'app-list-prestamos',
  standalone: false,
  templateUrl: './list-prestamos.component.html',
  styleUrls: ['./list-prestamos.component.css']
})
export class ListPrestamosComponent implements OnInit {
  prestamos: Prestamo[] = [];
  clientes: Cliente[] = [];
  nuevoPresta: Partial<Prestamo> = {
    clienteId: undefined,
    monto: undefined,
    tipoLiquidacion: TipoLiquidacion.DIARIO
  };
  mostraFormulario: boolean = false;
  cargando: boolean = false;
  error: string = '';
  tiposLiquidacion = Object.values(TipoLiquidacion) as TipoLiquidacion[];

  constructor(
    private prestamoService: PrestamoService,
    private clienteService: ClienteService
  ) {}

  ngOnInit(): void {
    this.cargarPrestamos();
    this.cargarClientes();
  }

  cargarPrestamos(): void {
    this.cargando = true;
    this.prestamoService.obtenerPrestamos().subscribe({
      next: (data) => {
        this.prestamos = data;
        this.cargando = false;
      },
      error: (err) => {
        this.error = 'Error al cargar los préstamos';
        this.cargando = false;
        console.error(err);
      }
    });
  }

  cargarClientes(): void {
    this.clienteService.obtenerClientes().subscribe({
      next: (data) => {
        this.clientes = data;
      },
      error: (err) => {
        console.error('Error al cargar clientes', err);
      }
    });
  }

  toggleFormulario(): void {
    this.mostraFormulario = !this.mostraFormulario;
    if (!this.mostraFormulario) {
      this.limpiarFormulario();
    }
  }

  guardarPrestamo(): void {
    if (!this.validarFormulario()) {
      return;
    }

    if (this.nuevoPresta.clienteId && this.nuevoPresta.monto && this.nuevoPresta.tipoLiquidacion) {
      this.prestamoService
        .crearPrestamo(
          this.nuevoPresta.clienteId,
          this.nuevoPresta.monto,
          this.nuevoPresta.tipoLiquidacion
        )
        .subscribe({
          next: () => {
            this.cargarPrestamos();
            this.limpiarFormulario();
            this.mostraFormulario = false;
          },
          error: (err) => {
            this.error = 'Error al crear el préstamo';
            console.error(err);
          }
        });
    }
  }

  eliminarPrestamo(id: number | undefined): void {
    if (!id || !confirm('¿Está seguro de que desea eliminar este préstamo?')) {
      return;
    }

    this.prestamoService.eliminarPrestamo(id).subscribe({
      next: () => {
        this.cargarPrestamos();
      },
      error: (err) => {
        this.error = 'Error al eliminar el préstamo';
        console.error(err);
      }
    });
  }

  actualizarEstados(): void {
    this.prestamoService.actualizarEstados().subscribe({
      next: () => {
        this.cargarPrestamos();
      },
      error: (err) => {
        this.error = 'Error al actualizar estados';
        console.error(err);
      }
    });
  }

  validarFormulario(): boolean {
    if (!this.nuevoPresta.clienteId || !this.nuevoPresta.monto || !this.nuevoPresta.tipoLiquidacion) {
      this.error = 'Por favor complete todos los campos';
      return false;
    }
    this.error = '';
    return true;
  }

  limpiarFormulario(): void {
    this.nuevoPresta = {
      clienteId: undefined,
      monto: undefined,
      tipoLiquidacion: TipoLiquidacion.DIARIO
    };
    this.error = '';
  }

  obtenerNombreCliente(clienteId: number | undefined): string {
    if (!clienteId) return 'Desconocido';
    const cliente = this.clientes.find(c => c.id === clienteId);
    return cliente ? cliente.nombre : 'Desconocido';
  }

  getEstadoClass(estado: string): string {
    return `estado-${estado.toLowerCase()}`;
  }
}

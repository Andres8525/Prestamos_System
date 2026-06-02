import { Component, OnInit } from '@angular/core';
import { ClienteService } from '../services/cliente.service';
import { Cliente } from '../models/cliente.model';

@Component({
  selector: 'app-list-clientes',
  standalone: false,
  templateUrl: './list-clientes.component.html',
  styleUrls: ['./list-clientes.component.css']
})
export class ListClientesComponent implements OnInit {
  clientes: Cliente[] = [];
  nuevoCliente: Cliente = { cedula: '', nombre: '', direccion: '', telefono: '' };
  mostraFormulario = false;
  clienteEnEdicion: Cliente | null = null;
  cargando = false;
  error = '';

  constructor(private clienteService: ClienteService) {}

  ngOnInit(): void {
    this.cargarClientes();
  }

  cargarClientes(): void {
    this.cargando = true;
    this.clienteService.obtenerClientes().subscribe({
      next: (data) => {
        this.clientes = data;
        this.cargando = false;
      },
      error: (err) => {
        this.error = 'No se pudo conectar con el servidor. Verifique que el backend esté activo.';
        this.cargando = false;
        console.error(err);
      }
    });
  }

  toggleFormulario(): void {
    this.mostraFormulario = !this.mostraFormulario;
    if (!this.mostraFormulario) {
      this.limpiarFormulario();
    }
  }

  guardarCliente(): void {
    if (!this.validarFormulario()) return;
    this.clienteEnEdicion ? this.actualizarCliente() : this.crearCliente();
  }

  crearCliente(): void {
    this.clienteService.crearCliente(this.nuevoCliente).subscribe({
      next: () => {
        this.cargarClientes();
        this.limpiarFormulario();
        this.mostraFormulario = false;
      },
      error: (err) => {
        this.error = this.extraerMensajeError(err, 'Error al crear el cliente');
        console.error(err);
      }
    });
  }

  editarCliente(cliente: Cliente): void {
    this.clienteEnEdicion = cliente;
    this.nuevoCliente = { ...cliente };
    this.mostraFormulario = true;
  }

  actualizarCliente(): void {
    if (!this.clienteEnEdicion?.id) return;
    this.clienteService.actualizarCliente(this.clienteEnEdicion.id, this.nuevoCliente).subscribe({
      next: () => {
        this.cargarClientes();
        this.limpiarFormulario();
        this.mostraFormulario = false;
      },
      error: (err) => {
        this.error = this.extraerMensajeError(err, 'Error al actualizar el cliente');
        console.error(err);
      }
    });
  }

  eliminarCliente(id: number | undefined): void {
    if (!id || !confirm('¿Está seguro de que desea eliminar este cliente?')) return;
    this.clienteService.eliminarCliente(id).subscribe({
      next: () => this.cargarClientes(),
      error: (err) => {
        this.error = this.extraerMensajeError(err, 'Error al eliminar el cliente');
        console.error(err);
      }
    });
  }

  validarFormulario(): boolean {
    if (!this.nuevoCliente.cedula.trim()) { this.error = 'La cédula es obligatoria'; return false; }
    if (!this.nuevoCliente.nombre.trim()) { this.error = 'El nombre es obligatorio'; return false; }
    if (!this.nuevoCliente.direccion.trim()) { this.error = 'La dirección es obligatoria'; return false; }
    if (!this.nuevoCliente.telefono.trim()) { this.error = 'El teléfono es obligatorio'; return false; }
    this.error = '';
    return true;
  }

  limpiarFormulario(): void {
    this.nuevoCliente = { cedula: '', nombre: '', direccion: '', telefono: '' };
    this.clienteEnEdicion = null;
    this.error = '';
  }

  private extraerMensajeError(err: any, fallback: string): string {
    if (err?.error) {
      // { "error": "mensaje" }  →  mensaje directo
      if (typeof err.error === 'string') return err.error;
      if (err.error.error) return err.error.error;
      // { "campo": "mensaje", ... }  →  primer campo con error
      const campos = Object.entries(err.error as Record<string, string>);
      if (campos.length > 0) return campos.map(([c, m]) => `${c}: ${m}`).join(' | ');
    }
    return fallback;
  }
}

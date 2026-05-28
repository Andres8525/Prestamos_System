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
  nuevoCliente: Cliente = {
    cedula: '',
    nombre: '',
    direccion: '',
    telefono: ''
  };
  mostraFormulario: boolean = false;
  clienteEnEdicion: Cliente | null = null;
  cargando: boolean = false;
  error: string = '';

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
        this.error = 'Error al cargar los clientes';
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
    if (!this.validarFormulario()) {
      return;
    }

    if (this.clienteEnEdicion) {
      this.actualizarCliente();
    } else {
      this.crearCliente();
    }
  }

  crearCliente(): void {
    this.clienteService.crearCliente(this.nuevoCliente).subscribe({
      next: () => {
        this.cargarClientes();
        this.limpiarFormulario();
        this.mostraFormulario = false;
      },
      error: (err) => {
        this.error = 'Error al crear el cliente';
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
    if (this.clienteEnEdicion && this.clienteEnEdicion.id) {
      this.clienteService.actualizarCliente(this.clienteEnEdicion.id, this.nuevoCliente).subscribe({
        next: () => {
          this.cargarClientes();
          this.limpiarFormulario();
          this.mostraFormulario = false;
        },
        error: (err) => {
          this.error = 'Error al actualizar el cliente';
          console.error(err);
        }
      });
    }
  }

  eliminarCliente(id: number | undefined): void {
    if (!id || !confirm('¿Está seguro de que desea eliminar este cliente?')) {
      return;
    }

    this.clienteService.eliminarCliente(id).subscribe({
      next: () => {
        this.cargarClientes();
      },
      error: (err) => {
        this.error = 'Error al eliminar el cliente';
        console.error(err);
      }
    });
  }

  validarFormulario(): boolean {
    if (!this.nuevoCliente.cedula || !this.nuevoCliente.nombre || !this.nuevoCliente.direccion || !this.nuevoCliente.telefono) {
      this.error = 'Por favor complete todos los campos';
      return false;
    }
    this.error = '';
    return true;
  }

  limpiarFormulario(): void {
    this.nuevoCliente = {
      cedula: '',
      nombre: '',
      direccion: '',
      telefono: ''
    };
    this.clienteEnEdicion = null;
    this.error = '';
  }
}

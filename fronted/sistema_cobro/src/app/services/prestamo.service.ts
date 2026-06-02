import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Prestamo } from '../models/prestamo.model';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PrestamoService {
  private apiUrl = `${environment.apiUrl}/prestamos`;

  constructor(private http: HttpClient) {}

  obtenerPrestamos(): Observable<Prestamo[]> {
    return this.http.get<Prestamo[]>(this.apiUrl);
  }

  obtenerPrestamoPorId(id: number): Observable<Prestamo> {
    return this.http.get<Prestamo>(`${this.apiUrl}/${id}`);
  }

  obtenerPrestamosPorCliente(clienteId: number): Observable<Prestamo[]> {
    return this.http.get<Prestamo[]>(`${this.apiUrl}/cliente/${clienteId}`);
  }

  obtenerClientesAlDia(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/estado/al-dia`);
  }

  obtenerClientesPendientes(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/estado/pendientes`);
  }

  obtenerClientesAtrasados(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/estado/atrasados`);
  }

  obtenerTotalMes(year: number, month: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/total-mes?year=${year}&month=${month}`);
  }

  crearPrestamo(clienteId: number, monto: number, tipoLiquidacion: string): Observable<Prestamo> {
    return this.http.post<Prestamo>(this.apiUrl, { clienteId, monto, tipoLiquidacion });
  }

  actualizarPrestamo(id: number, prestamo: Prestamo): Observable<Prestamo> {
    return this.http.put<Prestamo>(`${this.apiUrl}/${id}`, prestamo);
  }

  eliminarPrestamo(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  actualizarEstados(): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/actualizar-estados`, {});
  }
}

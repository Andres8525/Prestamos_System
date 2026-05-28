import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pago } from '../models/pago.model';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PagoService {
  private apiUrl = `${environment.apiUrl}/pagos`;

  constructor(private http: HttpClient) {}

  obtenerPagos(): Observable<Pago[]> {
    return this.http.get<Pago[]>(this.apiUrl);
  }

  obtenerPagoPorId(id: number): Observable<Pago> {
    return this.http.get<Pago>(`${this.apiUrl}/${id}`);
  }

  obtenerPagosPorPrestamo(prestamoId: number): Observable<Pago[]> {
    return this.http.get<Pago[]>(`${this.apiUrl}/prestamo/${prestamoId}`);
  }

  obtenerPagosPendientes(prestamoId: number): Observable<Pago[]> {
    return this.http.get<Pago[]>(`${this.apiUrl}/prestamo/${prestamoId}/pendientes`);
  }

  obtenerPagosRealizados(prestamoId: number): Observable<Pago[]> {
    return this.http.get<Pago[]>(`${this.apiUrl}/prestamo/${prestamoId}/realizados`);
  }

  obtenerPagosAtrasados(): Observable<Pago[]> {
    return this.http.get<Pago[]>(`${this.apiUrl}/atrasados`);
  }

  contarPagosPendientes(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/contar-pendientes`);
  }

  marcarPagoRealizado(id: number): Observable<Pago> {
    return this.http.post<Pago>(`${this.apiUrl}/${id}/pagar`, {});
  }

  actualizarPago(id: number, pago: Pago): Observable<Pago> {
    return this.http.put<Pago>(`${this.apiUrl}/${id}`, pago);
  }

  eliminarPago(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

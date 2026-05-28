import { Injectable } from '@angular/core';

export interface MenuItem {
  label: string;
  route: string;
  icon?: string;
}

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  private menuItems: MenuItem[] = [
    { label: 'Dashboard', route: '/dashboard', icon: '📊' },
    { label: 'Clientes', route: '/clientes', icon: '👥' },
    { label: 'Préstamos', route: '/prestamos', icon: '💰' },
    { label: 'Pagos', route: '/pagos', icon: '💳' }
  ];

  obtenerMenuItems(): MenuItem[] {
    return this.menuItems;
  }
}

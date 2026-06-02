import { Injectable } from '@angular/core';

export interface MenuItem {
  label: string;
  route: string;
  icon: string;
}

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  private menuItems: MenuItem[] = [
    { label: 'Dashboard', route: '/dashboard', icon: 'dashboard' },
    { label: 'Clientes',  route: '/clientes',  icon: 'clients'   },
    { label: 'Préstamos', route: '/prestamos', icon: 'loans'     },
    { label: 'Pagos',     route: '/pagos',     icon: 'payments'  }
  ];

  obtenerMenuItems(): MenuItem[] {
    return this.menuItems;
  }
}

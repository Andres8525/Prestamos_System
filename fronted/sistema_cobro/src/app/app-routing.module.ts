import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ListClientesComponent } from './clientes/list-clientes.component';
import { ListPrestamosComponent } from './prestamos/list-prestamos.component';
import { ListPagosComponent } from './pagos/list-pagos.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'clientes', component: ListClientesComponent },
  { path: 'prestamos', component: ListPrestamosComponent },
  { path: 'pagos', component: ListPagosComponent },
  { path: '**', redirectTo: '/dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}

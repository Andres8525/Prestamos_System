import { Component, OnInit } from '@angular/core';
import { MenuService, MenuItem } from '../../services/menu.service';
import { CommonModule } from '@angular/common';
import { MenuItemComponent } from '../menu-item/menu-item.component';

@Component({
  selector: 'app-nav-menu',
  standalone: true,
  imports: [CommonModule, MenuItemComponent],
  templateUrl: './nav-menu.component.html',
  styleUrls: ['./nav-menu.component.css']
})
export class NavMenuComponent implements OnInit {
  menuItems: MenuItem[] = [];

  constructor(private menuService: MenuService) {}

  ngOnInit(): void {
    this.menuItems = this.menuService.obtenerMenuItems();
  }
}

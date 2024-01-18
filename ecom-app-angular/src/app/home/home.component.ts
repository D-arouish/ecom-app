import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  totalClients: number = 123; // Example data
  totalProducts: number = 456; // Example data
  totalSales: number = 789; // Example data

  constructor(){}


}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomersComponent} from "./customers/customers.component";
import {ProductsComponent} from "./products/products.component";
import {SalesComponent} from "./sales/sales.component";
import {HomeComponent} from "./home/home.component";
import {AuthGuard} from "./guards/auth.guard";

const routes: Routes = [
  {path : "customers" , component : CustomersComponent,canActivate:[AuthGuard], data:{roles: ['ADMIN']}},
  {path : "products" , component : ProductsComponent, canActivate:[AuthGuard], data:{roles: ['ADMIN']}},
  {path : "sales" , component : SalesComponent,canActivate:[AuthGuard], data:{roles: ['ADMIN']}},
  {path : "" , component : HomeComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

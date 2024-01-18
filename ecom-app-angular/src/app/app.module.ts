import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {CommonModule} from "@angular/common";
import {RouterLink, RouterOutlet} from "@angular/router";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {ProductsComponent} from "./products/products.component";
import {CustomersComponent} from "./customers/customers.component";
import {SalesComponent} from "./sales/sales.component";
import {FormsModule} from "@angular/forms";
import {HomeComponent} from "./home/home.component";
import {HttpClientModule} from "@angular/common/http";


function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8080',
        realm: 'ecom-app',
        clientId: 'iao-ecom-client'
      },
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html'
      }
    });
}

@NgModule({
  declarations: [
    AppComponent,
    ProductsComponent,
    SalesComponent,
    CustomersComponent,
    HomeComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    RouterOutlet,
    RouterLink,
    KeycloakAngularModule,
    FormsModule,
    HttpClientModule,

  ],
  providers: [{provide: APP_INITIALIZER, deps : [KeycloakService],useFactory :initializeKeycloak, multi:true}],
  bootstrap: [AppComponent]
})
export class AppModule { }

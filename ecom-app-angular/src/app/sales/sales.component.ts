// sales.component.ts

import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpClientModule } from "@angular/common/http";
import { DatePipe, NgForOf, NgIf } from "@angular/common";
import { ProductItem } from "../model/productItem.model";
import { Vente } from "../model/vente.model";
import {FormsModule} from "@angular/forms";
import {Customer} from "../model/customer.model";
import {Product} from "../model/product.model";

@Component({
  selector: 'app-sales',
  templateUrl: './sales.component.html',
  styleUrls: ['./sales.component.css']
})
export class SalesComponent implements OnInit {
  ventes!: Array<Vente>;
  selectedVente: Vente | null = null;
  showAddSaleForm: boolean = false;
  newProductQuantity!: number;
  selectedProducts: any[] = [];
  products: Product[] = [];
  selectedCustomer!: Customer;
  customers: Customer[] = [];
  productQuantities: { [productId: number]: number } = {};
  productDiscounts: { [productId: number]: number } = {};
  currentDate: Date = new Date();



  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.getVentes();
    this.getCustomers();
    this.getProducts();
  }



  private getVentes() {
    this.http.get<Array<Vente>>("http://localhost:8888/VENTES-SERVICE/sales")
      .subscribe({
        next: data => {
          this.ventes = data;
        },
        error: err => console.error('Error fetching data:', err)
      });
  }

  private getCustomers() {
    this.http.get<any>("http://localhost:8888/CUSTOMER-SERVICE/api/customers")
      .subscribe({
        next: data => {
          this.customers = data;
        },
        error: err => console.error('Error fetching customers:', err)
      });
  }

  private getProducts() {
    this.http.get<any>("http://localhost:8888/PRODUCT-SERVICE/api/products")
      .subscribe({
        next: data => {
          this.products = data;
        },
        error: err => console.error('Error fetching products:', err)
      });
  }

  showProductItems(vente: Vente): void {
    // Set the selected sale when a row is clicked
    this.selectedVente = this.selectedVente === vente ? null : vente;
  }

  handleDelete(v: Vente) {
    if (confirm("think again !"))
        this.http.delete<Vente>(`http://localhost:8888/VENTES-SERVICE/sales/${v.id}`)
          .subscribe({
            next: () => {
              this.getVentes();
              this.selectedVente = null;
            }

          });
  }

  handleDeleteProduct(item: ProductItem) {
    if (confirm("are you sure"))
      this.http.delete<ProductItem>(`http://localhost:8888/VENTES-SERVICE/productItem/${item.id}`)
        .subscribe({
          next: () => this.getVentes(),
          error: err => console.error('Error fetching data:', err)
        });
  }

  toggleAddSaleForm() {
    this.showAddSaleForm = !this.showAddSaleForm;
    // Clear the input fields when toggling the form
    this.newProductQuantity = 0;
    this.selectedProducts = [];
  }

  addProductToSale() {
    // Check if newProductQuantity, selectedCustomer, and selectedProducts are not null or undefined
    if (this.selectedCustomer && this.selectedCustomer.id && this.selectedProducts.length > 0) {

      const newProductItems : ProductItem[] = this.selectedProducts.map(product => {
        return {
          id : 2,
          productId: product.id,
          quantity: this.productQuantities[product.id] || 1, // Default quantity to 1 if not specified
          price: product.price * this.newProductQuantity,
          discount: this.productDiscounts[product.id] || 0,
          product: product,
        };
      });

      const customer:Customer = {
        id: 1,
        email : "",
        name : "",
      }


      const newSale: Vente = {
        id : 4,
        customerId: this.selectedCustomer.id,
        customer: customer,
        productItems: newProductItems,
        venteDate: this.currentDate,
      };
      console.log(newSale);


      this.http.post<any>('http://localhost:8888/VENTES-SERVICE/sales', newSale)
        .subscribe({
          next: () => {
            this.showAddSaleForm = false;
            this.getVentes();
           // Hide the form after adding a sale
          },
          error: () => {
              this.showAddSaleForm = false;
              this.getVentes();
          }
        });
    } else {
      // Handle the case where newProductQuantity, selectedCustomer, or selectedProducts is null or undefined
      alert(`Please fill in all required fields before submitting the form. ${this.selectedCustomer.id}`);
      // You may want to provide user feedback or take appropriate action
    }
  }



}

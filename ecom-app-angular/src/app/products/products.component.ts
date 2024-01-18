import {Component, OnInit} from '@angular/core';
import {HttpClient, } from "@angular/common/http";
import {Product} from "../model/product.model";
import {Customer} from "../model/customer.model";


@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit{
  products!: Array<Product>;
    showAddProductForm: boolean = false;
    newProductName!: string ;  // Add any other properties you need for the new customer
    newProductPrice!: number ;
    newProductQuantity!:number;
    showEditedProductForm: boolean = false;
    editedProductName!: string;
    editedProductPrice!: number;
    editedProductQuantity!: number;
    productId!:number | undefined;
  constructor(private http:HttpClient) {
  }

  ngOnInit(): void {
    this.getProducts();
  }

  handleDelete(product: Product) {
    if (confirm("are you sure ?"))
    this.http.delete<Product>(`http://localhost:8888/PRODUCT-SERVICE/api/products/${product.id}`)
      .subscribe({
          next : () => {this.getProducts();}
        }
      )
  }






  private getProducts() {
      this.http.get<any>("http://localhost:8888/PRODUCT-SERVICE/api/products")
      .subscribe({
        next: data => {
            this.products = data;
        },
        error: err => {
          console.error('Error fetching data:', err);
        }
      });
  }

    addProduct() {
        // Check if newCustomerName and newCustomerEmail are not null or undefined
        if (this.newProductName && this.newProductQuantity && this.newProductPrice) {
            const newProduct: Product = {
                name: this.newProductName,
                price: this.newProductPrice,
                quantity:this.newProductQuantity,
            };

            this.http.post<Customer>('http://localhost:8888/PRODUCT-SERVICE/api/products', newProduct)
                .subscribe({
                    next: () => {
                        this.getProducts();
                        this.toggleAddProductForm(); // Hide the form after adding a customer
                    },
                    error: () => {
                        alert('Error adding Product:');
                    }
                });
        } else {
            // Handle the case where newCustomerName or newCustomerEmail is null or undefined
            alert('Please fill in all required fields before submitting the form.');
            // You may want to provide user feedback or take appropriate action
        }
    }

    editProduct() {
        if (this.editedProductName && this.editedProductPrice && this.editedProductQuantity) {
            const editedProduct: Product = {
                id:this.productId,
                name:this.editedProductName,
                price:this.editedProductPrice,
                quantity:this.editedProductQuantity
            };


            this.http.put<Product>(`http://localhost:8888/PRODUCT-SERVICE/api/products/${editedProduct.id}`, editedProduct)
                .subscribe({
                    error: err => {
                        console.error('Error adding Product:', err);
                    },
                    next: () => {
                        this.getProducts();
                        this.toggleEditForm(editedProduct); // Hide the form after adding a customer
                    }
                });
        } else {
            // Handle the case where newCustomerName or newCustomerEmail is null or undefined
            alert('Please fill in all required fields before submitting the form.');
        }}

    toggleEditForm(product: Product) {
        this.showEditedProductForm = !this.showEditedProductForm;
        // Clear the input fields when toggling the form
        this.productId = product.id;
        this.editedProductName = product.name;
        this.editedProductPrice = product.price;
        this.editedProductQuantity = product.quantity;
    }


    cancelEdit() {
        this.showEditedProductForm = false;
    }

    toggleAddProductForm() {

      this.showAddProductForm = !this.showAddProductForm;
      this.newProductName = '';
      this.newProductPrice=0;
      this.newProductQuantity=0;
    }

    cancelAdd() {
        this.showAddProductForm = false;
    }
}

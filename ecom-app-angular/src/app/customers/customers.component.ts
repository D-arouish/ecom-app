import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {Customer} from "../model/customer.model";


@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit{

  customers: Array<Customer> = [];
  showAddCustomerForm: boolean = false;
  newCustomerName!: string ;  // Add any other properties you need for the new customer
  newCustomerEmail!: string ;
  editedCustomerName!: string;
  editedCustomerEmail!: string;
  showEditedCustomerForm: boolean = false;
  customerId: number | undefined;
  constructor(private http: HttpClient) {
  }
  ngOnInit(): void {
    this.getCustomers();
  }

    private getCustomers() {
        this.http.get<any>("http://localhost:8888/CUSTOMER-SERVICE/api/customers")
            .subscribe({
                next: data => {
                        this.customers = data;
                },
                error: err => {
                    console.error('Error fetching data:', err);
                }
            });
    }

    handleDelete(customer: Customer) {
      if (confirm("Are you sure ?"))
        if (confirm("think again !"))
        this.http.delete<Customer>(`http://localhost:8888/CUSTOMER-SERVICE/api/customers/${customer.id}`)
            .subscribe({
                next : () => {this.getCustomers();}
                }
            )
    }

  addCustomer() {
    // Check if newCustomerName and newCustomerEmail are not null or undefined
    if (this.newCustomerName && this.newCustomerEmail) {
      const newCustomer: Customer = {
        name: this.newCustomerName,
        email: this.newCustomerEmail
      };

      this.http.post<Customer>('http://localhost:8888/CUSTOMER-SERVICE/api/customers', newCustomer)
          .subscribe({
            next: () => {
              this.getCustomers();
              this.toggleAddCustomerForm(); // Hide the form after adding a customer
            },
            error: err => {
              console.error('Error adding customer:', err);
            }
          });
    } else {
      // Handle the case where newCustomerName or newCustomerEmail is null or undefined
      alert('Please fill in all required fields before submitting the form.');
      // You may want to provide user feedback or take appropriate action
    }
  }


  toggleAddCustomerForm() {
    this.showAddCustomerForm = !this.showAddCustomerForm;
    // Clear the input fields when toggling the form
    this.newCustomerName = '';
    this.newCustomerEmail = '';
  }


  editCustomer() {
    if (this.editedCustomerName && this.editedCustomerEmail) {
      const editedCustomer: Customer = {
        id:this.customerId,
        name: this.editedCustomerName,
        email: this.editedCustomerEmail
      };


    this.http.put<Customer>(`http://localhost:8888/CUSTOMER-SERVICE/api/customers/${editedCustomer.id}`, editedCustomer)
        .subscribe({
          error: err => {
            console.error('Error adding customer:', err);
          },
          next: () => {
            this.getCustomers();
            this.toggleEditForm(editedCustomer); // Hide the form after adding a customer
          }
        });
  } else {
  // Handle the case where newCustomerName or newCustomerEmail is null or undefined
  alert('Please fill in all required fields before submitting the form.');
  // You may want to provide user feedback or take appropriate action
}}


  toggleEditForm(customer: Customer) {
    this.showEditedCustomerForm = !this.showEditedCustomerForm;
    // Clear the input fields when toggling the form
    this.customerId = customer.id;
    this.editedCustomerName = customer.name;
    this.editedCustomerEmail = customer.email;

  }

  cancelEdit() {
    this.showEditedCustomerForm = false;
  }

  cancelAdd(){
    this.showAddCustomerForm = false;
  }
}

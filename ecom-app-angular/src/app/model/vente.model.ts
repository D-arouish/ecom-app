import {Customer} from "./customer.model";
import {ProductItem} from "./productItem.model";

export interface Vente {
  id?:           number;
  customerId:   number;
  productItems: ProductItem[];
  customer?:     Customer;
  venteDate?:    Date;
}

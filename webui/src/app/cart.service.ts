import {Product} from './products';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

export class CartService {

  items: Product[] = [];

  constructor(private http: HttpClient) {
  }

  printCart() {
    console.log(this.items)
  }

  addToCart(product: Product) {
    this.items.push(product);
  }

  getItems() {
    return this.items;
  }

  clearCart() {
    this.items = [];
    return this.items;
  }

  getShippingPrices() {
    // const url = "/app/auth/signin"
    const url = "http://localhost/app/auth/signin"
    const body = {
      "username": "admin",
      "password": "password"
    }
    return this.http.post<any>(url, body)
    // return  this.http.get(url)
    // console.log(ret)
    // return this.http.get<{ type: string, price: number }[]>('/assets/shipping.json');
  }

}

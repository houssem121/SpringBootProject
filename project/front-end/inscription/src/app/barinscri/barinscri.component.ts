import { Component } from '@angular/core';
import { AuthService } from '../Services/auth.service';

@Component({
  selector: 'app-barinscri',
  templateUrl: './barinscri.component.html',
  styleUrls: ['./barinscri.component.css']
})
export class BarinscriComponent {
 // User Status
 isLoggedIn!: boolean;
 constructor(private authService : AuthService){}
 ngOnInit(): void {
   // check if the token exist in session storage
   this.isLoggedIn = !!this.authService.getToken();
 }
//get user Email Method
getUserName() {
 return this.authService.getUser();
}
//Method to logout
signOut() {
 this.authService.signOut();
}
}

import { Component, ViewChild } from '@angular/core';
import { AuthService } from '../Services/auth.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  
  constructor(public authService: AuthService,
    public router: Router,private jwtHelper: JwtHelperService ) { }

  
  goToPage2() {
    const jwtToken = this.authService.getToken();
    if (jwtToken || this.jwtHelper.isTokenExpired(jwtToken)) {
      this.router.navigateByUrl('/page2');
    } else {
      alert('Please log in to go to the other page.');
    }
  }
  datashow:boolean=false;
 datago()
  {
    this.datashow=!this.datashow;
  }
}

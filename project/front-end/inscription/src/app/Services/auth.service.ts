import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import jwt_decode from 'jwt-decode';
import { Observable } from 'rxjs';

const TOKEN_KEY = 'TOKEN_KEY';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private router : Router) {}
 idpay : string = '';
  public signIn(){}
  
  public saveid(id : string): void {
    window.sessionStorage.removeItem('idpay');
    window.sessionStorage.setItem('idpay', id);
  }
  public getid(): string | null {
    return window.sessionStorage.getItem('idpay') !== null ? window.sessionStorage.getItem('idpay') : null;
  }

  public saveToken(token : string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }
  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY) !== null ? window.sessionStorage.getItem(TOKEN_KEY) : null;
  }
  public getUser():string | null{
    const jwtToken = this.getToken();
      const decodedToken: any = this.getToken() != null ? jwt_decode(jwtToken as string) : null;
      const userId = decodedToken != null ? decodedToken?.sub : null;
    return userId;
  }


    signOut(): void {
      window.sessionStorage.clear();
      this.router.navigate(['/inscription'])
      .then(() => {
        window.location.reload();
      });
    }
  }
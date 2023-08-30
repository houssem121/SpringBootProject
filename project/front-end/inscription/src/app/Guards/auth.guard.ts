import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../Services/auth.service';

import { JwtHelperService } from '@auth0/angular-jwt';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(
    public authService: AuthService,
    public router: Router,

    private jwtHelper: JwtHelperService
  ) {}


  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    const jwtToken = this.authService.getToken();
    const decodedToken: any =
      this.authService.getToken() != null
        ? jwt_decode(jwtToken as string)
        : null;
    const userRole = decodedToken != null ? decodedToken.Role : null;

    if (!jwtToken || this.jwtHelper.isTokenExpired(jwtToken)) {
      // Check if the token is missing or expired
      if (this.jwtHelper.isTokenExpired(this.authService.getToken())) {
       
        this.authService.signOut();
        this.router.navigate(['/inscription'],
         {
          queryParams: { returnUrl: state.url },
        });
      } else {

        this.router.navigate(['/inscription'], {
          queryParams: { returnUrl: state.url },
        });
      }
    } 
   
    return true;
  }
}
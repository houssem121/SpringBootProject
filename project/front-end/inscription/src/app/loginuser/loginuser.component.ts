import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../Services/auth.service';
import { InscriptionService } from '../inscription.service';
import { AuthenticationResponse } from '../autheicationresponse';
import { HttpErrorResponse } from '@angular/common/http';
import { FacebookLoginProvider, GoogleLoginProvider, SocialAuthService, SocialUser } from '@abacritt/angularx-social-login';
@Component({
  selector: 'app-loginuser',
  templateUrl: './loginuser.component.html',
  styleUrls: ['./loginuser.component.css']
})
export class LoginuserComponent implements OnInit {


  constructor(private  router: Router, private inscriptionservice: InscriptionService, private authServices: AuthService, private authService : SocialAuthService )  {   }

    login: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
    ]),
  });

  
  get email() {
    return this.login.get('email');
  }
  get password() {
    return this.login.get('password');
  }

    ngOnInit() {
      this.authService.authState.subscribe((user) => {
        if (user) {
         this.user = user;
          console.log('User logged in:',user.idToken);
          const tokenGoogle = new AuthenticationResponse(this.user.idToken,"null");
          this.inscriptionservice.google(tokenGoogle).subscribe({
            next: (response: AuthenticationResponse) => {
              this.authServices.saveToken(response.token);
              console.log(this.authServices.getUser());
             
              this.isLoginFailed = false;
              this.loginSuccess = true;
              this.router.navigate(['/home']);
               window.location.reload();
            
    
            },
            error: (err: Error) => { 
              this.isLoginFailed = true;
            },
          });
          
        } else {
          // User is logged out
          console.log('User logged out');
        }
      });
    }
  
    

onSubmit() {
    const LoginInfo = {
      email: this.email?.value,
      password: this.password?.value,
    };
    if (this.login.valid) {
      this.inscriptionservice.login(LoginInfo).subscribe({
        next: (response: AuthenticationResponse) => {
          this.authServices.saveToken(response.token);
          console.log(this.authServices.getUser());
         
          this.isLoginFailed = false;
          this.loginSuccess = true;
          this.router.navigate(['/home']);
            window.location.reload();
/*           alert('Inscription effectuée avec succès');
 */    /*       window.location.reload(); */
        

        },
        error: (err: Error) => { 
      /*     alert('login failed');
          this.errorMessage = err.message; */
          this.isLoginFailed = true;
        },
      });
    } 
  }










  user: SocialUser | undefined;
    loggedIn: boolean | undefined;

  m: string | null | undefined;
  isSubmited: boolean = false;

  hide: boolean = true;
  
  isLoginFailed = false;

errorMessage = 'Invalid Credentials';
  successMessage!: string;
  invalidLogin = false;
  loginSuccess = false;
  AuthService: any;






  




  







  

  
}
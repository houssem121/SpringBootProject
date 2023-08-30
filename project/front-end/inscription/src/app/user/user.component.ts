import { Component } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { InscriptionService } from '../inscription.service';
import { Inscription } from '../inscription';
import { user } from '../autheicationresponse';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent {
  constructor(private inscriptionService: InscriptionService) { }
  passchange: FormGroup = new FormGroup({
    oldpassword: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
    ]),
    newpassword: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
      this.passwordValidator,
    ]),
    cnewpassword: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
    ]),
    
    
  },
  {
    validators: this.passwordMatch('newpassword', 'cnewpassword'),
  }

  );
  
  private passwordValidator(control: AbstractControl) {
    const password = control.value;
    const hasNumber = /\d/.test(password);
    const hasUppercase = /[A-Z]/.test(password);
    const hasLowercase = /[a-z]/.test(password);

    if (hasNumber && hasUppercase && hasLowercase) {
      if (password.length >= 8) {
        return null; 
      }
    }

    return { passwordRequirements: true }; 
  }
private   passwordMatch(password: string, confirmPassword: string) {
  return (formGroup: AbstractControl): ValidationErrors | null => {
    const passwordControl = formGroup.get(password);
    const confirmPasswordControl = formGroup.get(confirmPassword);
    if (!passwordControl || !confirmPasswordControl) {
      return null;
    }
    if (
      confirmPasswordControl.errors &&
      !confirmPasswordControl.errors['passwordMismatch']
    ) {
      return null;
    }
    if (passwordControl.value !== confirmPasswordControl.value) {
      confirmPasswordControl.setErrors({ passwordMismatch: true });
      return { passwordMismatch: true };
    } else {
      confirmPasswordControl.setErrors(null);
      return null;
    }
  };}


public user: user[]= [];
public message : String="";
showDetails: boolean = false;
showpassword: boolean = false;

toggleDetails() {
  this.showDetails = !this.showDetails;
 this.showpassword= false;

}
togglepassword() {
  this.showpassword = !this.showpassword;
  this.showDetails= false;
}
  ngOnInit() {
 this.userinfo();

  }
    get  oldpassword() {
    return this.passchange.get('oldpassword');
  }
  get newpassword() {
    return this.passchange.get('newpassword');
  }

get cnewpassword() {

    return this.passchange.get('cnewpassword');
  }
    
  onsubmit() {
    const LoginInfo = {
      oldPassword: this.oldpassword?.value,
    newPassword: this.newpassword?.value,
    };
    if (this.passchange.valid) {
      this.inscriptionService.updatepassword(LoginInfo).subscribe({
        next: (response) => {
            alert(response);
        
        },
        error: (err: Error) => { 
          alert(err.message);
        },
      });
    } else {
      alert('Please fill all the required fields');
        }
  }




  public userinfo(): any {
    
    this.inscriptionService.userinfo().subscribe(
      (response :any) => {
        this.user.push(response);
        console.log(this.user);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );



  }
}

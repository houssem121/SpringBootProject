import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { InscriptionService } from '../inscription.service';
import { Inscription } from '../inscription';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthenticationResponse } from '../autheicationresponse';
import { catchError } from 'rxjs';
import { Router } from '@angular/router';
@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent {
  router: any;
  constructor(private inscriptionService: InscriptionService) { }
  title = 'inscription';
  inscriptionee: FormGroup = new FormGroup({
    lastname: new FormControl('', [Validators.required , Validators.minLength(3)]),
    firstname: new FormControl('', [Validators.required , Validators.minLength(3)]),
    email: new FormControl('', [Validators.required, Validators.email]),
    address: new FormControl('', [Validators.required]),
    portable: new FormControl('', [Validators.required,this.exactLengthAndNumericValidator(8)]),
    
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
      this.passwordValidator,
    ]),
    confirmpassword: new FormControl('', [Validators.required]),
   
    
  },
  {
    validators: this.passwordMatch('password', 'confirmpassword'),
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

  private exactLengthAndNumericValidator(exactLength: number) {
    return (control: AbstractControl) => {
      const value = control.value;
      if (value === null || value === undefined || value === '') {
        return null;
      }

      const numericRegex = /^[0-9]+$/;
      if (value.length === exactLength && numericRegex.test(value)) {
        return null; 
      } else {
        return { exactLengthAndNumeric: true }; 
      }
    };
  }

get confirmpassword() {
  return this.inscriptionee.get('confirmpassword');
}
  get lastname() {  
    return this.inscriptionee.get('lastname');
  }
  get firstname() {
    return this.inscriptionee.get('firstname');
  }
  get email() {
    return this.inscriptionee.get('email');
  }
  get address() { 
    return this.inscriptionee.get('address');
  }
  get portable() {
    return this.inscriptionee.get('portable');
  }
  get password() {
    return this.inscriptionee.get('password');
  }



ngOnInit() {

}

    Onsubmit( ) { 
      const inscriptionee = {
        lastname: this.lastname?.value,
        firstname: this.firstname?.value,
        email: this.email?.value,
        address: this.address?.value,
        portable: this.portable?.value,
        password: this.password?.value,
      };
      

      if (this.inscriptionee.valid) {
      this.inscriptionService.inscri(inscriptionee).subscribe(
        (response: AuthenticationResponse) => {
          console.log(response);
          alert('Inscription effectuée avec succès');
          this.inscriptionee.reset();
        },
        (error: any) => {
          if (error.error && error.error.errorms) {
            console.log('Error:', error.error.errorms);
            alert('Error: ' + error.error.errorms); 
          } else {
            console.log('Error:', error.message);
            alert('Error: ' + error.message); 
          }
          this.inscriptionee.reset();
        }
      );}}
      
    }

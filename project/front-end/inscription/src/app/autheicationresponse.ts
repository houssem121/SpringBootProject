export class AuthenticationResponse {
    token: string;
    message: string;
  
    constructor(token: string, message: string) {
      this.token = token;
      this.message = message;
    }
  
    getMessage(): string {
      return this.message;
    }
    setMessage(value: string) {
      this.message = value;
    }
    getToken(): string {
      return this.token;
    }
    setToken(value: string) {
      this.token = value;
    }
  }
  export class RegisterRequest {
    lastname: string;
    firstname: string;
    email: string;
    address: string;
    portable: number;
    password: string;
  
    constructor(lastname: string, firstname: string, email: string, address: string, portable: number, password: string) {
      this.lastname = lastname;
      this.firstname = firstname;
      this.email = email;
      this.address = address;
      this.portable = portable;
      this.password = password;
    }}
    export class authRequest {
      email: string;
      password: string;
      constructor(email: string, password: string) {  
        this.email = email;
        this.password = password;
      }
    }
    export class user {
  
      nom: string;
      prenom: string;
      email: string;
      adresse: string;
      numportable: number;
      password: string;
    constructor(nom: string, prenom: string, email: string, adresse: string, numportable: number, password: string) {
      this.nom = nom;
      this.prenom = prenom;
      this.email = email;
      this.adresse = adresse;
      this.numportable = numportable;
      this.password = password;
    }
     
    }
    export class passchange {
      oldPassword: string;
      newPassword: string;
      constructor(oldpassword: string, newpassword: string) {  
        this.oldPassword = oldpassword;
        this.newPassword = newpassword;
      }
    }
      
  
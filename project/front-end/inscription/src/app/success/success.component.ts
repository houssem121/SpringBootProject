import { Component, OnInit } from '@angular/core';
import { InscriptionService } from '../inscription.service';
import { AuthService } from '../Services/auth.service';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent  implements OnInit{
  constructor(private inscriptionservice: InscriptionService,private authservice: AuthService) { }
show : boolean = false;

  ngOnInit(): void {
     
    console.log(this.authservice.getid());
    
      this.inscriptionservice.verify(this.authservice.getid()).subscribe({
        next: (response: any) => {
          const responseBody = JSON.parse(response.body); // Parse the response body
          if (responseBody.success) {
            this.show=true;         
           } 
          else
          {
           window.location.href = "http://localhost:4200/inscription/payfail";
          }
        },
        error: (err: Error) => {
          
     
          }});
    
  }
 




}

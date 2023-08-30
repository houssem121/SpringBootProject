import { Component,ViewChild  } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, map, startWith } from 'rxjs';
import { InscriptionService } from '../inscription.service';
import { AuthService } from '../Services/auth.service';

@Component({
  selector: 'app-page2',
  templateUrl: './page2.component.html',
  styleUrls: ['./page2.component.css']
})
export class Page2Component {


  placesOfBirths: string[] = [
    'تونس',
'أريانة',
'بن عروس',
'منوبة',
'نابل',
'زغوان',
'بنزرت',
'باجة',
'جندوبة',
'الكاف',
'سليانة',
'القيروان',
'القصرين',
'سيدي بوزيد',
'سوسة',
'المنستير',
'المهدية',
'صفاقس',
'قابس',
'مدنين',
'تطاوين',
'قفصة',
'توزر',
'قبلي'

  ];
  
  filteredPlaces: Observable<string[]> | undefined;
details : FormGroup = new FormGroup({
  lastname: new FormControl('', [Validators.required , Validators.minLength(3)]),
  firstname: new FormControl('', [Validators.required , Validators.minLength(3)]),
  bdate : new FormControl('', [Validators.required]),
  placeOfBirth: new FormControl('', [Validators.required]),
  



}); 

ngOnInit() {
  // Initialize the filtered places list
  this.filteredPlaces = this.details.get('placeOfBirth')?.valueChanges.pipe(
    startWith(''), // Start with an empty string
    map((value) => this._filterPlaces(value))
  );
}

private _filterPlaces(value: string): string[] {
  const filterValue = value.toLowerCase();
  return this.placesOfBirths.filter((place) =>
    place.toLowerCase().includes(filterValue)
  );
}

get placeOfBirth() {
  return this.details.get('placeOfBirth');
}
get lastname() {  
  return this.details.get('lastname');
}
get firstname() {
  return this.details.get('firstname');
}
get bdate() {
  return this.details.get('bdate'); 
}

Onsubmit() {
  const selectedDate = this.bdate?.value;
  console.log('Selected Date:', selectedDate);
  if (this.details.valid) {
    this.inscriptionservice.confirmpay().subscribe({
      next: (response: any) => {
        const responseBody = JSON.parse(response.body); // Parse the response body
        if (responseBody.result.success) {
          this.authservice.saveid(responseBody.result.payment_id);
          console.log(responseBody.result.payment_id);
          window.location.href = responseBody.result.link;
      }
        else
        {
          alert("payment failed ")
        }
      },
      error: (err: Error) => {
        
   
        }});
      
    };
  }












  constructor(private inscriptionservice: InscriptionService,private authservice: AuthService ) {
    
   }

  datashow:boolean=false;
 datago()
  {
    this.datashow=!this.datashow;
  }
}

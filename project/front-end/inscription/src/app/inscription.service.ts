import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Inscription } from './inscription';
import { AuthenticationResponse, RegisterRequest, authRequest, passchange, user } from './autheicationresponse';
@Injectable({
  providedIn: 'root'
})
export class InscriptionService {
  private urlss="http://localhost:8082/api/v2/inscription/userss";
private url="http://localhost:8082/api/v2/inscription";
private urls="http://localhost:8082/api/v2/user";
private urlp="http://localhost:8082/payment";
  constructor(private http: HttpClient ) { }
  public userinfo(): Observable<user> {
    return this.http.get<user>(`${this.urls}/userinfo`);
  }
public updatepassword(updatePassword: passchange): Observable<String> {
 return this.http.put<String>(`${this.urls}/updatePassword`,updatePassword);
}


public google(tokenDto: AuthenticationResponse): Observable<AuthenticationResponse> {
  return this.http.post<AuthenticationResponse>(`${this.url}/Googlelogin`, tokenDto);
}
public confirmpay() :Observable<any>{
  return this.http.get<any>(`${this.urlp}/create-payment`);
}

 public verify(idpay: any): Observable<any> {
    return this.http.post<any>(`${this.urlp}/verify-payment`,idpay);
  }
public getallmessage(): Observable<String> {
    return this.http.get<String>(this.urls, { responseType: 'text' as 'json' });
  }
  public   inscri(registerRequest: RegisterRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.url}/register`, registerRequest);
  }
  public  login(authRequest: authRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.url}/authenticate`, authRequest);
  }













  
}

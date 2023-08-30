import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MaterialModule } from './material.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'; // Import FormsModule
import { HttpClientModule } from '@angular/common/http';
import { BarinscriComponent } from './barinscri/barinscri.component';
import { FormComponent } from './form/form.component';
import { LoginuserComponent } from './loginuser/loginuser.component';
import { JwtModule } from '@auth0/angular-jwt'; 
import { InscriptionService } from './inscription.service';
import { authInterceptorProviders } from './Interceptor/auth.interceptor';
import { UserComponent } from './user/user.component';
import { HomeComponent } from './home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Page2Component } from './page2/page2.component';
import { SocialLoginModule, SocialAuthServiceConfig, FacebookLoginProvider } from '@abacritt/angularx-social-login';
import {GoogleLoginProvider} from '@abacritt/angularx-social-login';
import { FailComponent } from './fail/fail.component';
import { SuccessComponent } from './success/success.component';
@NgModule({
  declarations: [
    AppComponent,
    BarinscriComponent,
    FormComponent,
    FailComponent,
    SuccessComponent,
    LoginuserComponent,
    UserComponent,
    HomeComponent,
    Page2Component

    
    
  ],
  imports: [
    SocialLoginModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: () => {
          return localStorage.getItem('TOKEN_KEY'); 
        },
        allowedDomains:['localhost:8082'],
        disallowedRoutes: [],
      },
    }),
    BrowserAnimationsModule,
    MaterialModule,

  ],
  providers: [
    authInterceptorProviders,

    InscriptionService,
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
      {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider('258396886121-t7vp5nvt5e2eo1u3579ml2ig35p33dib.apps.googleusercontent.com')
      }
       ,
         
         
        ],
        onError: (err) => {
          console.error(err);
        }
      } as SocialAuthServiceConfig,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
 
} 

import { NgModule } from '@angular/core';
import { RouterModule, Routes, mapToCanActivate } from '@angular/router';
import { FormComponent } from './form/form.component';
import { LoginuserComponent } from './loginuser/loginuser.component';
import { UserComponent } from './user/user.component';
import { AuthGuard } from './Guards/auth.guard';
import { SecureInnerPagesGuard } from './Guards/secure-inner-pages.guard';
import { HomeComponent } from './home/home.component';
import { Page2Component } from './page2/page2.component';
import { SuccessComponent } from './success/success.component';
import { FailComponent } from './fail/fail.component';

const routes: Routes = [
  {path:'inscription',component:FormComponent ,canActivate:[SecureInnerPagesGuard]},
  {path:'admin',component:LoginuserComponent,canActivate:[SecureInnerPagesGuard]},
  {path:'user',component: UserComponent,canActivate:[AuthGuard]},
  {path:'home',component:HomeComponent},
  {path:'page2',component:Page2Component,canActivate:[AuthGuard]},
  {path:'paysuccess',component:SuccessComponent,canActivate:[AuthGuard]},
  {path:'payfail',component:FailComponent,canActivate:[AuthGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

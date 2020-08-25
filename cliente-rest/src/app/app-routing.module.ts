import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TablaComponent } from './tabla/tabla.component';
import { LoginComponent } from './login/login.component';
import { AuthserviceService } from './services/authservice.service'

const routes: Routes = [
  {
    path: '',
    redirectTo: '/tabla',
    pathMatch: 'full',
    canActivate: [AuthserviceService],
  },
  {
    path: 'tabla',
    component: TablaComponent ,
    canActivate: [AuthserviceService],
  },


  {
    path: 'login',
    component: LoginComponent,
  },
  // { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

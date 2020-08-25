import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class AuthserviceService implements CanActivate{
  private token: string;
  constructor(public router: Router) {}
  canActivate(): boolean {
    this.token = localStorage.getItem("token");
    console.log("token en guard =>", this.token);
    if (this.token === null) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }

  getToken(){
    return this.token;
  }
}

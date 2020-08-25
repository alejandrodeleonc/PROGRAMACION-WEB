import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Location } from '@angular/common';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { FuncionesService } from './funciones.service';
import { AuthserviceService } from './authservice.service';

@Injectable()
export class InterceptorService {

  constructor(private funciones: FuncionesService, private _location: Location, private auth: AuthserviceService){}
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token: string  = localStorage.getItem('token');
    console.log("AuthService token =>", token);

    if (token) {
      console.log("AuthService 22 =>");
      console.log("req =>",req)
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${this.auth.getToken()}`,
        },
      });
    }else{
      if(!(this.funciones.router.url.split('/').indexOf("login") > -1 || this._location.path(true).indexOf("login") > -1)){
        console.log("Excepcon");
        this.funciones.rediccionaLogin();
      }
    }



    return next.handle(req);
  }
}

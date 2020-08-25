import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Observable} from 'rxjs';
import { Router } from '@angular/router';



@Injectable({
  providedIn: 'root'
})
export class PeticionesService {
  private ruta: string = ' http://localhost:8000';
  constructor( private http: HttpClient, private router: Router) {}

  login(object: any) {
    const body = new FormData();
    body.append('usuario', object.usuario);
    body.append('pass', object.pass);
    return this.http.post('/apirest/login', body);
    // return this.http.post(`${this.ruta}/apirest/login`, body);
  }

  registrar(data: any){
    console.log("datos enviados =>", data);
    return this.http.post<any>('/apirest/newForm', data);
  }
  getData(usuario: string) {

    const body = new FormData();
    const token: string = localStorage.getItem("token");
    console.log("usuario from get data: ", usuario);
    body.append('usuario', usuario);
    console.log('body =>', body);

    // let head = new HttpHeaders();
    // head.set(Autorization,)

    return this.http.post<any>('/apirest/listar', body)
    // return this.http.post(`${this.ruta}/apirest/listar`, {'usuario': usuario})
    // return this.http.post(`${this.ruta}/apirest/listar`, body,{ headers: {
    //   Authorization: 'Bearer ' + localStorage.getItem("token")}
    // });
  }
}

import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import * as jwt_decode from 'jwt-decode';
import { WebcamImage } from 'ngx-webcam';

@Injectable()
export class FuncionesService {
  private foto: any;
  private cameraStatus:  boolean;
  constructor(public router: Router) {}

  getUsername(): string {
    var token = localStorage.getItem('token');
    var decoded = jwt_decode(token);


    return decoded.usuario;
  }
  rediccionaLogin() {
    this.router.navigate(['/login']);
  }

  setFoto(foto: string) {
    this.foto = foto;
  }

  setcamaraStatus(status: boolean){
    this.cameraStatus = status;
  }

  getcamaraStatus(){
    return this.cameraStatus;
  }
  getFoto():string {
    console.log("foto from getFoto() =>",this.foto);
    return this.foto;
  }

  getPosition(): Promise<any>
  {
    return new Promise((resolve, reject) => {

      navigator.geolocation.getCurrentPosition(resp => {

          resolve({lng: resp.coords.longitude, lat: resp.coords.latitude});
        },
        err => {
          reject(err);
        });
    });

  }
}

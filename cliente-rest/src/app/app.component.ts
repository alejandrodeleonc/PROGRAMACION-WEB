import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'cliente-rest';

  constructor( private router: Router){
    const token = localStorage.getItem("token");
    console.log("AppComponent 00");

    if(token === null || token === undefined){
      console.log("AppComponent 11");
      this.router.navigate(["login"]);
    }
    console.log("AppComponent 22");
  }
}

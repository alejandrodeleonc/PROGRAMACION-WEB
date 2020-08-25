import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { PeticionesService } from '../services/peticiones.service'
import { Router } from '@angular/router';
import { FuncionesService } from '../services/funciones.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  user: string
  constructor(private fb: FormBuilder, private peticones: PeticionesService, private router: Router,
    private funciones: FuncionesService) { }
  form: FormGroup;

  ngOnInit(): void {
    this.form = this.fb.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
    });
  }

  login(): void{

    const data = JSON.parse(JSON.stringify(this.form.value));
    const username: string = data.username;
    const password: string = data.password;
    const data1= {};
    data1["usuario"]= username ;
    data1["pass"]= password ;
    console.log("Data del json =>", data1);

    // console.log("username =>", username);
    // console.log("password =>", password);

    this.peticones.login(data1).subscribe(
      (res:any) => {
        if(res !== null){
          localStorage.setItem('token', res.token);
          console.log(username);
          console.log("Puto token =>",res.token);
          this.router.navigate(["/tabla"]);
        }


      });
  }

  }




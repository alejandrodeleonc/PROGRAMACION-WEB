import { Component, OnInit } from '@angular/core';
import { SelectItem, MenuItem } from 'primeng/api';
import { PeticionesService } from '../services/peticiones.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { FuncionesService } from '../services/funciones.service';
import { stringify } from '@angular/compiler/src/util';

@Component({
  selector: 'tabla',
  templateUrl: './tabla.component.html',
  styleUrls: ['./tabla.component.scss'],
})
export class TablaComponent implements OnInit {
  items: MenuItem[];
  grados: SelectItem[];
  cities: SelectItem[];
  selectedGrado: any[];
  selectedCities: any[];
  display: boolean = false;
  nuevoForm: FormGroup;
  userNameSearch: string;
  encuestas: [];
  latitud: string;
  longitud: string;
  constructor(private peticones: PeticionesService, private formBuilder: FormBuilder, private fun: FuncionesService) {
    this.grados = [
      { label: 'Basico', value: 'Basico' },
      { label: 'Bachiller', value: 'Bachiller' },
      { label: 'Universitario', value: 'Universitario' },
      { label: 'Postgrado', value: 'Postgrado' },
      { label: 'Doctorado', value: 'Doctorado' },
    ];
    this.cities = [
      { label: 'Duarte', value: 'Duarte' },
      { label: 'Dajabón', value: 'Dajabón' },
      { label: 'Espaillat', value: 'Espaillat' },
      { label: 'La Vega', value: 'La Vega' },
      { label: 'María Trinidad Sánchez ', value: 'María Trinidad Sánchez ' },
      { label: 'Monseñor Nouel ', value: 'Monseñor Nouel ' },
      { label: 'Montecristi ', value: 'Montecristi ' },
      { label: 'Puerto Plata ', value: 'Puerto Plata ' },
      { label: 'Salcedo ', value: 'Salcedo ' },
      { label: 'Samaná ', value: 'Samaná ' },
      { label: 'Sánchez Ramírez ', value: 'Sánchez Ramírez ' },
      { label: 'Santiago de los Caballeros ', value: 'Santiago de los Caballeros ' },
      { label: 'Santiago Rodríguez ', value: 'Santiago Rodríguez ' },
      { label: 'Valverde', value: 'Valverde' },
    ];
    this.items = [
      {
        label: 'Nuevo registro',
        icon: 'pi pi-fw pi-plus',
        command: (event) => {
          this.funciones('nuevo', null);
        },
      },
      {
        label: 'Salir',
        icon: 'pi pi-sign-out',
        command: (event) => {
          this.funciones('logout', null);
        },
      },
    ];
  }

  onFileSelected(event) {
    console.log(event);
  }
  getPos() {
    this.fun.getPosition().then((pos) => {
      // console.log(`Positon: ${pos.lng} ${pos.lat}`);
      this.latitud = pos.lat;
      this.longitud = pos.lng;
    });
  }
  ngOnInit(): void {
    // console.log('Usuario decodificado =>', this.fun.getUsername());
    // this.showDialog();
    // this.funciones('actualizar', null);

    this.fun.setcamaraStatus(false);
    this.getPos();
    this.nuevoForm = this.formBuilder.group({
      latitud: [this.latitud, Validators.required],
      longitud: [this.longitud, Validators.required],
      nombre: [null, Validators.required],
      apellido: [null, Validators.required],
      grado: [null, Validators.required],
      provincia: [null, Validators.required],
      foto: [null, Validators.required],
    });
  }
  getData() {
    const user = this.userNameSearch;
    console.log('user =>', user);
    this.funciones('actualizar', user);
  }

  showDialog() {
    this.display = true;
  }

  registro() {
    console.log('foto from registro => ', this.fun.getFoto());
    const data = JSON.parse(JSON.stringify(this.nuevoForm.value));
    let newForm = {
      longitud: this.longitud,
      latitud: this.latitud,
      nombre: data.nombre,
      apellido: data.apellido,
      provincia: data.provincia[0],
      nivelacad: data.grado[0],
      usuario: this.fun.getUsername(),
      foto: this.fun.getFoto(),
    };
    // console.log("newForm =>", newForm);

    this.nuevoForm.reset();
    this.getPos();
    this.peticones.registrar(newForm).subscribe((res) => {
      console.log('Insertando +>', res);
    });
  }
  logout() {
    localStorage.removeItem('token');
    this.fun.rediccionaLogin();
  }
  funciones(arg, opc: any) {
    switch (arg) {
      case 'nuevo':
        this.showDialog();
        console.log('Se supone que abra un modal');
        break;
      case 'actualizar':
        this.peticones.getData(opc).subscribe((res: any) => {
          console.log();
          this.encuestas = res;
          console.log('res => ', this.encuestas);
        });
        break;
      case 'logout': //code block statement3
        this.logout();
        break;
      default:
      //default block statement;
    }
  }
}

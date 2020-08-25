import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TablaComponent } from './tabla/tabla.component';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';
import {MenuModule} from 'primeng/menu';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DialogModule } from 'primeng/dialog';
import { MultiSelectModule } from 'primeng/multiselect';
import { InterceptorService } from './services/interceptor.service';
import { FuncionesService } from './services/funciones.service';
import {WebcamModule} from 'ngx-webcam';
import { CameraComponent } from './camera/camera.component';
// import { FormsModule } from '@angular/forms';
// import { RatingModule } from 'primeng/primeng';

@NgModule({
  declarations: [
    AppComponent,
    TablaComponent,
    LoginComponent,
    CameraComponent
    // FormsModule00
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    TableModule,
    ButtonModule,
    FormsModule,
    ReactiveFormsModule.withConfig({warnOnNgModelWithFormControl: 'never'}),
    HttpClientModule,
    MenuModule,
    BrowserAnimationsModule,
    DialogModule,
    MultiSelectModule,
    WebcamModule



    // RatingModule

  ],
  providers: [
    {
      provide : HTTP_INTERCEPTORS,
      useClass: InterceptorService,
      multi   : true,
    }, FuncionesService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { RoutesListComponent } from '../routes-list/routes-list.component';
import { RoutesComponent } from './routes.component';
import { SharedModule } from '../shared/shared.module';
import { RouteDetailsComponent } from '../route-details/route-details.component';
import { FormsModule, NgSelectOption, ReactiveFormsModule } from '@angular/forms';
import { NgxPaginationModule } from 'ngx-pagination';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {MatNativeDateModule} from '@angular/material/core';
import {HttpClientModule} from '@angular/common/http';
import {MatPaginatorModule} from '@angular/material/paginator';
import { MaterialExampleModule } from '../shared/material.module';
import { ProfileRoutesComponent } from '../profile-routes/profile-routes.component';
import { ProfileSavedRoutesComponent } from '../profile-saved-routes/profile-saved-routes.component';
import { ProfileRatedRoutesComponent } from '../profile-rated-routes/profile-rated-routes.component';
import { MapComponent } from '../map/map.component';




@NgModule({
  declarations: [
    RoutesListComponent,
    RouteDetailsComponent,
    RoutesComponent,
    ProfileRoutesComponent,
    ProfileSavedRoutesComponent,
    ProfileRatedRoutesComponent,
    MapComponent
  ],
  imports: [
    SharedModule,
    ReactiveFormsModule,
    MatFormFieldModule, 
    MatSelectModule, 
    MaterialExampleModule,
    FormsModule,
    NgxPaginationModule,
    MatFormFieldModule, FormsModule, ReactiveFormsModule, MatNativeDateModule, FormsModule, ReactiveFormsModule, HttpClientModule,BrowserModule,BrowserAnimationsModule, MatNativeDateModule, MatPaginatorModule
  ], 
  exports: [
    RoutesListComponent,
    ProfileRoutesComponent,
    ProfileSavedRoutesComponent,
    ProfileRatedRoutesComponent
    // Export other components if needed
  ],
  bootstrap: [RoutesComponent]
  
})
export class RouteModule { }
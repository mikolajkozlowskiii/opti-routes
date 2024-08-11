import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ProductListComponent } from 'src/products/product-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProductDetailComponent } from './products/product-detail.component';
import { WelcomeComponent } from './home/welcome.component';
import { RouterModule } from '@angular/router';
import { ProductDetailGuard } from './products/product-detail.guard';
import { ProductModule } from './products/product.module';
import { RoutesComponent } from './routes/routes.component';
import { RoutesListComponent } from './routes-list/routes-list.component';
import { convertToMFromMs } from './shared/convert-to-m-from-ms.pipe';
import { CustomDatePipe } from './shared/convert-to-date-time';
import { StarComponent } from './shared/star.component';
import { RouteDetailsComponent } from './route-details/route-details.component';
import { RouteModule } from './routes/route.module';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { UsersModule } from './users.module';
import { BoardUserComponent } from './board-user/board-user.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProfileRoutesComponent } from './profile-routes/profile-routes.component';
import { ProfileSavedRoutesComponent } from './profile-saved-routes/profile-saved-routes.component';
import { ProfileRatedRoutesComponent } from './profile-rated-routes/profile-rated-routes.component';


@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,


  
    //RoutesComponent,
   // RoutesListComponent,
   // convertToMFromMs, 
   // CustomDatePipe,
    //RouteDetailsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot([
      { path: 'routes-create', component: RoutesComponent},

      { path: 'routes-list', component: RoutesListComponent},
      { 
        path: 'routes-list/:id', component: RouteDetailsComponent,
        canActivate: [ProductDetailGuard],
      },
      { 
        path: 'products/:id', component: ProductDetailComponent,
        canActivate: [ProductDetailGuard],
      },
      { path: 'welcome', component: WelcomeComponent },
      { path: '', redirectTo: 'welcome', pathMatch: 'full' },
      { path: '**', redirectTo: 'welcome', pathMatch: 'full' },
      { path: 'home', component: WelcomeComponent },
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'user', component: BoardUserComponent },
      { path: 'mod', component: BoardModeratorComponent },
      { path: 'admin', component: BoardAdminComponent },
    ]),
    RouteModule,
    ReactiveFormsModule,
    UsersModule,
    BrowserAnimationsModule,
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

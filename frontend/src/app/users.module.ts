import { HttpClientModule } from "@angular/common/http";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserModule } from "@angular/platform-browser";
import { LoginComponent } from "./login/login.component";
import { RegisterComponent } from "./register/register.component";
import { ProfileComponent } from "./profile/profile.component";
import { BoardAdminComponent } from "./board-admin/board-admin.component";
import { BoardModeratorComponent } from "./board-moderator/board-moderator.component";
import { BoardUserComponent } from "./board-user/board-user.component";
import { NgModule } from "@angular/core";
import { httpInterceptorProviders } from "./_helpers/auth.interceptor";
import { RouterModule, Routes } from "@angular/router";
import { WelcomeComponent } from "./home/welcome.component";
import { RouteModule } from "./routes/route.module";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MaterialExampleModule } from "./shared/material.module";
import { MatSelectModule } from "@angular/material/select";
import { MatFormFieldModule } from "@angular/material/form-field";
import { SharedModule } from "./shared/shared.module";
import { MatNativeDateModule } from "@angular/material/core";

const routes: Routes = [
  { path: 'home', component: WelcomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'mod', component: BoardModeratorComponent },
  { path: 'admin', component: BoardAdminComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardModeratorComponent,
    BoardUserComponent,
    ProfileComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    //AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RouteModule,
    SharedModule,
    ReactiveFormsModule,
    MatFormFieldModule, 
    MatSelectModule, 
    MaterialExampleModule,
    FormsModule,
    MatFormFieldModule, FormsModule, ReactiveFormsModule, MatNativeDateModule, FormsModule, ReactiveFormsModule, HttpClientModule,BrowserModule, MatNativeDateModule, MatPaginatorModule

    
  ],
  exports: [
    ProfileComponent
    ]
 // providers: [httpInterceptorProviders]
})
export class UsersModule { }

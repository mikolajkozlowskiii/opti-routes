import { Component } from "@angular/core";
import { StorageService } from "./_services/token-storage.service";
import { AuthService } from "./_services/auth.service";

@Component({
  selector: 'pm-root',
  template: `
  <nav class='navbar navbar-expand navbar-light bg-light'>
    <ul class='nav nav-pills mx-auto'>
      <li><a class='nav-link' routerLink='/welcome'>Opti Routes</a></li>
      <li><a class='nav-link' routerLink="/products">Users</a></li>
      <li><a class='nav-link' routerLink="/routes-create">Create Route</a></li>
      <li><a class='nav-link' routerLink="/routes-list">Search Route</a></li>

      <li class="nav-item" *ngIf="showAdminBoard">
        <a href="/admin" class="nav-link" routerLink="admin">Admin Board</a>
      </li>
      <li class="nav-item" *ngIf="showModeratorBoard">
        <a href="/mod" class="nav-link" routerLink="mod">Moderator Board</a>
      </li>
    </ul>
    <ul class="navbar-nav ml-auto" *ngIf="!isLoggedIn">
      <li class="nav-item">
        <a href="/register" class="nav-link" routerLink="register">Sign Up</a>
      </li>
      <li class="nav-item">
        <a href="/login" class="nav-link" routerLink="login">Login</a>
      </li>
    </ul>


    <ul class="nav nav-pills mx-auto" *ngIf="isLoggedIn">
      <li class="nav-item">
        <a href="/profile" class="nav-link" routerLink="profile"> {{ email }}</a>
      </li>
      <li class="nav-item">
        <a href class="nav-link" (click)="logout()"><span class="material-symbols-outlined align-middle">
logout
</span></a>
      </li>
    </ul>
  </nav>
  <div class='container'>
    <router-outlet></router-outlet>
  </div>
  <footer class="bg-body-tertiary text-center">
  <!-- Grid container -->
  <div class="container p-4" style="margin-top: 30px"></div>
  <!-- Grid container -->
  <!-- Copyright -->
  <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.05);">
    © 2024 Copyright:
    <a class="text-body" href="https://mdbootstrap.com/">Opti Routes</a>
  </div>
  <!-- Copyright -->
</footer>
  `,
    styleUrls: ['./app.component.css']

})
export class AppComponent  {
  pageTitle: string = 'Wroclaw Routes';

  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  email?: string;

  constructor(private storageService: StorageService, private authService: AuthService) { }
  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.email = user.email;
    }
  }

  logout(): void {
    this.storageService.clean();
    // this.authService.logout().subscribe({
    //   next: res => {
    //     console.log(res);
    //     console.log("test")
    //     this.storageService.clean();
    //   },
    //   error: err => {
    //     console.log(err);
    //   }
    // });
  }
}
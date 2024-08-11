import { Component } from '@angular/core';
import { UserService } from '../_services/user.service';

@Component({
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent {
  public pageTitle = `Create optimized routes, share it to your friends, and lets explore Wrocław together!`;
  content?: string;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    // this.userService.getPublicContent().subscribe({
    //   next: data => {
    //     this.content = data;
    //   },
    //   error: err => {console.log(err)
    //     if (err.error) {
    //       this.content = JSON.parse(err.error).message;
    //     } else {
    //       this.content = "Error with status: " + err.status;
    //     }
    //   }
    // });
  }
}

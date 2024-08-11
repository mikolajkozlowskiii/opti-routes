import { Component, OnInit } from '@angular/core';
import { StorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';
import { UserInfo } from './user.model';
import { RoutesListComponent } from '../routes-list/routes-list.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  currentUser: any;
  userInfo?: UserInfo;

  constructor(private storageService: StorageService, private userService: UserService) { }

  ngOnInit(): void {
    this.currentUser = this.storageService.getUser();

    this.userService.getUserBoard().subscribe({
      next: data => {
        this.userInfo = data;
      },
      error: err => {console.log(err)
        if (err.error) {
          this.userInfo = JSON.parse(err.error).message;
        } else {
          this.userInfo = undefined;
        }
      }
    });

    console.log('arbuz');
    console.log(this.userInfo);
  }

  onSubmit(): void {
    // Save changes logic
    console.log('Changes saved', this.userInfo);
  }

  onCancel(): void {
    // Cancel changes logic
    console.log('Changes canceled');
  }

  onFileSelected(event: Event): void {
  }
}
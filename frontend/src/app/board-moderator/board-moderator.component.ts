import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'pm-board-moderator',
  templateUrl: './board-moderator.component.html',
  styleUrls: ['./board-moderator.component.css']
})
export class BoardModeratorComponent implements OnInit {

  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'email', 'roles', 'actions'];
  dataSource: MatTableDataSource<UserInfo>;

  constructor(private userService: UserService) {
    this.dataSource = new MatTableDataSource<UserInfo>();
  }

  ngOnInit(): void {
    this.getAllUsers();
  }

  getAllUsers(): void {
    console.log("fetching users");
    this.userService.getAllUsers().subscribe(
      data => {
        this.dataSource.data = data;
        console.log('Pobrani użytkownicy:', data); // Logowanie pobranych użytkowników
      },
      error => {
        console.log('Error fetching users:', error);
      }
    );
  }
  removeAccount(id: number) {
    // Implement logic to remove the account
  }
  
  addModerator(id: number) {
    // Implement logic to add ROLE_MODERATOR
  }
  
  removeModerator(id: number) {
    // Implement logic to remove ROLE_MODERATOR
  }
  
  addGuide(id: number) {
    // Implement logic to add ROLE_GUIDE
  }
  
  removeGuide(id: number) {
    // Implement logic to remove ROLE_GUIDE
  }
  
}

export interface UserInfo {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  roles: string[];
}
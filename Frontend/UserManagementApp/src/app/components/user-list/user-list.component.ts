import {Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {User} from '../../model/user';
import Swal from 'sweetalert2';
import * as routes from '../../utils/global-routes';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: User[] | undefined;
  duplicateUsers: User[] | undefined;
  // private currentUser: User | undefined;
  currentUser: any;
  currentIndex = -1;
  searchKeys = ['firstName', 'lastName', 'street', 'city', 'state', 'zipCode'];
  searchText = '';
  globalRoutes: any;

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.globalRoutes = routes;
    this.retrieveUserList();
  }

  retrieveUserList(): void {
    Swal.fire({
      title: 'Please Wait....',
      allowEscapeKey: false,
      allowOutsideClick: false,
      onOpen: () => {
        Swal.showLoading();
      }
    });
    this.userService.getAllUsers()
      .subscribe(data => {
        Swal.close();
        this.users = this.duplicateUsers = data;
        console.log(data);
      }, error => {
        Swal.close();
        console.log(error);
      });
  }

  refreshList(): void {
    this.retrieveUserList();
    this.currentUser = null; // todo: check it
    this.currentIndex = -1;
  }

  setActiveUser(user: User, index: number): void {
    this.currentUser = user;
    this.currentIndex = index;
  }

  removeUserById(id: number): void {
    this.userService.deleteUserById(id).subscribe(
      response => {
        this.fireSweetAlert(response.message, true);
        this.refreshList();
      }, error => {
        Swal.close();
        this.fireSweetAlert(error.message,  false);
      }
    );
  }

  removeAllUsers(): void {
    this.userService.deleteAllUsers()
      .subscribe(response => {
        console.log(response);
        this.refreshList();
      }, error => {
        console.log(error);
      });
  }

  /*todo: Add a function to fuzzy search*/
  searchTable(): void {
  }

  /*
  * searchTable() {
        this.users = this.usersDup.filter((o) => {
            let status = false;
            for (let i = 0; i <= this.searchKeys.length; i++) {
                const k = this.searchKeys[i];
                if (Array.isArray(o[k])) {
                    o[k].forEach(d => {
                        status = status || (d && d.indexOf(this.searchText) >= 0) || !this.searchText;
                    });
                } else if (o[k] && typeof o[k] === 'object') {
                   // console.log(Object.keys(o[k]));
                    Object.keys(o[k]).forEach(kk => {
                        status = status || (o[k][kk] && (o[k][kk] + '').indexOf(this.searchText) >= 0)
                            || (kk + '').indexOf(this.searchText) >= 0 || !this.searchText;
                    });
                } else {
                    status = status || (o[k] && o[k].indexOf(this.searchText) >= 0) || !this.searchText;
                }
            }
            return status;
        });
    }
  * */

  fireSweetAlert(titleStr: string, isSuccess: boolean): void {
    Swal.fire({
      title: titleStr,
      allowEscapeKey: false,
      iconColor: isSuccess ? 'success' : 'warning',
      allowOutsideClick: false,
    });
  }
}

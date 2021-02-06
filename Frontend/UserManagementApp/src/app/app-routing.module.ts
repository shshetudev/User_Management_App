import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UserListComponent} from './components/user-list/user-list.component';
import {UserAddComponent} from './components/user-add/user-add.component';
import {UserDetailsComponent} from './components/user-details/user-details.component';
import {globalRoutes} from './utils/global-routes';
const routes: Routes = [
  {path: '', redirectTo: 'userList', pathMatch: 'full'},
  {path: globalRoutes.userAddRoute, component: UserAddComponent},
  {path: globalRoutes.userListRoute, component: UserListComponent},
  {path: globalRoutes.userDetailsRoute, component: UserDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

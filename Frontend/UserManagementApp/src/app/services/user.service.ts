import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../model/user';
import {UserType} from '../model/user-type.enum';

/*todo: add here*/
const baseURL = 'http://localhost:8181/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) {
  }

  /*todo: fix return type of any*/

  /*(`${baseUrl}/${id}`);*/
  saveUser(user: User): Observable<any> {
    return this.httpClient.post<any>(`${baseURL}/save`, user);
  }

  getUserBydId(id: number): Observable<any> {
    return this.httpClient.get<any>(`${baseURL}/findById/${id}`);
  }

  getAllUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(`${baseURL}/findAll`);
  }

  getAllUsersByUserType(userType: UserType): Observable<User[]> {
    return this.httpClient.get<User[]>(`${baseURL}/findAllByUserType/${userType}`);
  }

  /*todo: check update method properly*/
  updateUser(user: User): Observable<any> {
    return this.httpClient.put<any>(`${baseURL}/update`, user);
  }

  deleteUserById(id: number): Observable<any> {
    return this.httpClient.delete<any>(`${baseURL}/delete/${id}`);

  }

  deleteAllUsers(): Observable<any> {
    return this.httpClient.delete<any>(`${baseURL}/deleteAll`);
  }

}

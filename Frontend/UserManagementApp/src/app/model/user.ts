import {UserType} from './user-type.enum';

 export interface User {
  id: number;
  firstName: string;
  lastName: string;
  street: string;
  city: string;
  state: string;
  zipcode: string;
  userType: UserType;
}

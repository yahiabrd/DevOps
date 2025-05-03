import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  public fetchUsers(): Observable<User[]> {
    console.log(environment)
    return this.http.get<User[]>(environment.apiUrl + "/users/all");
  }
}

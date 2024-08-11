import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StorageService } from './token-storage.service';
import { UserInfo } from '../profile/user.model';

const API_URL = 'http://localhost:8080/api/v1/users';
const ROUTE_SAVE_URL = 'http://localhost:8080/api/v1/routes/';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient, private storageService: StorageService) {}

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + '/all', { responseType: 'text' });
  }

  getAllUsers(): Observable<UserInfo[]> {
    return this.http.get<UserInfo[]>(API_URL + "/", { responseType: 'json' });
  }

  getUserBoard(): Observable<UserInfo> {
    console.log(this.storageService.getToken());
    return this.http.get<UserInfo>(API_URL + '/me', { responseType: 'json', headers: {'Authorization': 'Bearer ' + this.storageService.getToken()} });
  }
  
  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', { responseType: 'text', headers: {'Authorization': 'Bearer ' + this.storageService.getToken()} });
  }


  isRouteSaved(id: number): Observable<boolean> {
    var url = ROUTE_SAVE_URL + id.toString() + '/saves';
    return this.http.get<boolean>(url, { responseType: 'json', headers: {'Authorization': 'Bearer ' + this.storageService.getToken()} });
  }

  saveRoute(id: number): Observable<any> {
    var url = ROUTE_SAVE_URL + id.toString() + '/saves';
    return this.http.put(url, null,{ responseType: 'json', headers: {'Authorization': 'Bearer ' + this.storageService.getToken()} });
  }

  unSaveRoute(id: number): Observable<any> {
    var url = ROUTE_SAVE_URL + id.toString() + '/saves';
    console.log('brzoskiwania');
    console.log(url);
    return this.http.delete(url,  { responseType: 'json', headers: {'Authorization': 'Bearer ' + this.storageService.getToken()} });
  }




  rateRoute(idRoute: number, rate: number): Observable<any>{
    const url = ROUTE_SAVE_URL + idRoute + '/ratings';
    console.log('brzoskiwania1');
    console.log(url);
  
    const body = { rating: rate }; // Tworzymy obiekt JSON z właściwością "rating"
  
    return this.http.put(url, body, {
      responseType: 'json',
      headers: {'Authorization': 'Bearer ' + this.storageService.getToken()}
    });
  }

   unRateRoute(idRoute: number): Observable<any>{
    var url = ROUTE_SAVE_URL + idRoute + '/ratings';
    console.log('brzoskiwania2');
    console.log(url);
    return this.http.delete(url,  { responseType: 'json', headers: {'Authorization': 'Bearer ' + this.storageService.getToken()} });
  }

  isRouteRated(idRoute: number): Observable<number>{
    var url = ROUTE_SAVE_URL + idRoute + '/ratings';
    console.log('brzoskiwania3');
    console.log(url);
    return this.http.get<number>(url,  { responseType: 'json', headers: {'Authorization': 'Bearer ' + this.storageService.getToken()} });
  }

}
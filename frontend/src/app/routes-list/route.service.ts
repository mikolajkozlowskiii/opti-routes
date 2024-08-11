import { Injectable } from "@angular/core";
import { IRouteDetails, IRouteRatedDetails, IRouteSavedDetails } from "./route";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable, tap, catchError, throwError, map, find } from "rxjs";
import { StorageService } from "../_services/token-storage.service";
import { RouteRequest } from "../routes/routes.model";

@Injectable({
    providedIn: 'root'
})
export class RouteService{
    private rotuesUrl = 'http://localhost:8080/api/v1/routes/';

    constructor(private http: HttpClient,  private storageService: StorageService){}


    createRoute(routeRequest: RouteRequest): Observable<IRouteDetails> {
        return this.http.post<IRouteDetails>(
            this.rotuesUrl+'opti', 
            routeRequest
          , 
          { responseType: 'json', headers: {'Authorization': 'Bearer ' + this.storageService.getToken()} })
          .pipe(
            tap(data => console.log('All', JSON.stringify(data))),
            catchError(this.handleError)
        );
      }
    
    

     getUserRoutes(): Observable<IRouteDetails[]>{
        return this.http.get<IRouteDetails[]>(this.rotuesUrl+'user-author/details',  { responseType: 'json', headers: {'Authorization': 'Bearer ' + this.storageService.getToken()} }).pipe(
            tap(data => console.log('All', JSON.stringify(data))),
            catchError(this.handleError)
       );
    }

    getUserRatedRoutes(): Observable<IRouteRatedDetails[]> {
        return this.http.get<IRouteRatedDetails[]>(this.rotuesUrl+'user-author/details/rating',  { responseType: 'json', headers: {'Authorization': 'Bearer ' + this.storageService.getToken()} }).pipe(
            tap(data => console.log('All', JSON.stringify(data))),
            catchError(this.handleError)
       );
    }

    getUserSavedRoutes(): Observable<IRouteSavedDetails[]> {
        return this.http.get<IRouteSavedDetails[]>(this.rotuesUrl+'user-author/details/saves',  { responseType: 'json', headers: {'Authorization': 'Bearer ' + this.storageService.getToken()} }).pipe(
            tap(data => console.log('All', JSON.stringify(data))),
            catchError(this.handleError)
       );
    }

    // getRatedRoutes(): Observable<IRouteDetails[]>{
        
    // }

    // getSavedRoutes(): Observable<IRouteDetails[]>{
        
    // }

    getRoutes(): Observable<IRouteDetails[]>{
       return this.http.get<IRouteDetails[]>(this.rotuesUrl+'details').pipe(
            tap(data => console.log('All', JSON.stringify(data))),
            catchError(this.handleError)
       );
    }

    getRoute(id: number): Observable<IRouteDetails | undefined>{
        return this.getRoutes()
        .pipe(
            map((routes: IRouteDetails[]) => routes.find(r => id === r.id))
        );
     }

    private handleError(err: HttpErrorResponse) {
        let errorMessage = '';
        if(err.error instanceof ErrorEvent){
            errorMessage = `An error occurred: ${err.error.message}`;
        } else { 
            errorMessage = `Server returned code: ${err.status}, error message is: ${err.message}`;
        }
        console.error(errorMessage);
        return throwError(()=>errorMessage);
    }
}
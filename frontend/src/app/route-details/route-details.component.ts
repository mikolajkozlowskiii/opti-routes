import { Component, OnDestroy, OnInit, AfterViewInit, AfterViewChecked } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { UserService } from '../_services/user.service';
import * as mapboxgl from 'mapbox-gl';
import { environment } from '../../environments/environment';
import { IRouteDetails } from '../routes-list/route';
import { RouteService } from '../routes-list/route.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'pm-route-details',
  templateUrl: './route-details.component.html',
  styleUrls: ['./route-details.component.css']
})
export class RouteDetailsComponent implements OnInit, OnDestroy, AfterViewInit, AfterViewChecked {
  activeButton: number | null = null;
  durationInSeconds = 5;

  isRouteSaved?: boolean;
  routeId: number | undefined;
  formGroup: FormGroup;
  pageTitle: string | undefined = 'Route ';
  errorMessage = '';
  sub!: Subscription;
  routeDetail: IRouteDetails | undefined;
  _routeService: RouteService;
  arrayInstructions: string[] = [
    'Go straight for ', 'Turn left onto bulwar Słoneczny and follow this route for 350 meters', 'turn whenever you want..', 'go back', 'go left', 'turn left', 'turn right'
  ];

  map!: mapboxgl.Map;
  markers: mapboxgl.Marker[] = [];
  coordinates: { id: number, lng: number, lat: number, color: string }[] = [];
  sliderValue: number = 0;
  isInitialLoad: boolean = true;

  constructor(
    private _snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    routeService: RouteService,
    private userService: UserService
  ) {
    this._routeService = routeService;
    this.formGroup = this.fb.group({
      value: [0, Validators.required]
    });
  }

  async ngOnInit(): Promise<void> {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      try {
        await this.getRoute(id);
        this.pageTitle = `Route ${this.routeDetail?.id}`;
        this.isRouteSaved = false;
        this.isRouteAlreadySaved();
        this.routeId = this.routeDetail?.id;

        this.userService.isRouteRated(id).subscribe((rating) => {
          if (rating > 0) {
            this.sliderValue = rating;
          }
          this.isInitialLoad = false;
        });

        this.initializeMapAndMarkers();
      } catch (error) {
        console.error("Failed to load route details:", error);
      }
    }
  }

  ngAfterViewInit() {
    // Map initialization is now handled in ngOnInit after routeDetail is loaded
  }

  ngAfterViewChecked() {
    if (this.map) {
      this.map.resize();
    }
  }

  private initializeMapAndMarkers(): void {
    if (this.routeDetail && this.routeDetail.steps && this.routeDetail.steps.length > 0) {
      const firstStep = this.routeDetail.steps.find(s => s.step === 0);

      if (firstStep && firstStep.location && firstStep.location.locationInfo) {
        const initialCord: { lat: number, lng: number } = {
          lat: firstStep.location.locationInfo.latitude,
          lng: firstStep.location.locationInfo.longitude
        };

        this.initializeMap(initialCord);
        this.addMarkersForSteps();
      } else {
        console.error("The first step or its location information is missing.");
      }
    } else {
      console.error("Route details or steps are missing.");
    }
  }

  private initializeMap(center: { lat: number; lng: number }): void {
    (mapboxgl as any).accessToken = environment.mapbox.accessToken;
    this.map = new mapboxgl.Map({
      container: 'map',
      style: 'mapbox://styles/mapbox/streets-v11',
      center: [center.lng, center.lat],
      zoom: 13
    });

    this.map.on('load', () => {
      this.map.resize();
    });
  }

  private addMarkersForSteps(): void {
    if (this.routeDetail && this.routeDetail.steps) {
      const firstStep = this.routeDetail.steps.find(s => s.step === 0);
      const lastStep = this.routeDetail.steps[this.routeDetail.steps.length - 1];

      if (firstStep && lastStep) {
        const firstLocation = firstStep.location.locationInfo;
        const lastLocation = lastStep.location.locationInfo;

        if (firstLocation.latitude === lastLocation.latitude && firstLocation.longitude === lastLocation.longitude) {
          console.log("depot");
          for (const step of this.routeDetail.steps) {
            const { latitude, longitude } = step.location.locationInfo;
            const lngLat = new mapboxgl.LngLat(longitude, latitude);
            if (step === firstStep) {
              this.addMarkerDepot(lngLat, step.step);
            } else if (step !== lastStep) {
              this.addMarker(lngLat, step.step - 1);
            }

          }
        } else {
          console.log(" not depot ")
          for (const step of this.routeDetail.steps) {
            const { latitude, longitude } = step.location.locationInfo;
            const lngLat = new mapboxgl.LngLat(longitude, latitude);
            this.addMarker(lngLat, step.step);
          }
        }
      }
    }
  }

  private addMarker(lngLat: mapboxgl.LngLat, step: number): void {
    const el = document.createElement('div');
    const id = step + 1;
    el.className = 'marker';
    el.textContent = id.toString();
    el.style.backgroundColor = 'blue';
    el.style.color = 'white';
    el.style.borderRadius = '50%';
    el.style.width = '25px';
    el.style.height = '25px';
    el.style.display = 'flex';
    el.style.justifyContent = 'center';
    el.style.alignItems = 'center';

    const marker = new mapboxgl.Marker(el, { draggable: true })
      .setLngLat(lngLat)
      .addTo(this.map);

    this.markers.push(marker);
    this.coordinates.push({ id: step, lng: lngLat.lng, lat: lngLat.lat, color: 'primary' });

    marker.on('dragend', () => {
      const index = this.markers.findIndex((m) => m === marker);
      if (index !== -1) {
        const coordinates = marker.getLngLat();
        this.coordinates[index] = { id: step, lng: coordinates.lng, lat: coordinates.lat, color: 'red' };
      }
    });
  }

  private addMarkerDepot(lngLat: mapboxgl.LngLat, step: number): void {
    const el = document.createElement('div');
    const id = step + 1;
    el.className = 'marker';
    el.textContent = 'D';
    el.style.backgroundColor = 'blue';
    el.style.color = 'white';
    el.style.borderRadius = '50%';
    el.style.width = '25px';
    el.style.height = '25px';
    el.style.display = 'flex';
    el.style.justifyContent = 'center';
    el.style.alignItems = 'center';

    const marker = new mapboxgl.Marker(el, { draggable: true })
      .setLngLat(lngLat)
      .addTo(this.map);

    this.markers.push(marker);
    this.coordinates.push({ id: step, lng: lngLat.lng, lat: lngLat.lat, color: 'primary' });

    marker.on('dragend', () => {
      const index = this.markers.findIndex((m) => m === marker);
      if (index !== -1) {
        const coordinates = marker.getLngLat();
        this.coordinates[index] = { id: step, lng: coordinates.lng, lat: coordinates.lat, color: 'red' };
      }
    });
  }
  
  
  getRoute(id: number): Promise<void> {
    return new Promise((resolve, reject) => {
      this.sub = this._routeService.getRoute(id).subscribe({
        next: route => {
          this.routeDetail = route;
          resolve();
        },
        error: err => {
          this.errorMessage = err;
          reject(err);
        }
      });
    });
  }

  onBack(): void {
    this.router.navigate(['/routes-list']);
  }

  ngOnDestroy(): void {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

  onSave(): void {
    this.routeId = this.routeDetail?.id;
    if (this.routeId) {
      this.userService.saveRoute(this.routeId).subscribe({
        error: err => this.errorMessage = err
      });
      this.getRoute(this.routeId);
      this.isRouteAlreadySaved();
    }
  }

  onUnSave(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.userService.unSaveRoute(id).subscribe({
      error: err => this.errorMessage = err
    });
    this.getRoute(id);
    this.isRouteAlreadySaved();
  }

  isRouteAlreadySaved(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.userService.isRouteSaved(id).subscribe({
      next: isSaved => this.isRouteSaved = isSaved,
      error: err => this.errorMessage = err
    });
  }

  reloadPage(): void {
    window.location.reload();
  }

  isActive(buttonNumber: number): boolean {
    return buttonNumber === this.activeButton;
  }

  toggleActive(buttonNumber: number): void {
    this.activeButton = this.activeButton === buttonNumber ? null : buttonNumber;

    if (this.isActive(buttonNumber)) {
      this.rateRoute(buttonNumber);
    } else {
      this.unRateRoute();
    }
  }

  rateRoute(rate: number): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.userService.rateRoute(id, rate).subscribe(() => { });
  }

  unRateRoute(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.userService.unRateRoute(id).subscribe(() => { });
  }

  formatLabel(value: number): string {
    return value.toString();
  }

  onSliderChange(value: number): void {
    if (!this.isInitialLoad) {
      this.rateRoute(value);
    }
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, 'Close', {
      duration: this.durationInSeconds * 1000,
    });
  }

  handleBookmarkClick(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.userService.isRouteSaved(id).subscribe({
      next: isSaved => {
        this.isRouteSaved = isSaved;
        if (isSaved) {
          this.onUnSave();
          this.openSnackBar('Route Unsaved');
        } else {
          this.onSave();
          this.openSnackBar('Route Saved');
        }
      },
      error: err => this.errorMessage = err
    });
  }
}

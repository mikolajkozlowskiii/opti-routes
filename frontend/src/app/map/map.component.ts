import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import * as mapboxgl from 'mapbox-gl';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { RouteService } from '../routes-list/route.service';
import { Location, RouteRequest } from '../routes/routes.model';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'],
})
export class MapComponent implements OnInit {
  isLoading = false;

  isLocateMeLaunched = false;
  formData = {
    description: '',
    tags: [],
    isOptimized: false,
    isPublic: false,
    depot: null
  };
  

  availableTags = ['FIRST TIME IN WROCLAW', 'GOOD DRINKS', 'GOOD FOOD', 'HISTORIC', 'LOCALS', 'LOUD', 'MODERN', 'NATURAL', 'PEACEFUL', 'QUIET', 'VINTAGE', 'NOT MANY TOURISTS'];
  map!: mapboxgl.Map;
  markers: mapboxgl.Marker[] = [];
  coordinates: { id: number, lng: number, lat: number, color: string }[] = [];

  constructor(private _formBuilder: FormBuilder, private router: Router, private _routeService: RouteService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.initializeMap({ lat: 52.231631734934304, lng: 21.006234154639923 });
  }

  private initializeMap(center: { lat: number; lng: number }) {
    (mapboxgl as any).accessToken = environment.mapbox.accessToken;
    this.map = new mapboxgl.Map({
      container: 'map',
      style: 'mapbox://styles/mapbox/streets-v11',
      center: [center.lng, center.lat],
      zoom: 13,
    });

    this.map.on('click', (event) => {
      this.addMarker(event.lngLat);
    });    
  }

  private addMarker(lngLat: mapboxgl.LngLat) {
    const id = this.markers.length + 1;
    const el = document.createElement('div');
    el.className = 'marker';
    el.textContent = id.toString();
    el.style.animationName = 'bounce';
    el.style.animation = '.5s linear 1s infinite alternate slidein';
    el.style.backgroundColor = 'blue';
    el.style.color = 'white';
    el.style.borderRadius = '50%';
    el.style.width = '25px';
    el.style.height = '25px';
    el.style.display = 'flex';
    el.style.justifyContent = 'center';
    el.style.alignItems = 'center';

    const marker = new mapboxgl.Marker(el, {
      draggable: true,
    })
      .setLngLat(lngLat)
      .addTo(this.map);

    marker.getElement().setAttribute('data-id', id.toString());

    this.markers.push(marker);
    this.coordinates.push({ id, lng: lngLat.lng, lat: lngLat.lat, color: 'primary' });

    marker.on('dragend', () => {
      const index = this.markers.findIndex((m) => m === marker);
      if (index !== -1) {
        const coordinates = marker.getLngLat();
        this.coordinates[index] = { id: this.coordinates[index].id, lng: coordinates.lng, lat: coordinates.lat, color: 'red' };
      }
    });
  }

  removeMarker(id: number) {
    const index = this.coordinates.findIndex((coord) => coord.id === id);
    if (index !== -1) {
      this.markers[index].remove();
      this.markers.splice(index, 1);
      this.coordinates.splice(index, 1);
      this.updateMarkerNumbers();
    }
  }

  private updateMarkerNumbers() {
    this.markers.forEach((marker, index) => {
      const el = marker.getElement();
      el.textContent = (index + 1).toString();
      this.coordinates[index].id = index + 1;
      el.setAttribute('data-id', (index + 1).toString());
    });
  }

  onCheckboxIsOptimizedChange(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    this.formData.isOptimized = inputElement.checked;
    console.log('Checkbox is checked:', this.formData.isOptimized);
  }

  onCheckboxIsPublicChange(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    this.formData.isPublic = inputElement.checked;
    console.log('Checkbox is checked:', this.formData.isPublic);
  }

  onSubmit() {
    console.log('Form Data:', this.formData);
    this.isLoading = true;
    // Assuming you have a service method that returns an Observable
//{ location: Location; step: number }[];


const coordinateDepot = this.coordinates.find(coord => coord.id === this.formData.depot);

if (coordinateDepot) {
  const depot: Location = {
  
      name: '',
      address: '',
      latitude: coordinateDepot.lat,
      longitude: coordinateDepot.lng,
    
  };
  console.log("Depot")
console.log(depot)
  const locations_steps: { location: Location; step: number }[] = this.coordinates.map((coordinate) => ({
    location: {
      name: '',  
      address: '',  
      latitude: coordinate.lat,
      longitude: coordinate.lng,
    },
    step: coordinate.id - 1  
  }));

    let routeRequest: RouteRequest = {
      description: this.formData.description,
      is_public: this.formData.isPublic,
      is_optimized: this.formData.isOptimized,
      locations_steps: locations_steps,
      tags: this.formData.tags,
      depot: depot
    }
    console.log(routeRequest)
    this._routeService.createRoute(routeRequest).subscribe({
      next: (response) => {
        this.router.navigate([`/routes-list/${response.id}`]); // Assuming 'id' is part of your response
        this.isLoading = false;
      },
      error: (error) => {
        console.error('There was an error!', error);
        this.isLoading = false;
      }
    });
  }
}

  remove(coord: { id: number, lng: number, lat: number }): void {
    this.removeMarker(coord.id);
  }

  getCurrentLocation(): Promise<{ lat: number; lng: number }> {
    return new Promise((resolve, reject) => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            if (position) {
              const lat = position.coords.latitude;
              const lng = position.coords.longitude;
              resolve({ lat, lng });
            }
          },
          (error) => {
            console.error(error);
            reject(error);
          }
        );
      } else {
        reject('Geolocation is not supported by this browser.');
      }
    });
  }

  useCurrentLocation() {
    this.isLocateMeLaunched = true;
    this.cdr.detectChanges(); // Ręczna detekcja zmian
    this.getCurrentLocation().then((location: { lat: number; lng: number }) => {
      this.map.setCenter([location.lng, location.lat]);
      this.isLocateMeLaunched = false;
      this.cdr.detectChanges(); // Ręczna detekcja zmian
    }).catch(error => {
      console.error('Error getting location', error);
      this.isLocateMeLaunched = false;
      this.cdr.detectChanges(); // Ręczna detekcja zmian
    });
  }

}


import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'metersToKilometers'
})
export class MetersToKilometersPipe implements PipeTransform {
  transform(value: number): string {
    // Przekształć metry na kilometry z dokładnością do jednego miejsca po przecinku
    const kilometers = value / 1000;
    return kilometers.toFixed(1) + ' km';
  }
}
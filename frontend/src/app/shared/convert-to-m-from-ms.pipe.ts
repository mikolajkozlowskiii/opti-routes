import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
    name: 'convertToMFromMs'
})
export class convertToMFromMs implements PipeTransform{
    transform(value: number) {
        const totalMinutes = Math.floor(value / (1000 * 60));
        const hours = Math.floor(totalMinutes / 60);
        const minutes = totalMinutes % 60;
      
        if (hours > 0) {
          if (minutes > 0) {
            return `${hours} h ${minutes} min`;
          } else {
            return `${hours} h`;
          }
        } else {
          return `${minutes} min`;
        }
    }
}
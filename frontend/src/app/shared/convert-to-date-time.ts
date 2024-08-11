import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'customDate' })
export class CustomDatePipe implements PipeTransform {
  transform(value: string): string {
    const dateObj = new Date(value);
    const formattedDate = this.formatDate(dateObj);
    const formattedTime = this.formatTime(dateObj);

    return `${formattedDate} ${formattedTime}`;
  }

  private formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');

    return `${year}-${month}-${day}`;
  }

  private formatTime(date: Date): string {
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    //const seconds = date.getSeconds().toString().padStart(2, '0');

    return `${hours}:${minutes}`;
  }
}
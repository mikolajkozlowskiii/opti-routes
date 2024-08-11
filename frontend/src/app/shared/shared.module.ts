import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StarComponent } from './star.component';
import { FormsModule } from '@angular/forms';
import { convertToMFromMs } from './convert-to-m-from-ms.pipe';
import { CustomDatePipe } from './convert-to-date-time';
import { ConvertToSpacesPipe } from './convert-to-spaces.pipe';
import { MetersToKilometersPipe } from './convert-to-km-from-m.pipe';



@NgModule({
  declarations: [
    StarComponent,
    convertToMFromMs,
    CustomDatePipe,
    ConvertToSpacesPipe,
    MetersToKilometersPipe
  ],
  imports: [
    CommonModule
  ],
  exports: [
    CommonModule,
    FormsModule,
    StarComponent,
    convertToMFromMs,
    CustomDatePipe,
    ConvertToSpacesPipe,
    MetersToKilometersPipe
    
  ]
})
export class SharedModule { }

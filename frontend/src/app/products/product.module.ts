import { NgModule } from '@angular/core';
import { ProductDetailComponent } from './product-detail.component';
import { ConvertToSpacesPipe } from '../shared/convert-to-spaces.pipe';
import { ProductListComponent } from 'src/products/product-list.component';
import { RouterModule } from '@angular/router';
import { ProductDetailGuard } from './product-detail.guard';
import { SharedModule } from '../shared/shared.module';
import { StarComponent } from '../shared/star.component';



@NgModule({
  declarations: [
    ProductListComponent,
    ProductDetailComponent,
  ],
  imports: [
    RouterModule.forChild([
      { path: 'products', component: ProductListComponent },
      { 
        path: 'products/:id', component: ProductDetailComponent,
        canActivate: [ProductDetailGuard],
      },
    ]),
    SharedModule
  ]
})
export class ProductModule { }


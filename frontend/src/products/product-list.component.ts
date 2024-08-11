import { Component, OnDestroy, OnInit } from "@angular/core";
import { IProduct } from "./product";
import { ProductService } from "./product.service";
import { Subscription } from "rxjs";

@Component({
    selector: 'pm-products',
    templateUrl: './product-list.component.html',
    styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit, OnDestroy{
    pageTitle: string = 'Product List';
    imageWidth: number = 50;
    imageMargin: number = 2;
    showImage: boolean = true;
    errorMessage = '';
    sub!: Subscription;
    private _productService;
    constructor(productService: ProductService){
        this._productService = productService;
    }
    ngOnDestroy(): void {
        this.sub.unsubscribe();
    }

    private _listFilter: string = '';
    get listFilter(): string {
        return this._listFilter;
    }
    set listFilter(value: string){
        this._listFilter = value;
        console.log(' in setter: ' + value);
        this.filteredProducts = this.performFilter(value);
    }

    filteredProducts: IProduct[] = [];
    performFilter(filterBy: string): IProduct[]{
        filterBy = filterBy.toLocaleLowerCase();
        return this.products.filter((product: IProduct) =>
        product.productName.toLocaleLowerCase().includes(filterBy));
    }

    onRatingClicked(message: string): void {
        this.pageTitle = 'Product list: ' + message;
    }
    products: IProduct[] = [];  
    
    toggleImage(): void {
        this.showImage = !this.showImage;
        console.log(this.showImage);
    }

    ngOnInit(): void {
        console.log("on init works");
        this.listFilter = '';
        this.sub = this._productService.getProducts().subscribe({
            next: products => {
                this.products = products
                this.filteredProducts = products;
            },
            error: err => this.errorMessage = err

        });
    
    }
}
import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from "@angular/core";

@Component({
    selector: 'pm-star',
    templateUrl: 'star.component.html',
    styleUrls: ['./star.component.css']
})
export class StarComponent implements OnChanges{
    @Input() rating: number = 0;
    cropWidth: number = 0;
    @Output() ratingClicked: EventEmitter<string> = 
        new EventEmitter<string>();
    ngOnChanges(changes: SimpleChanges): void {
        this.cropWidth = this.rating * 150/10;
    }

    onClick(): void{
        this.ratingClicked.emit(`The rating ${this.rating} was clicked`);
    }
}
import { Component, forwardRef } from "@angular/core";
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from "@angular/forms";

@Component({
    selector: 'checkbox-group',
    template: `<ng-content></ng-content>`,
    providers: [
        {
          provide: NG_VALUE_ACCESSOR,
          useExisting: forwardRef(() => CheckboxGroupComponent),
          multi: true
        }
    ]
})
export class CheckboxGroupComponent implements ControlValueAccessor {
    private _model: any;
    private onChange: ((m: any) => void) | undefined;
    private onTouched: ((m: any) => void) | undefined;
    get model() {
        return this._model;
    }
    writeValue(value: any): void {
        this._model = value;
    }
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }
    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }
    
    set(value: any) {
        this._model = value;
        this.onChange(this._model);
    }
}
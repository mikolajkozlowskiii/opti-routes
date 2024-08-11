import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Route, Location } from './routes.model';

@Component({
  selector: 'pm-routes',
  templateUrl: './routes.component.html',
  styleUrls: ['./routes.component.css']
})
export class RoutesComponent  {
  pageTitle: string = 'Route Creator';
  tagOptions: string[] = ['Tag1', 'Tag2', 'Tag3', 'Tag4'];
  selectedTags: string[] = [];
  routeForm: FormGroup;
  hardcodedTags = ['Tag 1', 'Tag 2', 'Tag 3'];
  route: Route = {
    description: '',
    is_public: false,
    tags: [],
    locations_steps: [],
  };


  
  constructor(private fb: FormBuilder) {
    this.routeForm = this.fb.group({
      description: ['', Validators.required],
      is_public: [false, Validators.required],
      tags: this.fb.array([]),
      locations_steps: this.fb.array([]),
    });
  }


  onSubmit(): void {
    // Tu możesz obsłużyć logikę zapisu formularza do backendu
    this.route.tags = this.selectedTags.map(tag => ({ name: tag }));
var test = this.selectedTags.map(tag => ({ name: tag }));
    console.log('teeeeest')
    console.log(test);
    console.log('Form submitted:', this.route.tags);
  }

  addLocationStep(): void {
    const newStepValue = this.locationsArray.length;
    this.locationsArray.push(this.fb.group({
      location: this.fb.group({
        name: ['', Validators.required],
        address: ['', Validators.required],
        latitude: [0, Validators.required],
        longitude: [0, Validators.required],
      }),
      step: [newStepValue, Validators.required],
    }));
  }

  removeLocationStep(index: number): void {
    this.locationsArray.removeAt(index);
  }

  get tagsArray() {
    return this.routeForm.get('tags') as FormArray;
  }

  get locationsArray() {
    return this.routeForm.get('locations_steps') as FormArray;
  }

  ngOnInit() {
    this.addTag(); // Dodaj tagi na początku, jeśli potrzebujesz

  }

  onItemSelect(item: any) {
    console.log(item);
  }
  onSelectAll(items: any) {
    console.log(items);
  }

  addTag() {
    const tags = this.routeForm.get('tags') as FormArray;

    this.tagOptions.forEach(tag => {
      tags.push(this.fb.control(false));
    });
  }
  

  // addLocationStep() {
  //   const newStepValue = this.locationsArray.length;
  //   this.locationsArray.push(this.fb.group({
  //     selected: [true],
  //     location: this.fb.group({
  //       name: ['', Validators.required],
  //       address: ['', Validators.required],
  //       latitude: [0, Validators.required],
  //       longitude: [0, Validators.required],
  //     }),
  //     step: [newStepValue, Validators.required],
  //   }));
  // }

  // removeLocationStep(index: number) {
  //   this.locationsArray.removeAt(index);
  // }

  onTagSelectionChange(selectedItems: string[]) {
    this.selectedTags = selectedItems;
    this.tagsArray.setValue(this.selectedTags);
  }

  submitForm() {
    this.onSubmit();
    const formData = this.routeForm.value;
    console.log(formData);
    // Submit logic here
  }


 get control() {
  return this.routeForm.get('tags') as FormArray;
}

get locationStepsControls() {
  return (this.routeForm.get('locations_steps') as FormArray).controls;
}
}

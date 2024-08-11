import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileSavedRoutesComponent } from './profile-saved-routes.component';

describe('ProfileSavedRoutesComponent', () => {
  let component: ProfileSavedRoutesComponent;
  let fixture: ComponentFixture<ProfileSavedRoutesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfileSavedRoutesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileSavedRoutesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileRatedRoutesComponent } from './profile-rated-routes.component';

describe('ProfileRatedRoutesComponent', () => {
  let component: ProfileRatedRoutesComponent;
  let fixture: ComponentFixture<ProfileRatedRoutesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfileRatedRoutesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileRatedRoutesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

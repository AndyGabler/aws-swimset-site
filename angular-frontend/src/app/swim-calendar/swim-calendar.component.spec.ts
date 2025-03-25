import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SwimCalendarComponent } from './swim-calendar.component';

describe('SwimCalendarComponent', () => {
  let component: SwimCalendarComponent;
  let fixture: ComponentFixture<SwimCalendarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SwimCalendarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SwimCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

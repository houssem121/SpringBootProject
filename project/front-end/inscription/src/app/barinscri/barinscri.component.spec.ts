import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BarinscriComponent } from './barinscri.component';

describe('BarinscriComponent', () => {
  let component: BarinscriComponent;
  let fixture: ComponentFixture<BarinscriComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BarinscriComponent]
    });
    fixture = TestBed.createComponent(BarinscriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

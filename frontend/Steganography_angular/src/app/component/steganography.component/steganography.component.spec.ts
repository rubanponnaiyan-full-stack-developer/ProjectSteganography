import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SteganographyComponent } from './steganography.component';

describe('SteganographyComponent', () => {
  let component: SteganographyComponent;
  let fixture: ComponentFixture<SteganographyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SteganographyComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SteganographyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

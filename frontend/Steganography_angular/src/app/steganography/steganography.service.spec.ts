import { TestBed } from '@angular/core/testing';

import { SteganographyService } from './steganography.service';

describe('SteganographyService', () => {
  let service: SteganographyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SteganographyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

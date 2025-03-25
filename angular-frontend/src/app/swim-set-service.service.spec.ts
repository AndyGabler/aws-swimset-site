import { TestBed } from '@angular/core/testing';

import { SwimSetServiceService } from './swim-set-service.service';

describe('SwimSetServiceService', () => {
  let service: SwimSetServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SwimSetServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

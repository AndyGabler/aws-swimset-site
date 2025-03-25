import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { SwimSet } from '../swimsets';
import { Observable } from 'rxjs';
import { SwimSetService } from '../swim-set-service.service';

@Component({
  selector: 'app-set-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './set-list.component.html',
  styleUrl: './set-list.component.css'
})
export class SetListComponent {
  constructor(
    private router: Router,
    private service: SwimSetService
  ) {
    this.swimSets = this.service.getSets()
  }
  
  swimSets: SwimSet[] = this.service.getSets()

  clickSet(swimSet: SwimSet) {
    this.router.navigate(['/setdetail'], { state: { selectedSet: swimSet } });
  }
}

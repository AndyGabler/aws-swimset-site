import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SwimSet } from '../swimsets';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-set-details',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './set-details.component.html',
  styleUrl: './set-details.component.css'
})
export class SetDetailsComponent {
  selectedSet: SwimSet|null = null;
  constructor(private router: Router) {
    const navigationState = router.getCurrentNavigation()?.extras?.state
    if (navigationState != null) {
      // Technically, this can still produce an error, but only if end-user is really trying
      this.selectedSet = navigationState["selectedSet"]
    }
  }
  
}

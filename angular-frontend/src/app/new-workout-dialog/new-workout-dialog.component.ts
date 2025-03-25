import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { formatDate } from '@angular/common';
import { SwimSetService } from '../swim-set-service.service';
import { SwimSet } from '../swimsets';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-new-workout-dialog',
  standalone: true,
  imports: [CommonModule, MatDialogModule, ReactiveFormsModule],
  templateUrl: './new-workout-dialog.component.html',
  styleUrl: './new-workout-dialog.component.css'
})
export class NewWorkoutDialogComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) 
    private data: any,
    private dialogRef: MatDialogRef<NewWorkoutDialogComponent>,
    private swimSetService: SwimSetService,
  ) {
    this.workoutDate = data
    this.possibleSets = this.swimSetService.getSets()
  }

  workoutDate = formatDate(new Date(), 'yyyy-MM-dd', 'en')
  possibleSets: SwimSet[] = this.swimSetService.getSets()
  selectedSet = new FormControl<number|null>(null)

  onConfirmClick(): void {
    this.dialogRef.close(this.selectedSet.value);
  }
}

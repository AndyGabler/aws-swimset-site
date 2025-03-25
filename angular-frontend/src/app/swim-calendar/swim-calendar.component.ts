
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';
import { formatDate } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ScheduledSet } from '../scheduledset';
import { NewWorkoutDialogComponent } from '../new-workout-dialog/new-workout-dialog.component';

@Component({
  selector: 'app-swim-calendar',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './swim-calendar.component.html',
  styleUrl: './swim-calendar.component.css'
})
export class SwimCalendarComponent {
  constructor(
    private client: HttpClient,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.performLookupWithDate(this.lookupDate?.value)
  }

  lookupDate = new FormControl(formatDate(new Date(), 'yyyy-MM-dd', 'en'))
  totalYards = new FormControl(0)
  setsPerformed = this.formBuilder.array([])
  areasWorked = new FormControl('')

  changeDate(event: any) {
    this.snackBar.dismiss();
    let newDate = event.target.value
    this.performLookupWithDate(newDate)
  }

  addSetPopup() {
    this.snackBar.dismiss()
    let date = formatDate(this.lookupDate.value!!, 'yyyy-MM-dd', 'en')
    const dialogRef = this.dialog.open(NewWorkoutDialogComponent, {
      data: date,
      height: '200px',
      width: '390px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null || result == "") {
        console.log("returned")
        return
      }
      
      let setId = result
      let setOrder = this.setsPerformed.length + 1
      let workoutId = -1
      this.client.get<any>("/setschedule/maxId").subscribe(result => {
        workoutId = result["maxId"] + 1

        let newWorkout = {
          "id": workoutId,
          "dateScheduled": date,
          "order": setOrder,
          "scheduledSetId": setId
        }
  
        console.log("new workout details", newWorkout)
        this.client.post("/setschedule", newWorkout, { observe: 'response'}).subscribe(result => {
          console.log("post results", result)
          this.performLookupWithDate(date)

          if (result.status == 200) {
            // TODO technically need to ensure this is a JSON of what we want
            this.snackBar.open('Set added successfully.', 'Dismiss', {duration: 2000})
          } else {
            this.snackBar.open('Error adding set.', 'Dismiss', {duration: 2000})
          }
        })
      })
    })
  }

  performLookupWithDate(date: string|null|undefined) {
    console.log(date)
    if (date == null) {
      return
    }
    
    let httpParams = new HttpParams().set("dateScheduled", date)
    let yardCounter = 0;
    let labels = new Set<string>()
    this.client.get<ScheduledSet[]>("/setschedule", {params: httpParams}).subscribe((results: ScheduledSet[]) => {
      this.setsPerformed.clear()
      results.forEach(set => {
        console.log(set)
        yardCounter += (set.scheduledSet.repCount * set.scheduledSet.repLength)
        this.setsPerformed.push(new FormControl(set.scheduledSet.name))
        set.scheduledSet.labels.forEach(label => labels.add(label))
      })
      let labelList: string[] = []
      labels.forEach(label => labelList.push(label))
      this.totalYards.setValue(yardCounter)
      this.areasWorked.setValue(labelList.join(", "))
    })
  }
}

import { Routes } from '@angular/router';
import { SetDetailsComponent } from './set-details/set-details.component';
import { SetListComponent } from './set-list/set-list.component';
import { SwimCalendarComponent } from './swim-calendar/swim-calendar.component';

export const routes: Routes = [
    {path: 'sets', component: SetListComponent},
    {path: 'schedule', component: SwimCalendarComponent},
    {path: 'setdetail', component: SetDetailsComponent},
    {path: '', redirectTo: "sets", pathMatch: 'full'}
]
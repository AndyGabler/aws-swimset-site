import { SwimSet } from "./swimsets";

export class Workout {
    id: number;
    dateScheduled: string;
    order: number;
    swimSet: SwimSet;

    constructor(id: number, dateScheduled: string, order: number, swimSet: SwimSet) {
        this.id = id;
        this.dateScheduled = dateScheduled;
        this.order = order;
        this.swimSet = swimSet;
    }
}
import { SwimSet } from "./swimsets";

export class ScheduledSet {
    id: number;
    dateScheduled: string;
    order: number;
    scheduledSet: SwimSet;

    constructor(id: number, dateScheduled: string, order: number, scheduledSet: SwimSet) {
        this.id = id;
        this.dateScheduled = dateScheduled;
        this.order = order;
        this.scheduledSet = scheduledSet;
    }
}
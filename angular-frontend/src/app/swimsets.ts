export class SwimSet {
    id: number;
    repLength: number;
    repCount: number;
    name: string;
    description: string;
    labels: string[];

    constructor(id: number, repLength: number, repCount: number, name: string, description: string, labels: string[]) {
        this.id = id;
        this.repLength = repLength;
        this.repCount = repCount;
        this.name = name;
        this.description = description;
        this.labels = labels;
    }
}

export interface IOpinion {
    id?: number;
    score?: number;
    opinion?: string;
    authorId?: number;
    bottleId?: number;
}

export class Opinion implements IOpinion {
    constructor(public id?: number, public score?: number, public opinion?: string, public authorId?: number, public bottleId?: number) {}
}

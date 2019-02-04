import { IOpinion } from 'app/shared/model/opinion.model';

export interface IPerson {
    id?: number;
    city?: string;
    age?: number;
    gender?: boolean;
    userId?: number;
    opinions?: IOpinion[];
}

export class Person implements IPerson {
    constructor(
        public id?: number,
        public city?: string,
        public age?: number,
        public gender?: boolean,
        public userId?: number,
        public opinions?: IOpinion[]
    ) {
        this.gender = this.gender || false;
    }
}

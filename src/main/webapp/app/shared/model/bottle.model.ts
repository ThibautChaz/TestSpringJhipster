import { IOpinion } from 'app/shared/model/opinion.model';

export interface IBottle {
    id?: number;
    name?: string;
    description?: string;
    age?: number;
    degree?: number;
    pictureContentType?: string;
    picture?: any;
    origin?: string;
    mouth?: string;
    nose?: string;
    rawMaterial?: string;
    opinions?: IOpinion[];
}

export class Bottle implements IBottle {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public age?: number,
        public degree?: number,
        public pictureContentType?: string,
        public picture?: any,
        public origin?: string,
        public mouth?: string,
        public nose?: string,
        public rawMaterial?: string,
        public opinions?: IOpinion[]
    ) {}
}

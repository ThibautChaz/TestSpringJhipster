import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBottle } from 'app/shared/model/bottle.model';

type EntityResponseType = HttpResponse<IBottle>;
type EntityArrayResponseType = HttpResponse<IBottle[]>;

@Injectable({ providedIn: 'root' })
export class BottleService {
    public resourceUrl = SERVER_API_URL + 'api/bottles';

    constructor(protected http: HttpClient) {}

    create(bottle: IBottle): Observable<EntityResponseType> {
        return this.http.post<IBottle>(this.resourceUrl, bottle, { observe: 'response' });
    }

    update(bottle: IBottle): Observable<EntityResponseType> {
        return this.http.put<IBottle>(this.resourceUrl, bottle, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBottle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBottle[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

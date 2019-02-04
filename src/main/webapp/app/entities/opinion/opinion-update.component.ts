import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOpinion } from 'app/shared/model/opinion.model';
import { OpinionService } from './opinion.service';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';
import { IBottle } from 'app/shared/model/bottle.model';
import { BottleService } from 'app/entities/bottle';

@Component({
    selector: 'jhi-opinion-update',
    templateUrl: './opinion-update.component.html'
})
export class OpinionUpdateComponent implements OnInit {
    opinion: IOpinion;
    isSaving: boolean;

    people: IPerson[];

    bottles: IBottle[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected opinionService: OpinionService,
        protected personService: PersonService,
        protected bottleService: BottleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ opinion }) => {
            this.opinion = opinion;
        });
        this.personService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPerson[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPerson[]>) => response.body)
            )
            .subscribe((res: IPerson[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.bottleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBottle[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBottle[]>) => response.body)
            )
            .subscribe((res: IBottle[]) => (this.bottles = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.opinion.id !== undefined) {
            this.subscribeToSaveResponse(this.opinionService.update(this.opinion));
        } else {
            this.subscribeToSaveResponse(this.opinionService.create(this.opinion));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOpinion>>) {
        result.subscribe((res: HttpResponse<IOpinion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPersonById(index: number, item: IPerson) {
        return item.id;
    }

    trackBottleById(index: number, item: IBottle) {
        return item.id;
    }
}

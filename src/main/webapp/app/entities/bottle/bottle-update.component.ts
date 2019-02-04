import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiDataUtils } from 'ng-jhipster';
import { IBottle } from 'app/shared/model/bottle.model';
import { BottleService } from './bottle.service';

@Component({
    selector: 'jhi-bottle-update',
    templateUrl: './bottle-update.component.html'
})
export class BottleUpdateComponent implements OnInit {
    bottle: IBottle;
    isSaving: boolean;

    constructor(protected dataUtils: JhiDataUtils, protected bottleService: BottleService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bottle }) => {
            this.bottle = bottle;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bottle.id !== undefined) {
            this.subscribeToSaveResponse(this.bottleService.update(this.bottle));
        } else {
            this.subscribeToSaveResponse(this.bottleService.create(this.bottle));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBottle>>) {
        result.subscribe((res: HttpResponse<IBottle>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

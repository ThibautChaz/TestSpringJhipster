import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IBottle } from 'app/shared/model/bottle.model';
import { AccountService } from 'app/core';
import { BottleService } from './bottle.service';

@Component({
    selector: 'jhi-bottle',
    templateUrl: './bottle.component.html'
})
export class BottleComponent implements OnInit, OnDestroy {
    bottles: IBottle[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected bottleService: BottleService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.bottleService
            .query()
            .pipe(
                filter((res: HttpResponse<IBottle[]>) => res.ok),
                map((res: HttpResponse<IBottle[]>) => res.body)
            )
            .subscribe(
                (res: IBottle[]) => {
                    this.bottles = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBottles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBottle) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInBottles() {
        this.eventSubscriber = this.eventManager.subscribe('bottleListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

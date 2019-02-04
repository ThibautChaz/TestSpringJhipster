import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IBottle } from 'app/shared/model/bottle.model';

@Component({
    selector: 'jhi-bottle-detail',
    templateUrl: './bottle-detail.component.html'
})
export class BottleDetailComponent implements OnInit {
    bottle: IBottle;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}

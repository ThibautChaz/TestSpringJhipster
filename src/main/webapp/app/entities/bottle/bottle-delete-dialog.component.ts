import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBottle } from 'app/shared/model/bottle.model';
import { BottleService } from './bottle.service';

@Component({
    selector: 'jhi-bottle-delete-dialog',
    templateUrl: './bottle-delete-dialog.component.html'
})
export class BottleDeleteDialogComponent {
    bottle: IBottle;

    constructor(protected bottleService: BottleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bottleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bottleListModification',
                content: 'Deleted an bottle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bottle-delete-popup',
    template: ''
})
export class BottleDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bottle }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BottleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.bottle = bottle;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/bottle', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/bottle', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

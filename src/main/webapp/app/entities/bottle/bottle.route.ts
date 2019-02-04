import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Bottle } from 'app/shared/model/bottle.model';
import { BottleService } from './bottle.service';
import { BottleComponent } from './bottle.component';
import { BottleDetailComponent } from './bottle-detail.component';
import { BottleUpdateComponent } from './bottle-update.component';
import { BottleDeletePopupComponent } from './bottle-delete-dialog.component';
import { IBottle } from 'app/shared/model/bottle.model';

@Injectable({ providedIn: 'root' })
export class BottleResolve implements Resolve<IBottle> {
    constructor(private service: BottleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBottle> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Bottle>) => response.ok),
                map((bottle: HttpResponse<Bottle>) => bottle.body)
            );
        }
        return of(new Bottle());
    }
}

export const bottleRoute: Routes = [
    {
        path: '',
        component: BottleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'trafalbarApp.bottle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BottleDetailComponent,
        resolve: {
            bottle: BottleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'trafalbarApp.bottle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BottleUpdateComponent,
        resolve: {
            bottle: BottleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'trafalbarApp.bottle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BottleUpdateComponent,
        resolve: {
            bottle: BottleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'trafalbarApp.bottle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bottlePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BottleDeletePopupComponent,
        resolve: {
            bottle: BottleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'trafalbarApp.bottle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

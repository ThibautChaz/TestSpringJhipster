/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrafalbarTestModule } from '../../../test.module';
import { BottleDetailComponent } from 'app/entities/bottle/bottle-detail.component';
import { Bottle } from 'app/shared/model/bottle.model';

describe('Component Tests', () => {
    describe('Bottle Management Detail Component', () => {
        let comp: BottleDetailComponent;
        let fixture: ComponentFixture<BottleDetailComponent>;
        const route = ({ data: of({ bottle: new Bottle(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrafalbarTestModule],
                declarations: [BottleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BottleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BottleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bottle).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

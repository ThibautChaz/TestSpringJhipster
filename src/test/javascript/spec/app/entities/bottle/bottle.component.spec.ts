/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TrafalbarTestModule } from '../../../test.module';
import { BottleComponent } from 'app/entities/bottle/bottle.component';
import { BottleService } from 'app/entities/bottle/bottle.service';
import { Bottle } from 'app/shared/model/bottle.model';

describe('Component Tests', () => {
    describe('Bottle Management Component', () => {
        let comp: BottleComponent;
        let fixture: ComponentFixture<BottleComponent>;
        let service: BottleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrafalbarTestModule],
                declarations: [BottleComponent],
                providers: []
            })
                .overrideTemplate(BottleComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BottleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BottleService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Bottle(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bottles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

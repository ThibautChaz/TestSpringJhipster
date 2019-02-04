/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TrafalbarTestModule } from '../../../test.module';
import { BottleUpdateComponent } from 'app/entities/bottle/bottle-update.component';
import { BottleService } from 'app/entities/bottle/bottle.service';
import { Bottle } from 'app/shared/model/bottle.model';

describe('Component Tests', () => {
    describe('Bottle Management Update Component', () => {
        let comp: BottleUpdateComponent;
        let fixture: ComponentFixture<BottleUpdateComponent>;
        let service: BottleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrafalbarTestModule],
                declarations: [BottleUpdateComponent]
            })
                .overrideTemplate(BottleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BottleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BottleService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Bottle(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.bottle = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Bottle();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.bottle = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});

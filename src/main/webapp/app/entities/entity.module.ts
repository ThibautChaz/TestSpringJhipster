import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'person',
                loadChildren: './person/person.module#TrafalbarPersonModule'
            },
            {
                path: 'bottle',
                loadChildren: './bottle/bottle.module#TrafalbarBottleModule'
            },
            {
                path: 'opinion',
                loadChildren: './opinion/opinion.module#TrafalbarOpinionModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrafalbarEntityModule {}

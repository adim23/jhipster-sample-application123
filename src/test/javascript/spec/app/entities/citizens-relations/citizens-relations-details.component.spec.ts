/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CitizensRelationsDetailComponent from '@/entities/citizens-relations/citizens-relations-details.vue';
import CitizensRelationsClass from '@/entities/citizens-relations/citizens-relations-details.component';
import CitizensRelationsService from '@/entities/citizens-relations/citizens-relations.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CitizensRelations Management Detail Component', () => {
    let wrapper: Wrapper<CitizensRelationsClass>;
    let comp: CitizensRelationsClass;
    let citizensRelationsServiceStub: SinonStubbedInstance<CitizensRelationsService>;

    beforeEach(() => {
      citizensRelationsServiceStub = sinon.createStubInstance<CitizensRelationsService>(CitizensRelationsService);

      wrapper = shallowMount<CitizensRelationsClass>(CitizensRelationsDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { citizensRelationsService: () => citizensRelationsServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCitizensRelations = { id: 123 };
        citizensRelationsServiceStub.find.resolves(foundCitizensRelations);

        // WHEN
        comp.retrieveCitizensRelations(123);
        await comp.$nextTick();

        // THEN
        expect(comp.citizensRelations).toBe(foundCitizensRelations);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCitizensRelations = { id: 123 };
        citizensRelationsServiceStub.find.resolves(foundCitizensRelations);

        // WHEN
        comp.beforeRouteEnter({ params: { citizensRelationsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.citizensRelations).toBe(foundCitizensRelations);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});

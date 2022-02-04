/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CitizensMeetingsDetailComponent from '@/entities/citizens-meetings/citizens-meetings-details.vue';
import CitizensMeetingsClass from '@/entities/citizens-meetings/citizens-meetings-details.component';
import CitizensMeetingsService from '@/entities/citizens-meetings/citizens-meetings.service';
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
  describe('CitizensMeetings Management Detail Component', () => {
    let wrapper: Wrapper<CitizensMeetingsClass>;
    let comp: CitizensMeetingsClass;
    let citizensMeetingsServiceStub: SinonStubbedInstance<CitizensMeetingsService>;

    beforeEach(() => {
      citizensMeetingsServiceStub = sinon.createStubInstance<CitizensMeetingsService>(CitizensMeetingsService);

      wrapper = shallowMount<CitizensMeetingsClass>(CitizensMeetingsDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { citizensMeetingsService: () => citizensMeetingsServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCitizensMeetings = { id: 123 };
        citizensMeetingsServiceStub.find.resolves(foundCitizensMeetings);

        // WHEN
        comp.retrieveCitizensMeetings(123);
        await comp.$nextTick();

        // THEN
        expect(comp.citizensMeetings).toBe(foundCitizensMeetings);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCitizensMeetings = { id: 123 };
        citizensMeetingsServiceStub.find.resolves(foundCitizensMeetings);

        // WHEN
        comp.beforeRouteEnter({ params: { citizensMeetingsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.citizensMeetings).toBe(foundCitizensMeetings);
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

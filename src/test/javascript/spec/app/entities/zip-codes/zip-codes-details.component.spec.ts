/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ZipCodesDetailComponent from '@/entities/zip-codes/zip-codes-details.vue';
import ZipCodesClass from '@/entities/zip-codes/zip-codes-details.component';
import ZipCodesService from '@/entities/zip-codes/zip-codes.service';
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
  describe('ZipCodes Management Detail Component', () => {
    let wrapper: Wrapper<ZipCodesClass>;
    let comp: ZipCodesClass;
    let zipCodesServiceStub: SinonStubbedInstance<ZipCodesService>;

    beforeEach(() => {
      zipCodesServiceStub = sinon.createStubInstance<ZipCodesService>(ZipCodesService);

      wrapper = shallowMount<ZipCodesClass>(ZipCodesDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { zipCodesService: () => zipCodesServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundZipCodes = { id: 123 };
        zipCodesServiceStub.find.resolves(foundZipCodes);

        // WHEN
        comp.retrieveZipCodes(123);
        await comp.$nextTick();

        // THEN
        expect(comp.zipCodes).toBe(foundZipCodes);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundZipCodes = { id: 123 };
        zipCodesServiceStub.find.resolves(foundZipCodes);

        // WHEN
        comp.beforeRouteEnter({ params: { zipCodesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.zipCodes).toBe(foundZipCodes);
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

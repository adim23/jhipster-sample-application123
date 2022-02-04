/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ContactTypesDetailComponent from '@/entities/contact-types/contact-types-details.vue';
import ContactTypesClass from '@/entities/contact-types/contact-types-details.component';
import ContactTypesService from '@/entities/contact-types/contact-types.service';
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
  describe('ContactTypes Management Detail Component', () => {
    let wrapper: Wrapper<ContactTypesClass>;
    let comp: ContactTypesClass;
    let contactTypesServiceStub: SinonStubbedInstance<ContactTypesService>;

    beforeEach(() => {
      contactTypesServiceStub = sinon.createStubInstance<ContactTypesService>(ContactTypesService);

      wrapper = shallowMount<ContactTypesClass>(ContactTypesDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { contactTypesService: () => contactTypesServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundContactTypes = { id: 123 };
        contactTypesServiceStub.find.resolves(foundContactTypes);

        // WHEN
        comp.retrieveContactTypes(123);
        await comp.$nextTick();

        // THEN
        expect(comp.contactTypes).toBe(foundContactTypes);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundContactTypes = { id: 123 };
        contactTypesServiceStub.find.resolves(foundContactTypes);

        // WHEN
        comp.beforeRouteEnter({ params: { contactTypesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.contactTypes).toBe(foundContactTypes);
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

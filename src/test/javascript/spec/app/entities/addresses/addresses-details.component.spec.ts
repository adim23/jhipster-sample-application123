/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AddressesDetailComponent from '@/entities/addresses/addresses-details.vue';
import AddressesClass from '@/entities/addresses/addresses-details.component';
import AddressesService from '@/entities/addresses/addresses.service';
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
  describe('Addresses Management Detail Component', () => {
    let wrapper: Wrapper<AddressesClass>;
    let comp: AddressesClass;
    let addressesServiceStub: SinonStubbedInstance<AddressesService>;

    beforeEach(() => {
      addressesServiceStub = sinon.createStubInstance<AddressesService>(AddressesService);

      wrapper = shallowMount<AddressesClass>(AddressesDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { addressesService: () => addressesServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAddresses = { id: 123 };
        addressesServiceStub.find.resolves(foundAddresses);

        // WHEN
        comp.retrieveAddresses(123);
        await comp.$nextTick();

        // THEN
        expect(comp.addresses).toBe(foundAddresses);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAddresses = { id: 123 };
        addressesServiceStub.find.resolves(foundAddresses);

        // WHEN
        comp.beforeRouteEnter({ params: { addressesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.addresses).toBe(foundAddresses);
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

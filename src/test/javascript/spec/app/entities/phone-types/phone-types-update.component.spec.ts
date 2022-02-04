/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PhoneTypesUpdateComponent from '@/entities/phone-types/phone-types-update.vue';
import PhoneTypesClass from '@/entities/phone-types/phone-types-update.component';
import PhoneTypesService from '@/entities/phone-types/phone-types.service';

import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('PhoneTypes Management Update Component', () => {
    let wrapper: Wrapper<PhoneTypesClass>;
    let comp: PhoneTypesClass;
    let phoneTypesServiceStub: SinonStubbedInstance<PhoneTypesService>;

    beforeEach(() => {
      phoneTypesServiceStub = sinon.createStubInstance<PhoneTypesService>(PhoneTypesService);

      wrapper = shallowMount<PhoneTypesClass>(PhoneTypesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          phoneTypesService: () => phoneTypesServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.phoneTypes = entity;
        phoneTypesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(phoneTypesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.phoneTypes = entity;
        phoneTypesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(phoneTypesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPhoneTypes = { id: 123 };
        phoneTypesServiceStub.find.resolves(foundPhoneTypes);
        phoneTypesServiceStub.retrieve.resolves([foundPhoneTypes]);

        // WHEN
        comp.beforeRouteEnter({ params: { phoneTypesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.phoneTypes).toBe(foundPhoneTypes);
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

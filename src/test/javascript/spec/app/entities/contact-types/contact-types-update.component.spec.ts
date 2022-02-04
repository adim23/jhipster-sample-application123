/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ContactTypesUpdateComponent from '@/entities/contact-types/contact-types-update.vue';
import ContactTypesClass from '@/entities/contact-types/contact-types-update.component';
import ContactTypesService from '@/entities/contact-types/contact-types.service';

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
  describe('ContactTypes Management Update Component', () => {
    let wrapper: Wrapper<ContactTypesClass>;
    let comp: ContactTypesClass;
    let contactTypesServiceStub: SinonStubbedInstance<ContactTypesService>;

    beforeEach(() => {
      contactTypesServiceStub = sinon.createStubInstance<ContactTypesService>(ContactTypesService);

      wrapper = shallowMount<ContactTypesClass>(ContactTypesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          contactTypesService: () => contactTypesServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.contactTypes = entity;
        contactTypesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(contactTypesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.contactTypes = entity;
        contactTypesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(contactTypesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundContactTypes = { id: 123 };
        contactTypesServiceStub.find.resolves(foundContactTypes);
        contactTypesServiceStub.retrieve.resolves([foundContactTypes]);

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

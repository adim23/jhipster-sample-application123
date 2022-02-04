/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CitizensRelationsUpdateComponent from '@/entities/citizens-relations/citizens-relations-update.vue';
import CitizensRelationsClass from '@/entities/citizens-relations/citizens-relations-update.component';
import CitizensRelationsService from '@/entities/citizens-relations/citizens-relations.service';

import CitizensService from '@/entities/citizens/citizens.service';
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
  describe('CitizensRelations Management Update Component', () => {
    let wrapper: Wrapper<CitizensRelationsClass>;
    let comp: CitizensRelationsClass;
    let citizensRelationsServiceStub: SinonStubbedInstance<CitizensRelationsService>;

    beforeEach(() => {
      citizensRelationsServiceStub = sinon.createStubInstance<CitizensRelationsService>(CitizensRelationsService);

      wrapper = shallowMount<CitizensRelationsClass>(CitizensRelationsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          citizensRelationsService: () => citizensRelationsServiceStub,
          alertService: () => new AlertService(),

          citizensService: () =>
            sinon.createStubInstance<CitizensService>(CitizensService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.citizensRelations = entity;
        citizensRelationsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(citizensRelationsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.citizensRelations = entity;
        citizensRelationsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(citizensRelationsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCitizensRelations = { id: 123 };
        citizensRelationsServiceStub.find.resolves(foundCitizensRelations);
        citizensRelationsServiceStub.retrieve.resolves([foundCitizensRelations]);

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

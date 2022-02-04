/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CitizenFoldersUpdateComponent from '@/entities/citizen-folders/citizen-folders-update.vue';
import CitizenFoldersClass from '@/entities/citizen-folders/citizen-folders-update.component';
import CitizenFoldersService from '@/entities/citizen-folders/citizen-folders.service';

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
  describe('CitizenFolders Management Update Component', () => {
    let wrapper: Wrapper<CitizenFoldersClass>;
    let comp: CitizenFoldersClass;
    let citizenFoldersServiceStub: SinonStubbedInstance<CitizenFoldersService>;

    beforeEach(() => {
      citizenFoldersServiceStub = sinon.createStubInstance<CitizenFoldersService>(CitizenFoldersService);

      wrapper = shallowMount<CitizenFoldersClass>(CitizenFoldersUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          citizenFoldersService: () => citizenFoldersServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.citizenFolders = entity;
        citizenFoldersServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(citizenFoldersServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.citizenFolders = entity;
        citizenFoldersServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(citizenFoldersServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCitizenFolders = { id: 123 };
        citizenFoldersServiceStub.find.resolves(foundCitizenFolders);
        citizenFoldersServiceStub.retrieve.resolves([foundCitizenFolders]);

        // WHEN
        comp.beforeRouteEnter({ params: { citizenFoldersId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.citizenFolders).toBe(foundCitizenFolders);
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

/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import FilesUpdateComponent from '@/entities/files/files-update.vue';
import FilesClass from '@/entities/files/files-update.component';
import FilesService from '@/entities/files/files.service';

import UserService from '@/entities/user/user.service';
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
  describe('Files Management Update Component', () => {
    let wrapper: Wrapper<FilesClass>;
    let comp: FilesClass;
    let filesServiceStub: SinonStubbedInstance<FilesService>;

    beforeEach(() => {
      filesServiceStub = sinon.createStubInstance<FilesService>(FilesService);

      wrapper = shallowMount<FilesClass>(FilesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          filesService: () => filesServiceStub,
          alertService: () => new AlertService(),

          userService: () => new UserService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.files = entity;
        filesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(filesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.files = entity;
        filesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(filesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFiles = { id: 123 };
        filesServiceStub.find.resolves(foundFiles);
        filesServiceStub.retrieve.resolves([foundFiles]);

        // WHEN
        comp.beforeRouteEnter({ params: { filesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.files).toBe(foundFiles);
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

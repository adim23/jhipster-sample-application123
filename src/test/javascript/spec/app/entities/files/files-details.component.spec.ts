/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FilesDetailComponent from '@/entities/files/files-details.vue';
import FilesClass from '@/entities/files/files-details.component';
import FilesService from '@/entities/files/files.service';
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
  describe('Files Management Detail Component', () => {
    let wrapper: Wrapper<FilesClass>;
    let comp: FilesClass;
    let filesServiceStub: SinonStubbedInstance<FilesService>;

    beforeEach(() => {
      filesServiceStub = sinon.createStubInstance<FilesService>(FilesService);

      wrapper = shallowMount<FilesClass>(FilesDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { filesService: () => filesServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFiles = { id: 123 };
        filesServiceStub.find.resolves(foundFiles);

        // WHEN
        comp.retrieveFiles(123);
        await comp.$nextTick();

        // THEN
        expect(comp.files).toBe(foundFiles);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFiles = { id: 123 };
        filesServiceStub.find.resolves(foundFiles);

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

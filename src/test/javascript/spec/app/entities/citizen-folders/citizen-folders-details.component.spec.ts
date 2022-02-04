/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CitizenFoldersDetailComponent from '@/entities/citizen-folders/citizen-folders-details.vue';
import CitizenFoldersClass from '@/entities/citizen-folders/citizen-folders-details.component';
import CitizenFoldersService from '@/entities/citizen-folders/citizen-folders.service';
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
  describe('CitizenFolders Management Detail Component', () => {
    let wrapper: Wrapper<CitizenFoldersClass>;
    let comp: CitizenFoldersClass;
    let citizenFoldersServiceStub: SinonStubbedInstance<CitizenFoldersService>;

    beforeEach(() => {
      citizenFoldersServiceStub = sinon.createStubInstance<CitizenFoldersService>(CitizenFoldersService);

      wrapper = shallowMount<CitizenFoldersClass>(CitizenFoldersDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { citizenFoldersService: () => citizenFoldersServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCitizenFolders = { id: 123 };
        citizenFoldersServiceStub.find.resolves(foundCitizenFolders);

        // WHEN
        comp.retrieveCitizenFolders(123);
        await comp.$nextTick();

        // THEN
        expect(comp.citizenFolders).toBe(foundCitizenFolders);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCitizenFolders = { id: 123 };
        citizenFoldersServiceStub.find.resolves(foundCitizenFolders);

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

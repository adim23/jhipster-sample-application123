/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CitizenFoldersComponent from '@/entities/citizen-folders/citizen-folders.vue';
import CitizenFoldersClass from '@/entities/citizen-folders/citizen-folders.component';
import CitizenFoldersService from '@/entities/citizen-folders/citizen-folders.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('CitizenFolders Management Component', () => {
    let wrapper: Wrapper<CitizenFoldersClass>;
    let comp: CitizenFoldersClass;
    let citizenFoldersServiceStub: SinonStubbedInstance<CitizenFoldersService>;

    beforeEach(() => {
      citizenFoldersServiceStub = sinon.createStubInstance<CitizenFoldersService>(CitizenFoldersService);
      citizenFoldersServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CitizenFoldersClass>(CitizenFoldersComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          citizenFoldersService: () => citizenFoldersServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      citizenFoldersServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCitizenFolderss();
      await comp.$nextTick();

      // THEN
      expect(citizenFoldersServiceStub.retrieve.called).toBeTruthy();
      expect(comp.citizenFolders[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      citizenFoldersServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(citizenFoldersServiceStub.retrieve.called).toBeTruthy();
      expect(comp.citizenFolders[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      citizenFoldersServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(citizenFoldersServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      citizenFoldersServiceStub.retrieve.reset();
      citizenFoldersServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(citizenFoldersServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.citizenFolders[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      citizenFoldersServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(citizenFoldersServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCitizenFolders();
      await comp.$nextTick();

      // THEN
      expect(citizenFoldersServiceStub.delete.called).toBeTruthy();
      expect(citizenFoldersServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

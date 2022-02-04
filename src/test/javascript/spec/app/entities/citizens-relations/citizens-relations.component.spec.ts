/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CitizensRelationsComponent from '@/entities/citizens-relations/citizens-relations.vue';
import CitizensRelationsClass from '@/entities/citizens-relations/citizens-relations.component';
import CitizensRelationsService from '@/entities/citizens-relations/citizens-relations.service';
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
  describe('CitizensRelations Management Component', () => {
    let wrapper: Wrapper<CitizensRelationsClass>;
    let comp: CitizensRelationsClass;
    let citizensRelationsServiceStub: SinonStubbedInstance<CitizensRelationsService>;

    beforeEach(() => {
      citizensRelationsServiceStub = sinon.createStubInstance<CitizensRelationsService>(CitizensRelationsService);
      citizensRelationsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CitizensRelationsClass>(CitizensRelationsComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          citizensRelationsService: () => citizensRelationsServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      citizensRelationsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCitizensRelationss();
      await comp.$nextTick();

      // THEN
      expect(citizensRelationsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.citizensRelations[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      citizensRelationsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(citizensRelationsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.citizensRelations[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      citizensRelationsServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(citizensRelationsServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      citizensRelationsServiceStub.retrieve.reset();
      citizensRelationsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(citizensRelationsServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.citizensRelations[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      citizensRelationsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(citizensRelationsServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCitizensRelations();
      await comp.$nextTick();

      // THEN
      expect(citizensRelationsServiceStub.delete.called).toBeTruthy();
      expect(citizensRelationsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

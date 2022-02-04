/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CitizensMeetingsComponent from '@/entities/citizens-meetings/citizens-meetings.vue';
import CitizensMeetingsClass from '@/entities/citizens-meetings/citizens-meetings.component';
import CitizensMeetingsService from '@/entities/citizens-meetings/citizens-meetings.service';
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
  describe('CitizensMeetings Management Component', () => {
    let wrapper: Wrapper<CitizensMeetingsClass>;
    let comp: CitizensMeetingsClass;
    let citizensMeetingsServiceStub: SinonStubbedInstance<CitizensMeetingsService>;

    beforeEach(() => {
      citizensMeetingsServiceStub = sinon.createStubInstance<CitizensMeetingsService>(CitizensMeetingsService);
      citizensMeetingsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CitizensMeetingsClass>(CitizensMeetingsComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          citizensMeetingsService: () => citizensMeetingsServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      citizensMeetingsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCitizensMeetingss();
      await comp.$nextTick();

      // THEN
      expect(citizensMeetingsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.citizensMeetings[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      citizensMeetingsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(citizensMeetingsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.citizensMeetings[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      citizensMeetingsServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(citizensMeetingsServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      citizensMeetingsServiceStub.retrieve.reset();
      citizensMeetingsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(citizensMeetingsServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.citizensMeetings[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      citizensMeetingsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(citizensMeetingsServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCitizensMeetings();
      await comp.$nextTick();

      // THEN
      expect(citizensMeetingsServiceStub.delete.called).toBeTruthy();
      expect(citizensMeetingsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import JobsUpdateComponent from '@/entities/jobs/jobs-update.vue';
import JobsClass from '@/entities/jobs/jobs-update.component';
import JobsService from '@/entities/jobs/jobs.service';

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
  describe('Jobs Management Update Component', () => {
    let wrapper: Wrapper<JobsClass>;
    let comp: JobsClass;
    let jobsServiceStub: SinonStubbedInstance<JobsService>;

    beforeEach(() => {
      jobsServiceStub = sinon.createStubInstance<JobsService>(JobsService);

      wrapper = shallowMount<JobsClass>(JobsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          jobsService: () => jobsServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.jobs = entity;
        jobsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(jobsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.jobs = entity;
        jobsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(jobsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundJobs = { id: 123 };
        jobsServiceStub.find.resolves(foundJobs);
        jobsServiceStub.retrieve.resolves([foundJobs]);

        // WHEN
        comp.beforeRouteEnter({ params: { jobsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.jobs).toBe(foundJobs);
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

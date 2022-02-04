/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import JobsDetailComponent from '@/entities/jobs/jobs-details.vue';
import JobsClass from '@/entities/jobs/jobs-details.component';
import JobsService from '@/entities/jobs/jobs.service';
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
  describe('Jobs Management Detail Component', () => {
    let wrapper: Wrapper<JobsClass>;
    let comp: JobsClass;
    let jobsServiceStub: SinonStubbedInstance<JobsService>;

    beforeEach(() => {
      jobsServiceStub = sinon.createStubInstance<JobsService>(JobsService);

      wrapper = shallowMount<JobsClass>(JobsDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { jobsService: () => jobsServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundJobs = { id: 123 };
        jobsServiceStub.find.resolves(foundJobs);

        // WHEN
        comp.retrieveJobs(123);
        await comp.$nextTick();

        // THEN
        expect(comp.jobs).toBe(foundJobs);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundJobs = { id: 123 };
        jobsServiceStub.find.resolves(foundJobs);

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

/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TeamsDetailComponent from '@/entities/teams/teams-details.vue';
import TeamsClass from '@/entities/teams/teams-details.component';
import TeamsService from '@/entities/teams/teams.service';
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
  describe('Teams Management Detail Component', () => {
    let wrapper: Wrapper<TeamsClass>;
    let comp: TeamsClass;
    let teamsServiceStub: SinonStubbedInstance<TeamsService>;

    beforeEach(() => {
      teamsServiceStub = sinon.createStubInstance<TeamsService>(TeamsService);

      wrapper = shallowMount<TeamsClass>(TeamsDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { teamsService: () => teamsServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTeams = { id: 123 };
        teamsServiceStub.find.resolves(foundTeams);

        // WHEN
        comp.retrieveTeams(123);
        await comp.$nextTick();

        // THEN
        expect(comp.teams).toBe(foundTeams);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTeams = { id: 123 };
        teamsServiceStub.find.resolves(foundTeams);

        // WHEN
        comp.beforeRouteEnter({ params: { teamsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.teams).toBe(foundTeams);
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

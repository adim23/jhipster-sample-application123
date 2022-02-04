/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SocialKindsDetailComponent from '@/entities/social-kinds/social-kinds-details.vue';
import SocialKindsClass from '@/entities/social-kinds/social-kinds-details.component';
import SocialKindsService from '@/entities/social-kinds/social-kinds.service';
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
  describe('SocialKinds Management Detail Component', () => {
    let wrapper: Wrapper<SocialKindsClass>;
    let comp: SocialKindsClass;
    let socialKindsServiceStub: SinonStubbedInstance<SocialKindsService>;

    beforeEach(() => {
      socialKindsServiceStub = sinon.createStubInstance<SocialKindsService>(SocialKindsService);

      wrapper = shallowMount<SocialKindsClass>(SocialKindsDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { socialKindsService: () => socialKindsServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSocialKinds = { id: 123 };
        socialKindsServiceStub.find.resolves(foundSocialKinds);

        // WHEN
        comp.retrieveSocialKinds(123);
        await comp.$nextTick();

        // THEN
        expect(comp.socialKinds).toBe(foundSocialKinds);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSocialKinds = { id: 123 };
        socialKindsServiceStub.find.resolves(foundSocialKinds);

        // WHEN
        comp.beforeRouteEnter({ params: { socialKindsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.socialKinds).toBe(foundSocialKinds);
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

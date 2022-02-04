/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SocialKindsUpdateComponent from '@/entities/social-kinds/social-kinds-update.vue';
import SocialKindsClass from '@/entities/social-kinds/social-kinds-update.component';
import SocialKindsService from '@/entities/social-kinds/social-kinds.service';

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
  describe('SocialKinds Management Update Component', () => {
    let wrapper: Wrapper<SocialKindsClass>;
    let comp: SocialKindsClass;
    let socialKindsServiceStub: SinonStubbedInstance<SocialKindsService>;

    beforeEach(() => {
      socialKindsServiceStub = sinon.createStubInstance<SocialKindsService>(SocialKindsService);

      wrapper = shallowMount<SocialKindsClass>(SocialKindsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          socialKindsService: () => socialKindsServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.socialKinds = entity;
        socialKindsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(socialKindsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.socialKinds = entity;
        socialKindsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(socialKindsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSocialKinds = { id: 123 };
        socialKindsServiceStub.find.resolves(foundSocialKinds);
        socialKindsServiceStub.retrieve.resolves([foundSocialKinds]);

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

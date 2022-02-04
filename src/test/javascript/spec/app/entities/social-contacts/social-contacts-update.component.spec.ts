/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SocialContactsUpdateComponent from '@/entities/social-contacts/social-contacts-update.vue';
import SocialContactsClass from '@/entities/social-contacts/social-contacts-update.component';
import SocialContactsService from '@/entities/social-contacts/social-contacts.service';

import SocialKindsService from '@/entities/social-kinds/social-kinds.service';

import ContactTypesService from '@/entities/contact-types/contact-types.service';

import CitizensService from '@/entities/citizens/citizens.service';
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
  describe('SocialContacts Management Update Component', () => {
    let wrapper: Wrapper<SocialContactsClass>;
    let comp: SocialContactsClass;
    let socialContactsServiceStub: SinonStubbedInstance<SocialContactsService>;

    beforeEach(() => {
      socialContactsServiceStub = sinon.createStubInstance<SocialContactsService>(SocialContactsService);

      wrapper = shallowMount<SocialContactsClass>(SocialContactsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          socialContactsService: () => socialContactsServiceStub,
          alertService: () => new AlertService(),

          socialKindsService: () =>
            sinon.createStubInstance<SocialKindsService>(SocialKindsService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          contactTypesService: () =>
            sinon.createStubInstance<ContactTypesService>(ContactTypesService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          citizensService: () =>
            sinon.createStubInstance<CitizensService>(CitizensService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.socialContacts = entity;
        socialContactsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(socialContactsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.socialContacts = entity;
        socialContactsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(socialContactsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSocialContacts = { id: 123 };
        socialContactsServiceStub.find.resolves(foundSocialContacts);
        socialContactsServiceStub.retrieve.resolves([foundSocialContacts]);

        // WHEN
        comp.beforeRouteEnter({ params: { socialContactsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.socialContacts).toBe(foundSocialContacts);
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

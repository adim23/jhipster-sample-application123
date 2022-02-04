/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import EmailsUpdateComponent from '@/entities/emails/emails-update.vue';
import EmailsClass from '@/entities/emails/emails-update.component';
import EmailsService from '@/entities/emails/emails.service';

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
  describe('Emails Management Update Component', () => {
    let wrapper: Wrapper<EmailsClass>;
    let comp: EmailsClass;
    let emailsServiceStub: SinonStubbedInstance<EmailsService>;

    beforeEach(() => {
      emailsServiceStub = sinon.createStubInstance<EmailsService>(EmailsService);

      wrapper = shallowMount<EmailsClass>(EmailsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          emailsService: () => emailsServiceStub,
          alertService: () => new AlertService(),

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
        comp.emails = entity;
        emailsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(emailsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.emails = entity;
        emailsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(emailsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEmails = { id: 123 };
        emailsServiceStub.find.resolves(foundEmails);
        emailsServiceStub.retrieve.resolves([foundEmails]);

        // WHEN
        comp.beforeRouteEnter({ params: { emailsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.emails).toBe(foundEmails);
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

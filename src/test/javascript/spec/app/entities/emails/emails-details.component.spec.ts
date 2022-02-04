/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import EmailsDetailComponent from '@/entities/emails/emails-details.vue';
import EmailsClass from '@/entities/emails/emails-details.component';
import EmailsService from '@/entities/emails/emails.service';
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
  describe('Emails Management Detail Component', () => {
    let wrapper: Wrapper<EmailsClass>;
    let comp: EmailsClass;
    let emailsServiceStub: SinonStubbedInstance<EmailsService>;

    beforeEach(() => {
      emailsServiceStub = sinon.createStubInstance<EmailsService>(EmailsService);

      wrapper = shallowMount<EmailsClass>(EmailsDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { emailsService: () => emailsServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEmails = { id: 123 };
        emailsServiceStub.find.resolves(foundEmails);

        // WHEN
        comp.retrieveEmails(123);
        await comp.$nextTick();

        // THEN
        expect(comp.emails).toBe(foundEmails);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEmails = { id: 123 };
        emailsServiceStub.find.resolves(foundEmails);

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

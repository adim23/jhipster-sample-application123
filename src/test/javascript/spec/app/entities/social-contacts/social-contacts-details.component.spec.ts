/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SocialContactsDetailComponent from '@/entities/social-contacts/social-contacts-details.vue';
import SocialContactsClass from '@/entities/social-contacts/social-contacts-details.component';
import SocialContactsService from '@/entities/social-contacts/social-contacts.service';
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
  describe('SocialContacts Management Detail Component', () => {
    let wrapper: Wrapper<SocialContactsClass>;
    let comp: SocialContactsClass;
    let socialContactsServiceStub: SinonStubbedInstance<SocialContactsService>;

    beforeEach(() => {
      socialContactsServiceStub = sinon.createStubInstance<SocialContactsService>(SocialContactsService);

      wrapper = shallowMount<SocialContactsClass>(SocialContactsDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { socialContactsService: () => socialContactsServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSocialContacts = { id: 123 };
        socialContactsServiceStub.find.resolves(foundSocialContacts);

        // WHEN
        comp.retrieveSocialContacts(123);
        await comp.$nextTick();

        // THEN
        expect(comp.socialContacts).toBe(foundSocialContacts);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSocialContacts = { id: 123 };
        socialContactsServiceStub.find.resolves(foundSocialContacts);

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

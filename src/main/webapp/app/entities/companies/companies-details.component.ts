import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompanies } from '@/shared/model/companies.model';
import CompaniesService from './companies.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CompaniesDetails extends Vue {
  @Inject('companiesService') private companiesService: () => CompaniesService;
  @Inject('alertService') private alertService: () => AlertService;

  public companies: ICompanies = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companiesId) {
        vm.retrieveCompanies(to.params.companiesId);
      }
    });
  }

  public retrieveCompanies(companiesId) {
    this.companiesService()
      .find(companiesId)
      .then(res => {
        this.companies = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

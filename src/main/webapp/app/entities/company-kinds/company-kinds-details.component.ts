import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompanyKinds } from '@/shared/model/company-kinds.model';
import CompanyKindsService from './company-kinds.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CompanyKindsDetails extends Vue {
  @Inject('companyKindsService') private companyKindsService: () => CompanyKindsService;
  @Inject('alertService') private alertService: () => AlertService;

  public companyKinds: ICompanyKinds = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companyKindsId) {
        vm.retrieveCompanyKinds(to.params.companyKindsId);
      }
    });
  }

  public retrieveCompanyKinds(companyKindsId) {
    this.companyKindsService()
      .find(companyKindsId)
      .then(res => {
        this.companyKinds = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

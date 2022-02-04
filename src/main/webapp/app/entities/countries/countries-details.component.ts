import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICountries } from '@/shared/model/countries.model';
import CountriesService from './countries.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CountriesDetails extends Vue {
  @Inject('countriesService') private countriesService: () => CountriesService;
  @Inject('alertService') private alertService: () => AlertService;

  public countries: ICountries = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.countriesId) {
        vm.retrieveCountries(to.params.countriesId);
      }
    });
  }

  public retrieveCountries(countriesId) {
    this.countriesService()
      .find(countriesId)
      .then(res => {
        this.countries = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

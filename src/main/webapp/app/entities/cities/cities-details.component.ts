import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICities } from '@/shared/model/cities.model';
import CitiesService from './cities.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CitiesDetails extends Vue {
  @Inject('citiesService') private citiesService: () => CitiesService;
  @Inject('alertService') private alertService: () => AlertService;

  public cities: ICities = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.citiesId) {
        vm.retrieveCities(to.params.citiesId);
      }
    });
  }

  public retrieveCities(citiesId) {
    this.citiesService()
      .find(citiesId)
      .then(res => {
        this.cities = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

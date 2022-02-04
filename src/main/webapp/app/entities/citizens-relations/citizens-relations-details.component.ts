import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICitizensRelations } from '@/shared/model/citizens-relations.model';
import CitizensRelationsService from './citizens-relations.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CitizensRelationsDetails extends Vue {
  @Inject('citizensRelationsService') private citizensRelationsService: () => CitizensRelationsService;
  @Inject('alertService') private alertService: () => AlertService;

  public citizensRelations: ICitizensRelations = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.citizensRelationsId) {
        vm.retrieveCitizensRelations(to.params.citizensRelationsId);
      }
    });
  }

  public retrieveCitizensRelations(citizensRelationsId) {
    this.citizensRelationsService()
      .find(citizensRelationsId)
      .then(res => {
        this.citizensRelations = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITeams } from '@/shared/model/teams.model';
import TeamsService from './teams.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TeamsDetails extends Vue {
  @Inject('teamsService') private teamsService: () => TeamsService;
  @Inject('alertService') private alertService: () => AlertService;

  public teams: ITeams = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamsId) {
        vm.retrieveTeams(to.params.teamsId);
      }
    });
  }

  public retrieveTeams(teamsId) {
    this.teamsService()
      .find(teamsId)
      .then(res => {
        this.teams = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

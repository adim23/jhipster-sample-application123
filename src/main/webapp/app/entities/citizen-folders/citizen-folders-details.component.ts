import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICitizenFolders } from '@/shared/model/citizen-folders.model';
import CitizenFoldersService from './citizen-folders.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CitizenFoldersDetails extends Vue {
  @Inject('citizenFoldersService') private citizenFoldersService: () => CitizenFoldersService;
  @Inject('alertService') private alertService: () => AlertService;

  public citizenFolders: ICitizenFolders = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.citizenFoldersId) {
        vm.retrieveCitizenFolders(to.params.citizenFoldersId);
      }
    });
  }

  public retrieveCitizenFolders(citizenFoldersId) {
    this.citizenFoldersService()
      .find(citizenFoldersId)
      .then(res => {
        this.citizenFolders = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

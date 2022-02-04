import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFiles } from '@/shared/model/files.model';
import FilesService from './files.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class FilesDetails extends Vue {
  @Inject('filesService') private filesService: () => FilesService;
  @Inject('alertService') private alertService: () => AlertService;

  public files: IFiles = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.filesId) {
        vm.retrieveFiles(to.params.filesId);
      }
    });
  }

  public retrieveFiles(filesId) {
    this.filesService()
      .find(filesId)
      .then(res => {
        this.files = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

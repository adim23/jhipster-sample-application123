import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';

import { IFiles, Files } from '@/shared/model/files.model';
import FilesService from './files.service';
import { FileType } from '@/shared/model/enumerations/file-type.model';

const validations: any = {
  files: {
    uuid: {},
    filename: {},
    fileType: {},
    fileSize: {},
    createDate: {},
    filePath: {},
    version: {},
    mime: {},
  },
};

@Component({
  validations,
})
export default class FilesUpdate extends Vue {
  @Inject('filesService') private filesService: () => FilesService;
  @Inject('alertService') private alertService: () => AlertService;

  public files: IFiles = new Files();

  public files: IFiles[] = [];

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public fileTypeValues: string[] = Object.keys(FileType);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.filesId) {
        vm.retrieveFiles(to.params.filesId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.files.id) {
      this.filesService()
        .update(this.files)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.files.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.filesService()
        .create(this.files)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.files.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveFiles(filesId): void {
    this.filesService()
      .find(filesId)
      .then(res => {
        this.files = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.filesService()
      .retrieve()
      .then(res => {
        this.files = res.data;
      });

    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }
}

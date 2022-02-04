import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISocialKinds } from '@/shared/model/social-kinds.model';
import SocialKindsService from './social-kinds.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class SocialKindsDetails extends Vue {
  @Inject('socialKindsService') private socialKindsService: () => SocialKindsService;
  @Inject('alertService') private alertService: () => AlertService;

  public socialKinds: ISocialKinds = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.socialKindsId) {
        vm.retrieveSocialKinds(to.params.socialKindsId);
      }
    });
  }

  public retrieveSocialKinds(socialKindsId) {
    this.socialKindsService()
      .find(socialKindsId)
      .then(res => {
        this.socialKinds = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

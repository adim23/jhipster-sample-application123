export interface ISocialKinds {
  id?: number;
  code?: string;
  name?: string;
  call?: string;
}

export class SocialKinds implements ISocialKinds {
  constructor(public id?: number, public code?: string, public name?: string, public call?: string) {}
}

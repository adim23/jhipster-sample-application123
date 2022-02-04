package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocialKindsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocialKinds.class);
        SocialKinds socialKinds1 = new SocialKinds();
        socialKinds1.setId(1L);
        SocialKinds socialKinds2 = new SocialKinds();
        socialKinds2.setId(socialKinds1.getId());
        assertThat(socialKinds1).isEqualTo(socialKinds2);
        socialKinds2.setId(2L);
        assertThat(socialKinds1).isNotEqualTo(socialKinds2);
        socialKinds1.setId(null);
        assertThat(socialKinds1).isNotEqualTo(socialKinds2);
    }
}

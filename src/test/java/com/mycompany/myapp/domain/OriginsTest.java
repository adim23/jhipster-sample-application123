package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OriginsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Origins.class);
        Origins origins1 = new Origins();
        origins1.setId(1L);
        Origins origins2 = new Origins();
        origins2.setId(origins1.getId());
        assertThat(origins1).isEqualTo(origins2);
        origins2.setId(2L);
        assertThat(origins1).isNotEqualTo(origins2);
        origins1.setId(null);
        assertThat(origins1).isNotEqualTo(origins2);
    }
}

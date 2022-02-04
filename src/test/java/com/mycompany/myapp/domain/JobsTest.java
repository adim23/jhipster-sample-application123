package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jobs.class);
        Jobs jobs1 = new Jobs();
        jobs1.setId(1L);
        Jobs jobs2 = new Jobs();
        jobs2.setId(jobs1.getId());
        assertThat(jobs1).isEqualTo(jobs2);
        jobs2.setId(2L);
        assertThat(jobs1).isNotEqualTo(jobs2);
        jobs1.setId(null);
        assertThat(jobs1).isNotEqualTo(jobs2);
    }
}

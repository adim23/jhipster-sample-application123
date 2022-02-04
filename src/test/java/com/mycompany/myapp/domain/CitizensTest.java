package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CitizensTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Citizens.class);
        Citizens citizens1 = new Citizens();
        citizens1.setId(1L);
        Citizens citizens2 = new Citizens();
        citizens2.setId(citizens1.getId());
        assertThat(citizens1).isEqualTo(citizens2);
        citizens2.setId(2L);
        assertThat(citizens1).isNotEqualTo(citizens2);
        citizens1.setId(null);
        assertThat(citizens1).isNotEqualTo(citizens2);
    }
}

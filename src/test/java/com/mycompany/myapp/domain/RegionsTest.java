package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Regions.class);
        Regions regions1 = new Regions();
        regions1.setId(1L);
        Regions regions2 = new Regions();
        regions2.setId(regions1.getId());
        assertThat(regions1).isEqualTo(regions2);
        regions2.setId(2L);
        assertThat(regions1).isNotEqualTo(regions2);
        regions1.setId(null);
        assertThat(regions1).isNotEqualTo(regions2);
    }
}

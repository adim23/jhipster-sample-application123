package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CitiesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cities.class);
        Cities cities1 = new Cities();
        cities1.setId(1L);
        Cities cities2 = new Cities();
        cities2.setId(cities1.getId());
        assertThat(cities1).isEqualTo(cities2);
        cities2.setId(2L);
        assertThat(cities1).isNotEqualTo(cities2);
        cities1.setId(null);
        assertThat(cities1).isNotEqualTo(cities2);
    }
}

package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CountriesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Countries.class);
        Countries countries1 = new Countries();
        countries1.setId(1L);
        Countries countries2 = new Countries();
        countries2.setId(countries1.getId());
        assertThat(countries1).isEqualTo(countries2);
        countries2.setId(2L);
        assertThat(countries1).isNotEqualTo(countries2);
        countries1.setId(null);
        assertThat(countries1).isNotEqualTo(countries2);
    }
}

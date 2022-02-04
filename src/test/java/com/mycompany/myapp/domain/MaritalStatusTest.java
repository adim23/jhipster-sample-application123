package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MaritalStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaritalStatus.class);
        MaritalStatus maritalStatus1 = new MaritalStatus();
        maritalStatus1.setId(1L);
        MaritalStatus maritalStatus2 = new MaritalStatus();
        maritalStatus2.setId(maritalStatus1.getId());
        assertThat(maritalStatus1).isEqualTo(maritalStatus2);
        maritalStatus2.setId(2L);
        assertThat(maritalStatus1).isNotEqualTo(maritalStatus2);
        maritalStatus1.setId(null);
        assertThat(maritalStatus1).isNotEqualTo(maritalStatus2);
    }
}

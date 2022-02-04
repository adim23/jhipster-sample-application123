package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CitizensMeetingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CitizensMeetings.class);
        CitizensMeetings citizensMeetings1 = new CitizensMeetings();
        citizensMeetings1.setId(1L);
        CitizensMeetings citizensMeetings2 = new CitizensMeetings();
        citizensMeetings2.setId(citizensMeetings1.getId());
        assertThat(citizensMeetings1).isEqualTo(citizensMeetings2);
        citizensMeetings2.setId(2L);
        assertThat(citizensMeetings1).isNotEqualTo(citizensMeetings2);
        citizensMeetings1.setId(null);
        assertThat(citizensMeetings1).isNotEqualTo(citizensMeetings2);
    }
}

package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhoneTypesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhoneTypes.class);
        PhoneTypes phoneTypes1 = new PhoneTypes();
        phoneTypes1.setId(1L);
        PhoneTypes phoneTypes2 = new PhoneTypes();
        phoneTypes2.setId(phoneTypes1.getId());
        assertThat(phoneTypes1).isEqualTo(phoneTypes2);
        phoneTypes2.setId(2L);
        assertThat(phoneTypes1).isNotEqualTo(phoneTypes2);
        phoneTypes1.setId(null);
        assertThat(phoneTypes1).isNotEqualTo(phoneTypes2);
    }
}

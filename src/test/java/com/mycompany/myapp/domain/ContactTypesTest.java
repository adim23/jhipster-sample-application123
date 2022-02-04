package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactTypesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactTypes.class);
        ContactTypes contactTypes1 = new ContactTypes();
        contactTypes1.setId(1L);
        ContactTypes contactTypes2 = new ContactTypes();
        contactTypes2.setId(contactTypes1.getId());
        assertThat(contactTypes1).isEqualTo(contactTypes2);
        contactTypes2.setId(2L);
        assertThat(contactTypes1).isNotEqualTo(contactTypes2);
        contactTypes1.setId(null);
        assertThat(contactTypes1).isNotEqualTo(contactTypes2);
    }
}

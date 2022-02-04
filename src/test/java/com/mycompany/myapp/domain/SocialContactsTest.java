package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocialContactsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocialContacts.class);
        SocialContacts socialContacts1 = new SocialContacts();
        socialContacts1.setId(1L);
        SocialContacts socialContacts2 = new SocialContacts();
        socialContacts2.setId(socialContacts1.getId());
        assertThat(socialContacts1).isEqualTo(socialContacts2);
        socialContacts2.setId(2L);
        assertThat(socialContacts1).isNotEqualTo(socialContacts2);
        socialContacts1.setId(null);
        assertThat(socialContacts1).isNotEqualTo(socialContacts2);
    }
}

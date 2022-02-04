package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CodesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Codes.class);
        Codes codes1 = new Codes();
        codes1.setId(1L);
        Codes codes2 = new Codes();
        codes2.setId(codes1.getId());
        assertThat(codes1).isEqualTo(codes2);
        codes2.setId(2L);
        assertThat(codes1).isNotEqualTo(codes2);
        codes1.setId(null);
        assertThat(codes1).isNotEqualTo(codes2);
    }
}

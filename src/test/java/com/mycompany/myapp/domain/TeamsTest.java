package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeamsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Teams.class);
        Teams teams1 = new Teams();
        teams1.setId(1L);
        Teams teams2 = new Teams();
        teams2.setId(teams1.getId());
        assertThat(teams1).isEqualTo(teams2);
        teams2.setId(2L);
        assertThat(teams1).isNotEqualTo(teams2);
        teams1.setId(null);
        assertThat(teams1).isNotEqualTo(teams2);
    }
}

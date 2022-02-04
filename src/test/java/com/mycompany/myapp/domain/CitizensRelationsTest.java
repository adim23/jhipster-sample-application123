package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CitizensRelationsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CitizensRelations.class);
        CitizensRelations citizensRelations1 = new CitizensRelations();
        citizensRelations1.setId(1L);
        CitizensRelations citizensRelations2 = new CitizensRelations();
        citizensRelations2.setId(citizensRelations1.getId());
        assertThat(citizensRelations1).isEqualTo(citizensRelations2);
        citizensRelations2.setId(2L);
        assertThat(citizensRelations1).isNotEqualTo(citizensRelations2);
        citizensRelations1.setId(null);
        assertThat(citizensRelations1).isNotEqualTo(citizensRelations2);
    }
}

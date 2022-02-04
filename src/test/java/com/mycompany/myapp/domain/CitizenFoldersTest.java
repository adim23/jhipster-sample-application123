package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CitizenFoldersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CitizenFolders.class);
        CitizenFolders citizenFolders1 = new CitizenFolders();
        citizenFolders1.setId(1L);
        CitizenFolders citizenFolders2 = new CitizenFolders();
        citizenFolders2.setId(citizenFolders1.getId());
        assertThat(citizenFolders1).isEqualTo(citizenFolders2);
        citizenFolders2.setId(2L);
        assertThat(citizenFolders1).isNotEqualTo(citizenFolders2);
        citizenFolders1.setId(null);
        assertThat(citizenFolders1).isNotEqualTo(citizenFolders2);
    }
}

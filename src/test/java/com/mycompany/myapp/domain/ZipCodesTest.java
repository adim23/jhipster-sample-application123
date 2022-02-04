package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ZipCodesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZipCodes.class);
        ZipCodes zipCodes1 = new ZipCodes();
        zipCodes1.setId(1L);
        ZipCodes zipCodes2 = new ZipCodes();
        zipCodes2.setId(zipCodes1.getId());
        assertThat(zipCodes1).isEqualTo(zipCodes2);
        zipCodes2.setId(2L);
        assertThat(zipCodes1).isNotEqualTo(zipCodes2);
        zipCodes1.setId(null);
        assertThat(zipCodes1).isNotEqualTo(zipCodes2);
    }
}

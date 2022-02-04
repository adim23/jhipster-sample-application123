package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompanyKindsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyKinds.class);
        CompanyKinds companyKinds1 = new CompanyKinds();
        companyKinds1.setId(1L);
        CompanyKinds companyKinds2 = new CompanyKinds();
        companyKinds2.setId(companyKinds1.getId());
        assertThat(companyKinds1).isEqualTo(companyKinds2);
        companyKinds2.setId(2L);
        assertThat(companyKinds1).isNotEqualTo(companyKinds2);
        companyKinds1.setId(null);
        assertThat(companyKinds1).isNotEqualTo(companyKinds2);
    }
}

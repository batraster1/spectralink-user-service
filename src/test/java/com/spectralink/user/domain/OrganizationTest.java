package com.spectralink.user.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.spectralink.user.web.rest.TestUtil;

public class OrganizationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organization.class);
        Organization organization1 = new Organization();
        organization1.setId("id1");
        Organization organization2 = new Organization();
        organization2.setId(organization1.getId());
        assertThat(organization1).isEqualTo(organization2);
        organization2.setId("id2");
        assertThat(organization1).isNotEqualTo(organization2);
        organization1.setId(null);
        assertThat(organization1).isNotEqualTo(organization2);
    }
}

package com.spectralink.user.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.spectralink.user.web.rest.TestUtil;

public class UserSettingsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSettings.class);
        UserSettings userSettings1 = new UserSettings();
        userSettings1.setId("id1");
        UserSettings userSettings2 = new UserSettings();
        userSettings2.setId(userSettings1.getId());
        assertThat(userSettings1).isEqualTo(userSettings2);
        userSettings2.setId("id2");
        assertThat(userSettings1).isNotEqualTo(userSettings2);
        userSettings1.setId(null);
        assertThat(userSettings1).isNotEqualTo(userSettings2);
    }
}

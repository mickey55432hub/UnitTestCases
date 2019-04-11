package com.myproject.se28keymatchbanner;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.WCMMode;
import com.google.gson.Gson;
import io.wcm.testing.mock.aem.junit.AemContext;
import org.apache.sling.api.SlingHttpServletRequest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KeyMatchBannerTest {

    private static final String EXTERNALIZED_PATH_TO_BANNER = "/externalized/path/to/banner";

    @Rule
    public final AemContext context = new AemContext();

    @Mock
    private Externalizer externalizer;

    @Before
    public void setUp() {
        context.addModelsForPackage("com.philips.webcms.foundation.general.se28keymatchbanner");
        context.registerService(Externalizer.class, externalizer);
        when(externalizer.relativeLink(any(SlingHttpServletRequest.class), any(String.class))).thenReturn(EXTERNALIZED_PATH_TO_BANNER);
    }

    @Test
    public void testKeymatchJsonObject() {
        //given
        KeyMatchBanner keyMatchBanner = setResourceAndAdaptToKeyMatchBanner("/mocks/se28_keymatch/se28_keymatch_resource_pathfields.json");
        //when
        String keyMatchJsonObject = keyMatchBanner.getKeyMatchJsonObject();
        Map<String, String> keymatchMap = new Gson().fromJson(keyMatchJsonObject, HashMap.class);
        //then
        Map<String, String> expectedKeymatchMap = new HashMap<>();
        expectedKeymatchMap.put("ledalite", EXTERNALIZED_PATH_TO_BANNER);
        expectedKeymatchMap.put("philips blublu", EXTERNALIZED_PATH_TO_BANNER);
        expectedKeymatchMap.put("blublu", EXTERNALIZED_PATH_TO_BANNER);

        assertThat(keymatchMap)
                .hasSize(expectedKeymatchMap.size())
                .containsAllEntriesOf(expectedKeymatchMap);
    }

    @Test
    public void testIsUnconfigured_wcmModeEdit() {
        WCMMode.EDIT.toRequest(context.request());
        KeyMatchBanner keyMatchBanner = setResourceAndAdaptToKeyMatchBanner("/mocks/se28_keymatch/se28_keymatch_resource_nokeymatchconfig.json");
        assertThat(keyMatchBanner.isUnconfigured()).isTrue();
    }

    @Test
    public void testIsUnconfigured_wcmModeDisabled() {
        WCMMode.DISABLED.toRequest(context.request());
        KeyMatchBanner keyMatchBanner = setResourceAndAdaptToKeyMatchBanner("/mocks/se28_keymatch/se28_keymatch_resource_nokeymatchconfig.json");
        assertThat(keyMatchBanner.isUnconfigured()).isFalse();
    }

    private KeyMatchBanner setResourceAndAdaptToKeyMatchBanner(String resourcePath) {
        context.load().json(resourcePath, "/content");
        context.currentResource("/content");
        return context.request().adaptTo(KeyMatchBanner.class);
    }

}

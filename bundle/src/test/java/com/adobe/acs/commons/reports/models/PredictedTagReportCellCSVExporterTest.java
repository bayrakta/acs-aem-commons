package com.adobe.acs.commons.reports.models;

import com.adobe.acs.commons.reports.internal.PredictedTagsUtil;
import com.day.cq.dam.api.Asset;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class PredictedTagReportCellCSVExporterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PredictedTagReportCellCSVExporterTest.class);

    private static final String ASSET_PATH = "/content/dam/sample.jpg";

    @Mock
    private Resource mockResource;

    @Mock
    private ResourceResolver mockResolver;

    @Mock
    private Asset mockAsset;

    @Spy
    PredictedTagsUtil predictedTagsUtil;

    @InjectMocks
    PredictedTagReportCellCSVExporter systemUnderTest;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        assertNotNull(systemUnderTest);

        doReturn(mockResolver).when(mockResource).getResourceResolver();
        doReturn(mockAsset).when(predictedTagsUtil).resolveToAsset(Mockito.any(Resource.class));

        doReturn(ASSET_PATH).when(mockResource).getPath();
        doReturn(ASSET_PATH).when(mockAsset).getPath();
    }

    @Test
    public void testPropertyDoesNotExist() throws IllegalAccessException {
        LOGGER.info("testEmpty");
        FieldUtils.writeField(systemUnderTest, "property", "nonExistingProperty", true);
        assertEquals("", systemUnderTest.getValue(mockResource));
        LOGGER.info("Test successful!");
    }

}

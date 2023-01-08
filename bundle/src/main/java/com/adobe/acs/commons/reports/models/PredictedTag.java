package com.adobe.acs.commons.reports.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

/**
 * Model for rendering PredictedTag properties to CSV cells.
 */
@Model(adaptables = Resource.class)
public class PredictedTag {

    private static final String CONFIDENCE_FORMAT_STRING = "%.4f";

    @Inject
    private String name;
    @Inject
    private Double confidence;
    @Inject
    private Boolean isCustom;

    public String getName() {
        return name;
    }

    public Double getConfidence() {
        return confidence;
    }

    public String getConfidenceAsFormattedString() {
        return String.format(CONFIDENCE_FORMAT_STRING, confidence);
    }

    public Boolean getCustom() {
        return isCustom;
    }

    @Override
    public String toString() {
        return "PredictedTag{" +
                "name='" + name + "'," +
                "confidence='" + confidence + "'," +
                "custom=" + isCustom +
                '}';
    }

}

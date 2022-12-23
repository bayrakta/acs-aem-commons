package com.adobe.acs.commons.reports.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

/**
 * Model for rendering PredictedTag properties to CSV cells.
 */
@Model(adaptables = Resource.class)
public class PredictedTag {

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

    public Boolean getCustom() {
        return isCustom;
    }

    @Override
    public String toString() {
        return "PredictedTag{" +
                "name='" + name + '\'' +
                '}';
    }

}

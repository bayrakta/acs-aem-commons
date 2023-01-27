package com.adobe.acs.commons.reports.models;

import com.adobe.acs.commons.reports.api.ReportCellCSVExporter;
import com.adobe.acs.commons.reports.internal.ExporterUtil;
import com.adobe.acs.commons.reports.internal.PredictedTagsUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for rendering PredictedTag properties to CSV cells.
 */
@Model(adaptables = Resource.class)
public class PredictedTagReportCellCSVExporter implements ReportCellCSVExporter {

    public static final String EMPTY_STRING = "";
    public static final String CONFIDENCE_BRACKET_OPEN = "[";
    public static final String CONFIDENCE_BRACKET_CLOSE = "]";
    public static final String VALUE_SEPARATOR = ";";

    @Inject
    private String property;
    @Inject @Optional
    private Double lowerConfidenceThreshold;

    private PredictedTagsUtil predictedTagsUtil = new PredictedTagsUtil();

    @Override
    public String getValue(final Object result) {
        final Resource resource = (Resource) result;
        final String relativePropertyPath = ExporterUtil.relativizePath(property);

        final List<PredictedTag> predictedTags = predictedTagsUtil.getPredictedTags(resource, relativePropertyPath, lowerConfidenceThreshold);
        if (CollectionUtils.isEmpty(predictedTags)) {
            return EMPTY_STRING;
        }

        final List<String> predictedTagRenderedValue = new ArrayList<>();
        for (final PredictedTag predictedTag : predictedTags) {
            predictedTagRenderedValue.add(asCellCSVValue(predictedTag));
        }

        return StringUtils.join(predictedTagRenderedValue, VALUE_SEPARATOR);
    }


    public String asCellCSVValue(PredictedTag predictedTag) {
        if (predictedTag == null) {
            return EMPTY_STRING;
        }

        return predictedTag.getName() +
                CONFIDENCE_BRACKET_OPEN +
                predictedTag.getConfidenceAsFormattedString() +
                CONFIDENCE_BRACKET_CLOSE;
    }
}

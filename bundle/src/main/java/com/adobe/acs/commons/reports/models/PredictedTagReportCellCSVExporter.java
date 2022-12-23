package com.adobe.acs.commons.reports.models;

import com.adobe.acs.commons.reports.api.ReportCellCSVExporter;
import com.adobe.acs.commons.reports.internal.ExporterUtil;
import com.adobe.acs.commons.reports.internal.PredictedTagsUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for rendering PredictedTag properties to CSV cells.
 */
@Model(adaptables = Resource.class)
public class PredictedTagReportCellCSVExporter implements ReportCellCSVExporter {

    @Inject
    private String property;

    @Override
    public String getValue(final Object result) {
        final Resource resource = (Resource) result;
        final String relativePropertyPath = ExporterUtil.relativizePath(property);

        final List<PredictedTag> predictedTags = PredictedTagsUtil.getPredictedTags(resource, relativePropertyPath);
        if (CollectionUtils.isEmpty(predictedTags)) {
            return "";
        }

        final List<String> predictedTagNames = new ArrayList<>();
        for (final PredictedTag predictedTagModel : predictedTags) {
            predictedTagNames.add(predictedTagModel.getName());
        }

        return StringUtils.join(predictedTagNames, ";");
    }

}

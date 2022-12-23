package com.adobe.acs.commons.reports.internal;

import com.adobe.acs.commons.reports.models.PredictedTag;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.dam.commons.util.DamUtil;
import org.apache.jackrabbit.vault.util.PathUtil;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PredictedTagsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PredictedTagsUtil.class);

    private PredictedTagsUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static List<PredictedTag> getPredictedTags(final Resource resource,
                                                      final String relativePropertyPath) {
        if (resource == null) {
            LOGGER.error("getPredictedTags : The given resource is null, hence returning empty list.");
            return Collections.emptyList();
        }
        final Asset asset = DamUtil.resolveToAsset(resource);
        if (asset == null) {
            LOGGER.error("getPredictedTags : The given resource could not be resolved to an asset, hence returning empty list.");
            return Collections.emptyList();
        }
        final Resource predictedTagsResource = getPredictedTagsResource(resource, relativePropertyPath);
        if (predictedTagsResource == null) {
            LOGGER.error("getPredictedTags : Asset contains no predictedTags, hence returning empty list.");
            return Collections.emptyList();
        }

        final Iterable<Resource> predicateTagResourcesIterable = predictedTagsResource.getChildren();
        final Iterator<Resource> predicateTagResourcesIterator = predicateTagResourcesIterable.iterator();
        final List<PredictedTag> predictedTags = new ArrayList<>();
        while (predicateTagResourcesIterator.hasNext()) {
            final Resource predicateTagResource = predicateTagResourcesIterator.next();
            final PredictedTag predictedTag = predicateTagResource.adaptTo(PredictedTag.class);
            predictedTags.add(predictedTag);
        }

        LOGGER.debug("getPredictedTags : Loaded predictedTags {}.", predictedTags);
        return predictedTags;
    }

    private static Resource getPredictedTagsResource(final Resource resource,
                                                     final String relativePropertyPath) {
        String predictedTagsPath = PathUtil.append(resource.getPath(), relativePropertyPath);
        Resource predictedTagsResource = resource.getResourceResolver().getResource(predictedTagsPath);
        if (predictedTagsResource == null) {
            // fallback
            predictedTagsPath = PathUtil.append(resource.getPath(), DamConstants.PREDICTED_TAGS);
            return resource.getResourceResolver().getResource(predictedTagsPath);
        }
        return predictedTagsResource;
    }

}

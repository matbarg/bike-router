package at.ac.hcw.bikerouter.util;

import com.graphhopper.ResponsePath;
import com.graphhopper.util.details.PathDetail;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RouteFeaturesCalculator {
    public Map<String, Map<String, Double>> calculateRouteFeaturesRatios(ResponsePath path) {
        Map<String, Map<String, Double>> routeFeatures = new HashMap<>();
        Map<String, List<PathDetail>> pathDetails = path.getPathDetails();

        if (pathDetails == null || pathDetails.isEmpty()) {
            return routeFeatures;
        }

        for (Map.Entry<String, List<PathDetail>> entry : pathDetails.entrySet()) {
            routeFeatures.put(entry.getKey(), calculateSingleRouteFeatureRatio(entry.getValue(), entry.getKey()));
        }

        return routeFeatures;
    }

    public Map<String, Double> calculateSingleRouteFeatureRatio(List<PathDetail> pathDetails, String featureName) {
        Map<String, Integer> lengthPerValue = new HashMap<>();
        int totalLength = 0;

        String key;

        for (PathDetail pathDetail : pathDetails) {

            if ("average_slope".equals(featureName)) {
                double slope = ((Number) pathDetail.getValue()).doubleValue();
                key = slopeCategory(slope);
            } else {
                Object featureType = pathDetail.getValue();
                key = featureType != null ? featureType.toString() : "missing";

            }

            int length = pathDetail.getLength();
            totalLength += length;
            lengthPerValue.merge(key, length, Integer::sum);
        }

        Map<String, Double> ratios = new HashMap<>();

        if (totalLength == 0) {
            return ratios;
        }

        for (Map.Entry<String, Integer> entry : lengthPerValue.entrySet()) {
            ratios.put(
                    entry.getKey(),
                    (double) entry.getValue() / totalLength
            );
        }

        ratios.remove("missing");

        return ratios;
    }

    private String slopeCategory(double slope) {
        if (slope < -8) {
            return "steep_down";
        } else if (slope < -2) {
            return "moderate_down";
        } else if (slope <= 2) {
            return "flat";
        } else if (slope <= 8) {
            return "moderate_up";
        } else {
            return "steep_up";
        }
    }
}
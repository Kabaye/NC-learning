package edu.netcracker.customer.app.metrics.collector;

import edu.netcracker.metrics.models.MetricData;
import edu.netcracker.metrics.models.MetricType;


public interface MetricsCollector {
    void saveSuccessfulMetrics(MetricType metricType, MetricData metricData);

    void saveErrorMetrics(MetricType metricType, MetricData metricData);
}

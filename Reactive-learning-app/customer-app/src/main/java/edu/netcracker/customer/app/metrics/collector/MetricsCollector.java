package edu.netcracker.customer.app.metrics.collector;

import edu.netcracker.customer.app.metrics.model.MetricsData;
import edu.netcracker.customer.app.metrics.model.MetricsType;


public interface MetricsCollector {
    void saveSuccessfulMetrics(MetricsType metricsType, MetricsData metricsData);

    void saveErrorMetrics(MetricsType metricsType, MetricsData metricsData);
}

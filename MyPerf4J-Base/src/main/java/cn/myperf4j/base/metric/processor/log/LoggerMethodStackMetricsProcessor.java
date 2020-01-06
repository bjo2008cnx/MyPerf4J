package cn.myperf4j.base.metric.processor.log;

import cn.myperf4j.base.MethodTag;
import cn.myperf4j.base.metric.MethodMetrics;
import cn.myperf4j.base.metric.formatter.MethodMetricsFormatter;
import cn.myperf4j.base.metric.formatter.impl.DefaultMethodMetricsFormatter;
import cn.myperf4j.base.metric.processor.AbstractMethodMetricsProcessor;
import cn.myperf4j.base.util.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by LinShunkang on 2018/7/11
 */
public class LoggerMethodStackMetricsProcessor extends AbstractMethodMetricsProcessor {

  private ConcurrentHashMap<Long, List<MethodMetrics>> metricsMap = new ConcurrentHashMap<>(8);

  private MethodMetricsFormatter formatter = new DefaultMethodMetricsFormatter();

  @Override
  public void beforeProcess(long processId, long startMillis, long stopMillis) {
    metricsMap.put(processId, new ArrayList<MethodMetrics>(64));
  }

  @Override
  public void process(MethodMetrics metrics, long processId, long startMillis, long stopMillis) {
    List<MethodMetrics> metricsList = metricsMap.get(processId);
    if (metricsList != null) {
      metricsList.add(metrics);
      MethodTag methodTag = metrics.getMethodTag();
      String methodName = methodTag.getMethodName();

      //忽略is&get方法
      if (methodName.startsWith("get") || methodName.startsWith("is")) {
        return;
      }
      logger.logAndFlush(String.format("%s:%s,%s", methodTag.getFullDesc(), methodName, methodTag.getMethodParamDesc()));
    } else {
      String metricValue = processId + ", " + startMillis + ", " + stopMillis;
      Logger.error("LoggerMethodMetricsProcessor.process(" + metricValue + "): metricsList is null!!!");
    }
  }

  @Override
  public void afterProcess(long processId, long startMillis, long stopMillis) {
    List<MethodMetrics> metricsList = metricsMap.remove(processId);
    if (metricsList != null) {
      //logger.logAndFlush(formatter.format(metricsList, startMillis, stopMillis));
    } else {
      String metricValue = processId + ", " + startMillis + ", " + stopMillis;
      Logger.error("LoggerMethodMetricsProcessor.afterProcess(" + metricValue + "): metricsList is null!!!");
    }
  }
}

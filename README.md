简体中文 | [English](./README.EN.md)

<h1 align="center">MyPerf4J</h1>

<div align="center">

一个针对高并发、低延迟应用设计的高性能 Java 性能监控和统计工具。

[![GitHub (pre-)release](https://img.shields.io/github/release/LinShunKang/MyPerf4J/all.svg)](https://github.com/LinShunKang/MyPerf4J) [![Build Status](https://travis-ci.com/LinShunKang/MyPerf4J.svg?branch=develop)](https://travis-ci.com/LinShunKang/MyPerf4J) [![Coverage Status](https://coveralls.io/repos/github/LinShunKang/MyPerf4J/badge.svg?branch=develop)](https://coveralls.io/github/LinShunKang/MyPerf4J?branch=develop) [![GitHub issues](https://img.shields.io/github/issues/LinShunKang/MyPerf4J.svg)](https://github.com/LinShunKang/MyPerf4J/issues) [![GitHub closed issues](https://img.shields.io/github/issues-closed/LinShunKang/MyPerf4J.svg)](https://github.com/LinShunKang/MyPerf4J/issues?q=is%3Aissue+is%3Aclosed) [![GitHub](https://img.shields.io/github/license/LinShunKang/MyPerf4J.svg)](./LICENSE)

</div>

## 价值
* 快速定位性能瓶颈
* 快速定位故障原因

## 优势
* [高性能](https://github.com/LinShunKang/MyPerf4J/wiki/%E6%80%A7%E8%83%BD%E5%BC%80%E9%94%80): 单线程支持每秒 **1600 万次** 响应时间的记录，每次记录只花费 **63 纳秒**
* [无侵入](https://github.com/LinShunKang/MyPerf4J/wiki/%E5%AE%9E%E7%8E%B0%E5%8E%9F%E7%90%86#%E6%95%B0%E6%8D%AE%E9%87%87%E9%9B%86): 采用 **JavaAgent** 方式，对应用程序完全无侵入，无需修改应用代码
* [低内存](https://github.com/LinShunKang/MyPerf4J/wiki/%E5%AE%9E%E7%8E%B0%E5%8E%9F%E7%90%86#%E6%95%B0%E6%8D%AE%E5%AD%98%E5%82%A8): 采用**内存复用**的方式，整个生命周期只产生极少的临时对象，不影响应用程序的 GC
* 高实时: 支持**秒级统计**，最低统计粒度为 **1 秒**，并且是**全量统计**，不丢失任何一次记录

## 文档
* [English Doc](https://github.com/LinShunKang/MyPerf4J/wiki/English-Doc)
* [中文文档](https://github.com/LinShunKang/MyPerf4J/wiki/Chinese-Doc)    
    
## 监控指标
MyPerf4J 为每个应用收集数十个监控指标，所有的监控指标都是实时采集和展现的。

下面是 MyPerf4J 目前支持的监控指标列表:
- **[Method Metrics](https://grafana.com/dashboards/7766)**<br/>
[RPS，Count，Avg，Min，Max，StdDev，TP50, TP90, TP95, TP99, TP999, TP9999, TP100](https://github.com/LinShunKang/MyPerf4J/wiki/%E6%8C%87%E6%A0%87#method-metrics)
![Markdown](https://raw.githubusercontent.com/LinShunKang/Objects/master/MyPerf4J-InfluxDB-Method_Show_Operation.gif)

- **[JVM Metrics](https://grafana.com/dashboards/8787)**<br/>
[Thread](https://github.com/LinShunKang/MyPerf4J/wiki/%E6%8C%87%E6%A0%87#jvm-thread-metrics)，[Memory](https://github.com/LinShunKang/MyPerf4J/wiki/%E6%8C%87%E6%A0%87#jvm-memory-metrics)，[ByteBuff](https://github.com/LinShunKang/MyPerf4J/wiki/%E6%8C%87%E6%A0%87#jvm-bytebuff-metrics)，[GC](https://github.com/LinShunKang/MyPerf4J/wiki/%E6%8C%87%E6%A0%87#jvm-gc-metrics)，[Class](https://github.com/LinShunKang/MyPerf4J/wiki/%E6%8C%87%E6%A0%87#jvm-class-metrics)，[Compilation](https://github.com/LinShunKang/MyPerf4J/wiki/%E6%8C%87%E6%A0%87#jvm-compilation-metrics)，[FileDescriptor](https://github.com/LinShunKang/MyPerf4J/wiki/%E6%8C%87%E6%A0%87#jvm-filedescriptor-metrics)
![Markdown](https://github.com/LinShunKang/Objects/blob/master/images/JVM_Metrics_Dashboard_V2.png?raw=true)

    > 想知道如何实现上述效果？请先按照[快速启动](https://github.com/LinShunKang/MyPerf4J#%E5%BF%AB%E9%80%9F%E5%90%AF%E5%8A%A8)的描述启动应用，再按照[这里](https://github.com/LinShunKang/MyPerf4J/wiki/InfluxDB_)的描述进行安装配置即可。


=======================================================================================================================================

## 快速启动
MyPerf4J 采用 JavaAgent 配置方式，**透明化**接入应用，对应用代码完全**没有侵入**。

### 下载
* 下载并解压 [MyPerf4J-ASM.zip](https://github.com/LinShunKang/Objects/blob/master/zips/CN/MyPerf4J-ASM-2.9.0.zip?raw=true)
* 阅读解压出的 `README` 文件
* 修改解压出的 `MyPerf4J.properties` 配置文件中 `AppName`、`IncludePackages` 和 `xxxMetricsFile` 的配置值

> 查看[配置文件模板](https://raw.githubusercontent.com/LinShunKang/Objects/master/jars/MyPerf4J.properties)。想了解更多的配置？请看[这里](https://github.com/LinShunKang/MyPerf4J/wiki/%E9%85%8D%E7%BD%AE)

### 配置
在 JVM 启动参数里加上以下两个参数
* -javaagent:/path/to/MyPerf4J-ASM.jar -DMyPerf4JPropFile=/path/to/MyPerf4J.properties

> 形如：java -javaagent:/path/to/MyPerf4J-ASM.jar -DMyPerf4JPropFile=/path/to/MyPerf4J.properties `-jar yourApp.jar`

### 运行
启动应用，监控日志输出到 /path/to/log/method_metrics.log:
```
MyPerf4J Method Metrics [2019-06-02 23:44:30, 2019-06-02 23:44:40]
Method[4]                            Type        Level      RPS  Avg(ms)  Min(ms)  Max(ms)   StdDev     Count     TP50     TP90     TP95     TP99    TP999   TP9999    TP100
DemoServiceImpl.getId1(long)      General      Service  3274139     0.00        0        0     0.00  32741398        0        0        0        0        0        0        0
DemoServiceImpl.getId2(long)      General      Service  3274139     0.00        0        0     0.00  32741398        0        0        0        0        0        0        0
DemoDAO.getId1(long)         DynamicProxy          DAO  3274139     0.00        0        0     0.00  32741398        0        0        0        0        0        0        0
DemoDAO.getId2(long)         DynamicProxy          DAO  3274139     0.00        0        0     0.00  32741398        0        0        0        0        0        0        0
```

### 卸载
在 JVM 启动参数中去掉以下两个参数，重启即可卸载此工具。
* -javaagent:/path/to/MyPerf4J-ASM.jar
* -DMyPerf4JPropFile=/path/to/MyPerf4J.properties

## 构建
您可以自行构建 MyPerf4J-ASM.jar
* git clone git@github.com:LinShunKang/MyPerf4J.git
* mvn clean package

> MyPerf4J-ASM-${MyPerf4J-version}.jar 在 MyPerf4J-ASM/target/ 目录下


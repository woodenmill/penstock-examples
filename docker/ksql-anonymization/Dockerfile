FROM confluentinc/cp-ksql-server:5.0.0
LABEL maintainer="wojda@woodenmill.io"

COPY queries.ksql /opt/queries.ksql
ENV KSQL_KSQL_QUERIES_FILE=/opt/queries.ksql

COPY prom-jmx-agent-config.yml /etc/kafka/prom-jmx-agent-config.yml
ADD http://central.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.10/jmx_prometheus_javaagent-0.10.jar /etc/kafka/jmx_prometheus_javaagent.jar
ENV KSQL_OPTS=-javaagent:/etc/kafka/jmx_prometheus_javaagent.jar=9000:/etc/kafka/prom-jmx-agent-config.yml

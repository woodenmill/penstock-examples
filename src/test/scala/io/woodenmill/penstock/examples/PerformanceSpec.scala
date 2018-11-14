package io.woodenmill.penstock.examples

import java.net.URI

import akka.Done
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import cats.effect.IO
import io.woodenmill.penstock.LoadRunner
import io.woodenmill.penstock.Metrics.Gauge
import io.woodenmill.penstock.backends.kafka.{KafkaBackend, createProducerRecord}
import io.woodenmill.penstock.metrics.prometheus.{PromQl, PrometheusConfig, PrometheusMetric}
import io.woodenmill.penstock.report.ConsoleReport
import org.apache.kafka.common.serialization.{Serializer, StringSerializer}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Future
import scala.concurrent.duration._

class PerformanceSpec extends FlatSpec with ScalaFutures with Matchers {
  implicit val system: ActorSystem = ActorSystem("PerformanceSpec")
  implicit val mat: ActorMaterializer = ActorMaterializer()
  implicit val kafkaBackend: KafkaBackend = KafkaBackend("kafka:9093")
  implicit val stringSerializer: Serializer[String] = new StringSerializer()
  implicit val contentWatchedSerializer: Serializer[ContentWatched] = new ContentWatchedSerializer()
  implicit val promConfig: PrometheusConfig = PrometheusConfig(new URI("localhost:9090"))
  override implicit val patienceConfig: PatienceConfig = PatienceConfig(timeout = 5.minutes, interval = 10.seconds)


  "KSQL-Anonymization" should "process at least 200 messages per second" in {
    //given
    val event = ContentWatched("123", User("Paul", "Smith", "W1T 6BW"), System.currentTimeMillis())
    val kafkaMessage = createProducerRecord("content-watched", event)

    //and
    val recordSendRateQuery = PromQl("""sum(kafka_producer_producer_topic_metrics_record_send_rate{topic="ANONYMIZED_WATCHED"})""")
    val recordSendRate: IO[Gauge] = PrometheusMetric[Gauge](metricName ="record send rate", query = recordSendRateQuery)
    val report = ConsoleReport(recordSendRate)

    //when
    val load: Future[Done] = LoadRunner(kafkaBackend).start(() => kafkaMessage, duration = 2.minutes, throughput = 200)
    report.runEvery(5.seconds)

    //then
    whenReady(load) { _ =>
      recordSendRate.unsafeRunSync().value shouldBe 200.0 +- 20.0
    }
  }

}

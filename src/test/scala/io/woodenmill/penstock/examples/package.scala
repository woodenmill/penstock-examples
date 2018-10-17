package io.woodenmill.penstock

import io.circe.generic.auto._
import io.circe.syntax._
import org.apache.kafka.common.serialization.Serializer

package object examples {

  case class ContentWatched(contentId: String, user: User, eventTimestamp: Long)
  case class User(firstName: String, lastName: String, postCode: String)

  class ContentWatchedSerializer extends Serializer[ContentWatched] {
    override def configure(configs: java.util.Map[String, _], isKey: Boolean): Unit = ()
    override def close(): Unit = ()
    override def serialize(topic: String, data: ContentWatched): Array[Byte] = {
      if (data == null) null
      else data.asJson.noSpaces.getBytes()
    }
  }

}

package conversions

import model.Book
import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import spray.json._

trait BookJsonSupport extends DefaultJsonProtocol {
  implicit val toJson: RootJsonFormat[Book] = jsonFormat2(Book)
  implicit val fromJson: String => Book = (s: String) => s.parseJson.convertTo[Book]
}

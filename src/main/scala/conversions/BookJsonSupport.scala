package conversions

import model.Book
import spray.json.DefaultJsonProtocol

trait BookJsonSupport extends DefaultJsonProtocol {
  implicit val bookFormat = jsonFormat2(Book)
}

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import conversions.BookJsonSupport
import db.{Database, MongoConnection}
import model.Book

object BooksApp extends App with SprayJsonSupport with BookJsonSupport {

  implicit val system = ActorSystem(Behaviors.empty, "books-app-system")
  implicit val executionContext = system.executionContext

  import akka.http.scaladsl.server.Directives._

  val collection = MongoConnection.init
  val db = Database(collection)

  val route = {
    path("api" / "all") {
      parameters("page".as[Int], "limit".as[Int]) { (page, limit) =>
        complete(db.findAll(page, limit))
      }
    } ~
      path("api" / "book") {
        post {
          entity(as[Book]) { book =>
            validate(book.title.nonEmpty && book.author.nonEmpty, "Title or author must not be empty.") {
              db.insert(book)
              complete(s"Book was added.")
            }
          }
        }
      } ~
      path("api" / "book" / Segment) { (id: String) =>
        get {
          complete(db.findOne(id))
        } ~
          put {
            entity(as[Book]) { book =>
              complete(db.update(id, book))
            }
          } ~
          delete(complete(db.delete(id)))
      }
  }

  Http().newServerAt("localhost", 8081).bind(route)
}
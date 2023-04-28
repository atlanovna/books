package http

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.actor.typed.scaladsl.Behaviors
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
        complete(
          HttpEntity(
            ContentTypes.`application/json`,
            s"books page: $page, limit: $limit"
          )
        )
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
          complete(
            HttpEntity(
              ContentTypes.`application/json`,
              s"book with id: $id"
            )
          )
        } ~
          put {
            entity(as[Book]) { book =>
              complete(s"Get old book with id: $id and update it to new $book")
            }
          } ~
          delete(complete(s"Book with id: $id was deleted"))
      }
  }

  Http().newServerAt("localhost", 8081).bind(route)
}
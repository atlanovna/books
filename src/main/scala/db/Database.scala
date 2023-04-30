package db

import conversions.BookJsonSupport
import model.Book
import org.mongodb.scala.bson.BsonObjectId
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates
import org.mongodb.scala.model.Updates.combine
import org.mongodb.scala.{Document, MongoCollection}

import scala.concurrent.Future

case class Database(collection: MongoCollection[Document]) extends BookJsonSupport {
  implicit val ec = scala.concurrent.ExecutionContext.global

  def insert(book: Book) = {
    val doc = Document("title" -> book.title, "author" -> book.author)
    collection.insertOne(doc).toFuture()
  }

  def findOne(id: String) =
    collection.find(equal("_id", BsonObjectId(id))).first().map(_.toJson(): Book).toFuture().map(_.headOption)

  def update(id: String, book: Book) =
    collection.updateOne(equal("_id", BsonObjectId(id)),
      combine(Updates.set("title", book.title), Updates.set("author", book.author)))
      .toFuture().map(res => if (res.wasAcknowledged()) book else throw new Exception("Book wasn't updated."))

  def delete(id: String) =
    collection.deleteOne(equal("_id", BsonObjectId(id)))
      .toFuture().map(res => if (res.wasAcknowledged()) "Book was deleted" else "Book wasn't deleted")

  def findAll(page: Int, limit: Int): Future[Seq[Book]] = {
    val skipCount = if (page == 1) 0 else limit * (page - 1)
    collection.find().skip(skipCount).limit(limit).toFuture().map(_.map(_.toJson(): Book))
  }
}

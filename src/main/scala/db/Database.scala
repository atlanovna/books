package db

import db.Helpers.GenericObservable
import model.Book
import org.mongodb.scala.{Document, MongoCollection}

case class Database(collection: MongoCollection[Document]) {

  def insert(book: Book) = {
    val doc = Document("title" -> book.title, "author" -> book.author)
    collection.insertOne(doc).results()
  }
}

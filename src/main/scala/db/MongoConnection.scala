package db

import db.Helpers.GenericObservable
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase, SingleObservable}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object MongoConnection extends App {

  lazy val init = {
    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase("db")
    database.getCollection("books")
  }

  val doc = Document("title" -> "MongoDB tutorial", "author" -> "Atlanova")




  // val res1: SingleObservable[Document] = collection.find().first()
  //  res1.toFuture().onComplete {
  //    case Success(value) => println(value)
  //    case Failure(exception) => exception.printStackTrace()
  //  }
  //  val insertAndCount = for {
  //    countResult <- collection.countDocuments()
  //  } yield println(countResult)
}

package db

import db.Helpers.GenericObservable
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase, SingleObservable}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object MongoConnection extends App {

    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase("db")
//  database.createCollection("books").toFuture().foreach {
//    _ => println("Database initialized")
//  }
    val collection: MongoCollection[Document] = database.getCollection("books")
    val doc = Document("title" -> "MongoDB tutorial", "author" -> "Atlanova")

  //todo check if we need results()
  def insert(title: String, author: String) = {
    println("I'm here!")
    val doc = Document("title" -> title, "author" -> author)
    println(doc)
    println(collection)
    collection.insertOne(doc).results()
  }


  // val res1: SingleObservable[Document] = collection.find().first()
//  res1.toFuture().onComplete {
//    case Success(value) => println(value)
//    case Failure(exception) => exception.printStackTrace()
//  }
  //  val insertAndCount = for {
  //    countResult <- collection.countDocuments()
  //  } yield println(countResult)
}

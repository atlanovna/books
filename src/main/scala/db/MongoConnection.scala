package db

import org.mongodb.scala.{MongoClient, MongoDatabase}

object MongoConnection extends App {

  lazy val init = {
    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase("db")
    database.getCollection("books")
  }
}

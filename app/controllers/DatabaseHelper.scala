package controllers

import com.mongodb.{BasicDBObject, Mongo}
import scala.collection.JavaConversions._
import java.util.Date

object DatabaseHelper {

  def distinctServers = {
    val mongo = new Mongo()
    val db = mongo.getDB("slogger");
    val coll = db.getCollection("logs")
    val servers = coll.distinct("server")
    servers.map(f => if (f != null) f.toString else "").toList
  }


  /**
   * Get the distict times from a server
   * @param server
   */
  def distinctDates(server: String) = {
    val mongo = new Mongo()
    val db = mongo.getDB("slogger");
    val coll = db.getCollection("logs")

    val start = System.currentTimeMillis()
    val query = new BasicDBObject()
    query.put("server", server)
    val result = coll.distinct("time", query)
    val stop = System.currentTimeMillis()
    println("Querying servers took: "+(stop-start)+" ms.")
    val res =  result.map(f => new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date(f.toString.toLong))).distinct.toList
    println(res)
    res
  }
  def main(args: Array[String]) {
    distinctDates("Srens-MacBook-Pro.local")
  }
}

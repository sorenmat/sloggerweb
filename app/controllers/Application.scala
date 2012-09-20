package controllers

import play.api.mvc._
import com.mongodb.{BasicDBObject, Mongo}
import scala.collection.JavaConversions._
import java.util.Date
import play.api.libs.json.Json._


object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Slogger"))
  }

  def servers = Action {
    val mongo = new Mongo()
    val db = mongo.getDB("slogger");
    val coll = db.getCollection("logs")
    val servers = coll.distinct("server")
    Ok(views.html.servers("hi", servers.map(f => if (f != null) f.toString else "").toList))
  }


  def serverJson(name: String) = Action {

    println("...asdfa.df")
    val mongo = new Mongo()
    val db = mongo.getDB("slogger");
    val coll = db.getCollection("logs")

    val query = new BasicDBObject()
    query.put("server", name)
    val result = coll.find(query)

    println("jaja")
    val data = result.iterator().toList.sortWith((o1, o2) => o1.get("time").toString < o2.get("time").toString).map(f => (f.get("logMessage").toString))
    val res = toJson(data)
    Ok(res).as("application/json")
  }

  def server(name: String) = Action {
    val mongo = new Mongo()
    val db = mongo.getDB("slogger");
    val coll = db.getCollection("logs")

    val start = System.currentTimeMillis()
    val query = new BasicDBObject()
    query.put("server", name)
    val result = coll.find(query)
    val stop = System.currentTimeMillis()
    println("Querying servers took: "+(stop-start)+" ms.")


    // cpu load
    val cpuStart = System.currentTimeMillis()
    val cpucol = db.getCollection("metrics")
    val cpu_result = cpucol.find(query).limit(500)
    val data_values: List[(Date, Double)] = cpu_result.toArray.toList.map(f => (new Date(f.get("time").toString.toLong), f.get("cpuload").toString.toDouble))
    val cpuStop = System.currentTimeMillis()
    println("CPU metrics took: "+(cpuStop-cpuStart)+" ms.")

    val sortStart = System.currentTimeMillis()
    val sortedData = result.iterator().toList.sortWith((o1, o2) => o1.get("time").toString < o2.get("time").toString)
    val sortStop = System.currentTimeMillis()
    println("Sorting servers took: "+(sortStop-sortStart)+" ms.")
    Ok(views.html.server(name, sortedData, data_values))
  }

  def date = Action {
    Ok(views.html.date("", List("test")))
  }

  def time()(f: Unit) {
    val start = System.currentTimeMillis()
    f
    val stop = System.currentTimeMillis()
    println(f + " took " + (stop - start) + "ms.")
  }
}
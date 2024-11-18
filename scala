val x = spark.read.json("C:\\Program Files\\spark-3.5.2-bin-hadoop3-scala2.13\\examples\\src\\main\\resources\\people.json");
x.show()
x.printSchema()

x.select($"name",$"age").show()

x.filter($"age">20).show()

x.select($"age"+1).show()

x.createOrReplaceTempView("people")
val sqlDF = spark.sql("Select * from people")
sqlDF.show()

df.createGlobalTempView("people")

spark.sql("SELECT * FROM global_temp.people").show()

spark.newSession().sql("SELECT * FROM global_temp.people").show()

case class Person(name: String, age: Long)

val caseClassDS = Seq(Person("Andy", 32)).toDS()
caseClassDS.show()

val primitiveDS = Seq(1, 2, 3).toDS()

primitiveDS.map(_ + 1).collect()

val path = "C:\\Program Files\\spark-3.5.2-bin-hadoop3-scala2.13\\examples\\src\\main\\resources\\people.json"

val peopleDS = spark.read.json(path).as[Person]
peopleDS.show()

import spark.implicits._

val peopleDF = spark.sparkContext.textFile("C:\\Program Files\\spark-3.5.2-bin-hadoop3-scala2.13\\examples\\src\\main\\resources\\people.txt").map(_.split(",")).map(attributes => Person(attributes(0), attributes(1).trim.toInt)).toDF()

peopleDF.createOrReplaceTempView("people")
val teenagersDF = spark.sql("SELECT name, age FROM people WHERE age BETWEEN 13 AND 19")

teenagersDF.map(teenager => "Name: " + teenager(0)).show()

teenagersDF.map(teenager => "Name: " + teenager.getAs[String]("name")).show()

implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[Map[String, Any]]

teenagersDF.map(teenager => teenager.getValuesMap[Any](List("name", "age"))).collect()

csv:
val a = spark.read.option("header", "true").csv("C:\\Program Files\\spark-3.5.2-bin-hadoop3-scala2.13\\examples\\src\\main\\resources\\people.csv");
a.show()
a.printSchema()

12. Performing operations on custom data.:
val mydata = spark.read.format("csv").option("inferschema", "true").option("header", "true").load("C:\\Program Files\\spark-3.5.2-bin-hadoop3-scala2.13\\examples\\src\\main\\resources\\banking.csv")
mydata.show()
mydata.show(50)

mydata.select($"age", $"y").show()

mydata.count()

mydata.count.toDouble

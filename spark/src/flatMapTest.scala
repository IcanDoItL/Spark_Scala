import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object flatMapTest {
  def main(args: Array[String]): Unit = {
    val conf=new SparkConf
    conf.setMaster("local[2]").setAppName("WORDCOUNT")
    val sc=new SparkContext(conf)
     val first = sc.parallelize(List("dog,salmon,salmon,rat,elephant","aaa,aaa,bbb,vvv,ddd"), 3)
     val second=lines(first)
     //val second=first.flatMap { line => line.split(",") }
    second.foreach { n => println(n) }
  }
  def lines(rdd:RDD[String]):RDD[String]=
  {
     rdd.flatMap(split)
  }
  def split(line:String):Array[String]=
  {
   val list=line.split(",")
   return list
  }
}
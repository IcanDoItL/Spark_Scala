
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
object combineTest {
  
  def main(args: Array[String]): Unit = {
     val conf=new SparkConf
     conf.setMaster("local[2]").setAppName("WORDCOUNT")
     val sc=new SparkContext(conf)
     val a = sc.parallelize(List("dog","cat","gnu","salmon","rabbit","turkey","wolf","bear","bee"), 3)
     val b = sc.parallelize(List(1,1,2,2,2,1,2,2,2), 3)
     val c = b.zip(a)
     c.collect().foreach(println(_));
     val d = c.combineByKey(List(_), (x:List[String], y:String) => y :: x, (x:List[String], y:List[String]) => x ::: y)
     d.collect().foreach(print(_))
  }
     
}
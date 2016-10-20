package graphx

import org.apache.spark.rdd.RDD
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.graphx.Edge
import org.apache.spark.graphx.VertexId
import org.apache.spark.graphx.Graph
import sun.security.provider.certpath.Vertex
import org.apache.spark.graphx.VertexRDD



object fifthJoin {
  

  def main(args: Array[String]): Unit = {
     
    val conf=new SparkConf
    conf.setMaster("local[2]").setAppName("fifthJoin")
    val sc=new SparkContext(conf)
    
    val users: RDD[(VertexId, (String, String))] =
    sc.parallelize(Array((3L, ("rxin", "student")), (7L, ("jgonzal", "postdoc")),
                       (5L, ("franklin", "prof")), (2L, ("istoica", "prof"))))
    // Create an RDD for edges
    val relationships: RDD[Edge[String]] =
    sc.parallelize(Array(Edge(3L, 7L, "collab"),    Edge(5L, 3L, "advisor"),
                       Edge(2L, 5L, "colleague"), Edge(5L, 7L, "pi")))
    val graph=Graph(users,relationships)
    graph.vertices.foreach(print(_))
   
   // graph.vertices.filter{case (id,(name,pos))=>pos=="student"}.count();
  //  val newGraph=graph.mapVertices()


  }


}
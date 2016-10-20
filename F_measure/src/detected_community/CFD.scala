package detected_community

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

object CFD {
  var NodePairNum=0;
  def main(args: Array[String]): Unit = {
    readfile()
  }
  def readfile():Int=
  {
    val conf=new SparkConf
    conf.setMaster("local[2]").setAppName("CFD")
    val sc=new SparkContext(conf)
    val FileData=sc.textFile("/home/hu/code/F_measureOld1/need/cFinder/k=3/communities");
    val Pairs=lines(FileData)
    val distinctPairs=Pairs.distinct()//去重出现错误
    distinctPairs.foreach(calcuteNum)//这里是对的
    println(NodePairNum+"===========")//为什么每次的结果不一样
    return NodePairNum
    
  }
  //RDD 和 List 性质类似 表示集合 
  //map 可以单个元素 flatMap 是 合并集合元素 所以flatMap的参数是元素集合
  def lines(rdd:RDD[String]):RDD[String]=
  {
     rdd.flatMap(split)
  }
  def split(line:String):List[String]=
  {
    var collects :List[String]=List()
    val array=line.split(": ")
    val array1=array(1).split(" ")
    val length=array1.length
    
    for(i<-0 until(length))
    {
       val first=array1(i)
      for(j<-i+1 until(length))
      {
        val  second=array1(j)
           if(first>second)
       {
       val pair=first+"_"+second;
       //添加元素
       collects=pair+:collects
       }
       else
       {
       val  pair=second+"_"+first
       collects=pair+:collects
       }
      }
     
    }
  return collects
  }
  def calcuteNum(n:String)=
  {
    NodePairNum=NodePairNum+1
  }

}
package detected_community
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

object realcommunity {
  def main(args: Array[String]): Unit = {
    readfile()
    val aa=Array("aa","bb")
    print(aa(1))
  }
  def readfile()=
  {
    val conf=new SparkConf
    conf.setMaster("local[2]").setAppName("realcommunity")
    val sc=new SparkContext(conf)
    val FileDate=sc.textFile("hdfs://192.168.10.101:8020/user/hu/community100.txt")
    val array=lines(FileDate)
    //X 表示是value 要转换的类型 y是value的类型 结合 形成 y :: x 我理解就是前面 是 String 后面是 List[String]
    //以key分组 返回的是 key ,排好序 的 表
    //val test=array.groupByKey()
    //test.foreach(println(_))
    //可以自己指定 要返回的value的形式  y :: x 表示的是结合在一起 把 前面的y (value)都结合在一起./
    val arrays=array.combineByKey(List(_),(x:List[String], y:String) => y :: x, (x:List[String], y:List[String]) => x ::: y)
    //返回 value 的 list 链表 RDD[List[String]]
    val iterator=arrays.values;
    val collectPairs=iterator.map(n=>collect(n))
    val allPairs=collectPairs.reduce((a,b)=>join(a,b))
    var length=allPairs.length;
    println(length)
    val distinctPairs=allPairs.distinct//去重
    val nodepairNumInReal=distinctPairs.length
    println(nodepairNumInReal+"======================")
    //allPairs.foreach { n => println(n) }
  }
  
  def lines(rdd:RDD[String]):RDD[(String,String)]=
  {
    rdd.flatMap(split)
  }
  def split(line:String):Array[(String,String)]=
  {
   val array=line.split("\t")
   val array1=array(1).split(" ")
   val array2=array1.map { node => (node,array(0)) }
   return array2
  }
  //主要理解他的 单个元素代表什么
  def collect(list:List[String]):List[String]= //能否直接返回 节点对
  {
    //var 动态可以变化的 val 固定元素 不可以改变
   var collects :List[String]=List()
   //用for循环吧
   val length=list.length
  
   for(i<-0 until(length))
   {
     val first=list(i)
     for(j<-i+1 until(length))
     {
       val second=list(j)
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
 
  def join(a:List[String],b:List[String]):List[String]=
  {
    val list=a ::: b
    return list
  }

}
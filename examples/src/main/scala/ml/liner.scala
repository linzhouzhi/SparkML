package ml

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * Created by lzz on 16/9/29.
  */
object liner extends App{
  val sparkConf = new SparkConf().setMaster("local")
    .setAppName("clean app")
  val sc = new SparkContext(sparkConf)

  val PATH = "file:///Users/lzz/work/SparkML/"

  val raw_data = sc.textFile( PATH+"data/bike/hour_noheader.csv")
  val num_data = raw_data.count()
  val records = raw_data
  val first = records.first
  println( first )
  println( num_data )

  records.cache()

  def get_mapping( rdd: RDD[String], idx: Int)={
    rdd.map( x => x(idx) ).distinct().zipWithIndex().collectAsMap()
  }

  println( "mapping of first categorical feasture column: " + get_mapping( records, 3 ).toString() )

}

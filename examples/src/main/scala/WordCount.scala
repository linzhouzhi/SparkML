/**
  * Created by lzz on 16/9/15.
  */
import org.apache.spark.{SparkConf, SparkContext}

object WordCount extends App{
  val sparkConf = new SparkConf().setMaster("local")
    .setAppName("clean app")
  val sc = new SparkContext(sparkConf)

  val line = sc.textFile("hdfs://localhost:9000/word_count.txt")
  line.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_).take(10).foreach(println)
  sc.stop()

}

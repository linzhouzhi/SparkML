package ml
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.mllib.util.MLUtils

/**
  * Created by lzz on 16/10/23.
  */
class LogisticRegressionWithElasticNetExample extends  App{

  val sparkConf = new SparkConf().setMaster("local")
    .setAppName("clean app")
  val sc = new SparkContext(sparkConf)
  val sqlContext= new org.apache.spark.sql.SQLContext(sc)
  import sqlContext.implicits._
  // Load training data
  val training = MLUtils.loadLibSVMFile(sc, "data/mllib/sample_libsvm_data.txt").toDF()

  val lr = new LogisticRegression()
    .setMaxIter(10)
    .setRegParam(0.3)
    .setElasticNetParam(0.8)

  // Fit the model
  val lrModel = lr.fit(training)

  // Print the weights and intercept for logistic regression
  println(s"Weights: ${lrModel.weights} Intercept: ${lrModel.intercept}")
}

# SparkML

     基于spark的机器学习项目，采用的工具是jupyter是为例方便代码运行和过程的描述。
### 目录介绍
* data／  －－机器学习调用的数据
* example／  －－项目中使用的例子源码
* 数学基础／ －－包含了机器学习使用到的数学知识
* 矩阵与向量／ －－机器学习使用的数据类型
* 基本统计／ －－Spark ML提供的统计方法
* spark_kernel安装.md  －－ jupyter 安装 Spark 的支持,方便算法描述和解释
* xxxxx／ －－其它目录看名称基本可以理解

### Spark MLlib 介绍
  Spark机器学习库有两套API，ml和mllib,其中Spark.ml使用的数据类型是DataFrame，而spark.mllib使用的是RDD，目前spark会一直保留基于RDD的mllib 但是不会再新增新的功能 （等spark.ml可以覆盖大部分的特性，spark.mllib 将会被废弃）
    

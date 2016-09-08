* Jupyter安装spark kernel

1 安装好spark 和 sbt
2 安装toree
#git clone https://github.com/apache/incubator-toree.git
#cd incubator-toree 修改 export APACHE_SPARK_VERSION=1.6.1
#make build
#make dist
3 修改
#cd  /Users/lzz/Library/Jupyter/kernels
#mkdir spark
#vim kernel.json
-----------------file-----------------
{
"display_name": "Spark 1.5.2 (Scala 2.10)",
"lauguage_info": {"name": "scala"},
"argv": [
    "/Users/lzz/soft_install/incubator-toree/dist/toree/bin/run.sh",
        "--profile",
	    "{connection_file}"
],	    
"codemirror_mode": "scala",
"env":{
	"SPARK_OPTS": "--master=local[2] --driver-java-options=-Xms1024M --driver-java-options=-Xms4096M --driver-java-options=-Dlog4j.logLevel=info",
    	"MAX_INTERPRETER_THREADS": "16",
        "CAPTURE_STANDARD_OUT": "true",
	"CAPTURE_STANDARD_ERR": "true",
        "SEND_EMPTY_OUTPUT": "false",	    
	"SPARK_HOME": "/Users/lzz/soft_install/hadoop/spark",
	"PYTHONPATH": "/Users/lzz/soft_install/hadoop/spark/python:/Users/lzz/soft_install/hadoop/spark/python/lib/py4j-0.8.2.1-src.zip"
      }
}
4 查看结果
upyter kernelspec list

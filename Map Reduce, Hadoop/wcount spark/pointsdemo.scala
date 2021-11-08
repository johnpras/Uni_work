import org.apache.spark.{SparkConf, SparkContext}

object wordcount {

  def main(args: Array[String]): Unit = {

    // Create spark configuration
    val sparkConf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("wordcount")

    // Create spark context
    val sc = new SparkContext(sparkConf)  

    val currentDir = System.getProperty("user.dir")  
    val inputFile = "file://" + currentDir + "/file*"
    val outputDir = "file://" + currentDir + "/outputtext"
    val txtFile = sc.textFile(inputFile,4)


    val result = txtFile.flatMap(line => line.split(" ")) // split each line based on spaces

      .map(word => (word.toLowerCase.head,1)) // map each word to (lowercase letter,1) pairs
      .reduceByKey(_+_) // reduce by key (sum the values of the same key)
      .collect() // collect results back to the driver
      .foreach(println) // print results

    sc.stop()

  }
}

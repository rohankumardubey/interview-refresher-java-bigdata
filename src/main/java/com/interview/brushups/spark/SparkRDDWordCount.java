package com.interview.brushups.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class SparkRDDWordCount {
    public static void main(String[] args) {

        SparkConf conf =new SparkConf().setMaster("local").setAppName("SparkRDDWordCount");
        JavaSparkContext javaSparkContext = new JavaSparkContext(conf);

        JavaRDD<String> inputData = javaSparkContext.textFile("word-count.txt");
        JavaRDD<String> words = inputData.flatMap(line -> Arrays.asList(line.split(" ")).iterator());
        JavaPairRDD<String,Integer> wordMapTo1 = words.mapToPair(word -> new Tuple2<String,Integer>(word,1));

        /*
         The above code can also be simplified an written as below.
         JavaPairRDD<String,Integer> flattenPairs = inputData.flatMapToPair(text -> Arrays.asList(text.split(" "))
         .stream().map(word -> new Tuple2<String, Integer>(word,1))
         .iterator());
        */

        JavaPairRDD<String, Integer> wordCountRDD = wordMapTo1.reduceByKey((v1,v2) -> v1 + v2);
        wordCountRDD.saveAsTextFile("spark-output");
    }
}
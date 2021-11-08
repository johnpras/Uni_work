package org.myorg;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class wcount {

  public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, MyCompositeKey, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private MyCompositeKey mck = new MyCompositeKey();

    public void map(LongWritable key, Text value, OutputCollector<MyCompositeKey, IntWritable> output, Reporter reporter) throws IOException {
      String filename = ((FileSplit) reporter.getInputSplit()).getPath().getName();
      String line = value.toString();
      StringTokenizer tokenizer = new StringTokenizer(line);
      while (tokenizer.hasMoreTokens()) {
	String token = tokenizer.nextToken().toLowerCase();
	char tmp;
        if( (token.matches("(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z).*"))) {
		tmp=token.charAt(0);
		token=String.valueOf(tmp);
        	mck.set(token,filename);
        	output.collect(mck, one);
      }
      }
    }
}

  public static class Reduce extends MapReduceBase implements Reducer<MyCompositeKey, IntWritable, MyCompositeKey, IntWritable> {
    public void reduce(MyCompositeKey key, Iterator<IntWritable> values, OutputCollector<MyCompositeKey, IntWritable> output, Reporter reporter) throws IOException {
      int sum = 0;
      while (values.hasNext()) {
        sum += values.next().get();
      }
	
      output.collect(key, new IntWritable(sum));

    }
  }


  public static void main(String[] args) throws Exception {
    JobConf conf1 = new JobConf(wcount.class);
    conf1.setJobName("wcount1");

    conf1.setOutputKeyClass(MyCompositeKey.class);
    conf1.setOutputValueClass(IntWritable.class);

    conf1.setMapperClass(Map.class);
    conf1.setCombinerClass(Reduce.class);
    conf1.setReducerClass(Reduce.class);

    conf1.setInputFormat(TextInputFormat.class);
    conf1.setOutputFormat(TextOutputFormat.class);

    conf1.setNumReduceTasks(3);

    FileInputFormat.setInputPaths(conf1, new Path(args[0]));
    FileOutputFormat.setOutputPath(conf1, new Path(args[1]));

    JobClient.runJob(conf1);

    
}

}


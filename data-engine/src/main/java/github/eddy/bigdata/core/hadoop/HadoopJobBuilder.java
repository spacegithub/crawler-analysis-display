package github.eddy.bigdata.core.hadoop;

import static org.apache.hadoop.mapreduce.Job.getInstance;

import com.mongodb.hadoop.MongoInputFormat;
import com.mongodb.hadoop.MongoOutputFormat;
import github.eddy.bigdata.core.common.JobEnum;
import github.eddy.common.HadoopTools;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

@Slf4j
public class HadoopJobBuilder {

  private Job job;
  private Configuration conf;

  public HadoopJobBuilder config(Configuration conf, JobEnum jobEnum) throws IOException {
    this.conf = conf;
    this.job = getInstance(conf, jobEnum.name());
    job.setJarByClass(this.getClass());
    switch (jobEnum) {
      case mongodb:
        return initMongo();
      default:
        return this;
    }
  }

  private HadoopJobBuilder initMongo() throws IOException {
    job.setInputFormatClass(MongoInputFormat.class);
    job.setOutputFormatClass(MongoOutputFormat.class);
    return this;
  }

  public HadoopJobBuilder addMapper(Class<? extends Mapper> mapperClass) throws IOException {
    HadoopTools.addMapper(job, mapperClass, conf);
    return this;
  }

  public HadoopJobBuilder setReducer(Class<? extends Reducer> reducerClass) {
    HadoopTools.setReducer(job, reducerClass, conf);
    //job.setCombinerClass(reducerClass);
    return this;
  }

  public HadoopJobBuilder afterReducer(Class<? extends Mapper> mapperClass) throws IOException {
    HadoopTools.afterReducer(job, mapperClass, conf);
    return this;
  }

  public Job build() {
    return job;
  }
}
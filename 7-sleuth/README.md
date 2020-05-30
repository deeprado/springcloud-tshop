
# 下载 zipkin 

https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/

# 启动 zipkin

java -jar zipkin-server-2.12.9-exec.jar

默认 9411 端口

# 依赖 

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-zipkin</artifactId>
		</dependency>

# 配置

spring:
  zipkin:
      base-url: http://localhost:9411
  sleuth:
    sampler:
      percentage: 1.0  # 抽取百分比 

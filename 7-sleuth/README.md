
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


# 整合ELK

## 环境
docker run -dit --name elk-ins \
    -p 5601:5601 \
    -p 9200:9200 \
	-p 9300:9300 \
    -p 5044:5044 \
	-v /opt/elk-data:/var/lib/elasticsearch \
    -v /etc/localtime:/etc/localtime \
	--restart=always \
    sebp/elk:671
	
firewall-cmd --zone=public --add-port=9200/tcp --permanent 
firewall-cmd --zone=public --add-port=5601/tcp --permanent 
firewall-cmd --zone=public --add-port=5044/tcp --permanent 

## 配置

参考 order-sleuth resources/logback-spring.xml

## 依赖

		<dependency>
			<groupId>net.logstash.logback</groupId>
			<artifactId>logstash-logback-encoder</artifactId>
			<version>6.3</version>
		</dependency>
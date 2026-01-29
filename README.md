# kafkadb

kafka 설치

참조 URL
https://cladren123.tistory.com/190

Kafka 다운 로드
https://kafka.apache.org/community/downloads/ 에서 적당한 버전을 찾아 다운로드합니다.

1. zookeeper 실행
C:\prj\kafka_2.13-3.8.1>bin\windows\zookeeper-server-start.bat config\zookeeper.properties

2. kafka 실행
C:\prj\kafka_2.13-3.8.1>bin\windows\kafka-server-start.bat config\server.properties


3.confluent 실행
C:\prj\confluent-7.1.0>bin\windows\connect-distributed.bat config\connect-distributed.properties

4. kafka-ui 실행
C:\prj\kafka-ui-api-v0.7.2>start.bat
kafka-ui 접속
http://localhost:8180/


https://packages.confluent.io/archive/
에서 적당한 버전을 찾아 다운로드 합니다.


C:\prj\confluent-7.1.0>bin\windows\connect-distributed.bat config\connect-distributed.properties
에러 사항

Classpath is empty. Please build the project first e.g. by running 'gradlew jarAll’

닫기
windows를 진행하다 보면 위의 명렁어 에러 사항을 만날 수도 있습니다. 

이는 binary 파일을 다운 받고 실행시킨 것이 아니라 source 파일을 다운 받고 실행해 생긴 문제라 합니다. 

 

해결 방법은 다음과 같습니다. 

 

C:\prj\confluent-7.1.0\bin\windows\kafka-run-class.bat 파일을 수정하면 됩니다. 

rem Classpath addition for core 부분 위 다음 코드를 삽입합니다. 

rem classpath addition for LSB style path
if exist %BASE_DIR%\share\java\kafka\* (
	call:concat %BASE_DIR%\share\java\kafka\*
)




Kafka Connect JDBC
Kafka Connect JDBC는 Kafka와 RDBMS 사이에 데이터를 이동시키는데 사용되는 Kafka Connect의 플러그인 입니다.

JDBC API 를 활용하여 다양한 RDBMS와 상호작용 합니다. 

 

 

Kafka Connect JDBC 다운 

Kafka Connect와 마찬가지로 Confluenct 사이트에서 다운 받을 수 있습니다.

 
다운로드 사이트
https://www.confluent.io/hub/confluentinc/kafka-connect-jdbc


Kafka Connect의 설정 파일 변경 

config 폴더가 없는 경우에는 C:\prj\kafka_2.13-3.8.1\config 폴더를 복사하여 사용하시면 됩니다.

C:\prj\kafka_2.13-3.8.1\config\connect-distributed.properties 파일의 내용을 변경합니다.

맨 아래에 있는 plugin.path를 confluentinc-kafka-connect-jdbc의 lib 폴더의 경로로 변경합니다. 

이 때, 경로 사이에는 더블 역슬래쉬를 사용해야 합니다. ( \\ )

plugin.path=\C:\\prj\\confluentinc-kafka-connect-jdbc-10.6.3\\lib


mysql 과 kafka 연동을 위해서 mysql connector 라이브러리를 confluent 폴더에 `share/java/kafka`로 이동
mysql connector 는 이슈가 존재합니다.(링크)
maria-db-client를 추가해주셔 합니다.

mysql-connector-j-9.5.0.jar
mariadb-java-client-3.5.7.jar


C:\prj\confluent-7.1.0>bin\windows\connect-distributed.bat config\connect-distributed.properties


source connector 등록
127.0.0.1:8083/connectors

incrementing 예제
{
	"name" : "my-source-connect",
	"config" : {
		"connector.class" : "io.confluent.connect.jdbc.JdbcSourceConnector",
		"connection.url":"jdbc:mysql://localhost:3306/apigateway",
		"connection.user":"root",
		"connection.password":"password",
		"mode": "incrementing",
		"incrementing.column.name" : "id",
		"table.whitelist":"user",
		"topic.prefix" : "my_topic_",
		"tasks.max" : "1"
		}
}

timestamp 예제 
{
	"name" : "my-source-connect",
	"config" : {
		"connector.class" : "io.confluent.connect.jdbc.JdbcSourceConnector",
		"connection.url":"jdbc:mysql://localhost:3306/apigateway",
		"connection.user":"root",
		"connection.password":"hyungsuu1!",
		"mode": "timestamp",
		"timestamp.column.name" : "reg_date, upd_date",
		"table.whitelist":"user",
		"topic.prefix" : "my_topic_",
		"tasks.max" : "1",
		 "poll.interval.ms": 1000
		}
}


mode 가 timestamp 인 경우에는 최소한 두개의 컬럼을 사용해야 할듯 최초등록일, 최종 수정일.

get  127.0.0.1:8083/connectors


get 127.0.0.1:8083/connectors/커넥터 이름/status

get 127.0.0.1:8083/connectors/my-source-connect/status



put  127.0.0.1:8083/connectors/커넥터 이름/pause
put  127.0.0.1:8083/connectors/my-source-connect/pause

put  127.0.0.1:8083/connectors/커넥터 이름/resume
put  127.0.0.1:8083/connectors/my-source-connect/resume

delete 127.0.0.1:8083/connectors/커넥터 이름
delete 127.0.0.1:8083/connectors/my-source-connect

curl http://localhost:8083/connectors/<connector_name>/tasks | jq
curl http://localhost:8083/connectors/<connector_name>/tasks/0/status | jq

curl -X POST http://localhost:8083/connectors/<connector_name>/restart
curl -X POST http://localhost:8083/connectors/my-sink-connect/tasks/0/restart



Connector
connector 목록 조회
curl -X GET "http://localhost:8083/connectors/"
connector 상세 정보 조회
curl -X GET "http://localhost:8083/connectors?expand=status&expand=info"
connector config 조회
GET 으로도 동일하게 동작함
curl -X PUT "http://localhost:8083/connectors/{connector_name}/config
특정 connector 상태 조회
curl -X GET "http://localhost:8083/connectors/{connector_name}/status"
connector 재시작
※ task는 재시작되지 않음
curl -X POST "http://localhost:8083/connectors/{connector_name}/restart"
connector 일시중지 (pause)
비동기 방식이므로 상태 조회시 바로 PAUSE 를 리턴하지 않을 수 있음
curl -X PUT "http://localhost:8083/connectors/{connector_name}/pause"
connector resume
pause 상태인 connector 를 resume시킨다.
비동기 방식이므로 상태 조회시 바로 RUNNING을 리턴하지 않을 수 있음
curl -X PUT "http://localhost:8083/connectors/{connector_name}/resume"
 

connector 삭제
curl -X DELETE "http://localhost:8083/connectors/{connector_name}
Task
connector의 task 목록 조회
curl -X GET "http://localhost:8083/connectors/{connector_name}/tasks"
connector 의 task 상태 조회
curl -X GET "http://localhost:8083/connectors/{connector_name}/tasks/{task_id}/status"
connector 의 task 재시작
※ connector 가 RUNNING, task 가 FAIL 일 경우 사용
curl -X POST "http://localhost:8083/connectors/{connector_name}/tasks/{task_id}/restart"

Topic
connector topic 조회
curl -X GET "http://localhost:8083/connectors/{connector_name}/topics"
connector topic reset
curl -X PUT "http://localhost:8083/connectors/{connector_name}/topics/reset"
Connector Plugin
Kafka Connector Cluster 에 설치된 모든 plugin 목록 조회
curl -X GET "http://localhost:8083/connector-plugins"
Kafka Connector plgin validate



Sink Connector 등록
127.0.0.1:8083/connectors
 

보낼 데이터 

{
	"name":"my-sink-connect",
	"config":{
	"connector.class":"io.confluent.connect.jdbc.JdbcSinkConnector",
	"connection.url":"jdbc:mysql://localhost:3306/apigateway",
	"connection.user":"root",
	"connection.password":"hyungsuu1!",
	"auto.create":"true",
	"auto.evolve":"true",
	"delete.enabled":"true",
	"tasks.max":"1",
	"topics":"my_topic_user"
	}
}

{
	"name":"my-sink-connect",
	"config":{
	"connector.class":"io.confluent.connect.jdbc.JdbcSinkConnector",
	"connection.url":"jdbc:mysql://localhost:3306/apigateway",
	"connection.user":"root",
	"connection.password":"hyungsuu1!",
	"topics": "my-topic-user,my-topic-admin",
	"topic.prefix" : "my_topic_",
    "insert.mode": "upsert",
    "pk.mode": "record_value",
    "pk.fields": "user_id",
    "auto.create": "false",
    "auto.evolve": "true",
	"tasks.max":"1",
	"behavior.on.null.values":"ignore",
	"table.name.format":"user"
	}
}


    
Sink Connector가 토픽에서 가져온 데이터를 관계형 DB(MySQL, PostgreSQL 등)에 실제 저장하는 구조입니다.
자동 생성: auto.create=true 설정 시 토픽 이름을 기반으로 테이블을 생성합니다.
수동 생성: auto.create=false 또는 스키마 불일치 문제로 인해, 미리 DB에 테이블을 생성하고 컬럼 구조를 정의해야 할 수 있습니다. 
table.name.format: 토픽 이름을 테이블 이름으로 매핑할 때 사용하며, {topic} 플레이스홀더를 이용해 동적으로 이름을 만들 수 있습니다. 예: my_data_{topic}.
일단은 Sink Connector가 자동생성해준 my_topic_user를 사용했음



{"partitions": [
    {"topic": "test.topic", "partition": 0, "offset": 123},
    {"topic": "test.topic", "partition": 1, "offset": 124},
    {"topic": "test.topic", "partition": 2, "offset": 125},
    {"topic": "test.topic", "partition": 3, "offset": 130}
    ],
 "version":1 }
in/kafka-delete-records.sh --bootstrap-server localhost:9092 --offset-json-file delete-topic.json

Tip

topic 생성
C:\prj\kafka_2.13-3.8.1>bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --topic test-topic

생성되어진 topic 목록 조회
C:\prj\kafka_2.13-3.8.1>bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092

생성되어진 topic에 메세지  보내기
C:\prj\kafka_2.13-3.8.1>bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test-topic

topic에 있는메세지 가져오기
C:\prj\kafka_2.13-3.8.1>bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test-topic

토픽의 데이터 확인
C:\prj\kafka_2.13-3.8.1>bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic my_topic_user --from-beginning

topic에 내용 확인
C:\prj\kafka_2.13-3.8.1>bin\windows\kafka-topics.bat --describe --bootstrap-server localhost:9092 --topic my_topic_user

토픽 삭제하기

config 폴더 안에있는 server.properties 파일을 vi 편집기로 열어서 아래 내용 추가

delete.topic.enable=true
그리고 다시 상위폴더로 이동해서
C:\prj\kafka_2.13-3.8.1>bin\windows\kafka-topics.bat --zookeeper localhost:2181 --delete --topic test


컨슈머 그룹 리스트 확인

bin\windows\kafka-consumer-groups.bat --bootstrap-server localhost:9092 --list
컨슈머 그룹 상태 확인

bin\windows\kafka-consumer-groups.bat --bootstrap-server localhost:9092 --group test-consumer --describe


https://devocean.sk.com/blog/techBoardDetail.do?ID=164372
# Project information

## Requirements
- MVC Structure
- use TCP, DB(MYBATIS) ORM
- DB
    - MESSAGE INTERFACE
    - IMPL TEST
    - TCP Definition
    - use JSON
    - Create SEND, SEND_ACK, REPORT, REPORT_ACK
- MESSAGE QUEUE INTERFACE
    - Try a Managed Messaging Protocol
    - interface.(SEND, json);


## TODO
1. service impl 구현
2. 비즈니스 로직 구현에 대한 생각 더하기


## Complete
- TestCode에 TestContainer 생성 후 테스트 
- 프로젝트 방향성 재 확립 
- 프로젝트 구조 리펙토링 
- Repository와 service 비즈니스 로직 간 명확한 구분 짓기 
- Interface CrudRespositoty에 명시된 메소드들 구현하기
- 테스트 코드 재 작성
- Receive 테이블에 참조되는 SendTable 구현체에 대한 수정


## COMMIT
| DATE         | content                                                                                                                                                |
|--------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| [2022-10-05] | Httpserver 생성, password 디코딩 비즈니스 로직 추가, 각 목적별 Dto 생성, Exception으로 오류 처리 , Http 요청에 따른 Request 구현, Object Mapper 로직 추가, Httpserver 테스트 코드 작성, handler작성 중 |
| [2022-10-06] | ReceiveServer 구현, Model 구현 ( Message,LoginMessage ), HttpServerHandler에 대한 테스트 코드 작성 완료 , PacketType enum 작성, 잘못된 요청에 대한 BadSendPacket 구현중             |                                                                                                     |
| [2022-10-17] | Mybatis Mapping 될 MessageDAO 및 implement구현 , DAO 테스트 코드 구현 , 각 목적에 따른 Exception 구현, 프로젝트 방향서 재성립                                                       |                                                                                                     |
| [2022-10-19] | 인터페이스 CrudRepository에서 명시한 메소드들 구현, Repository 구현체에 대한 테스트 코드 작성, Repository와 비즈니스 로직에 대한 명확한 구분을 짓기위한 프로젝트 구조 개선, resource에 설정파일 두지 않고 리소스 루트 폴더에 지정  |                                                                                                     |
| [2022-10-21] | 관계형 데이터 베이스 구축 , Repository 구현체 각각 테스트 코드 작성 완료                                                                                                        |                                                                                                     |
| [2022-10-26] | Service 구현체 구현중, 모든 테스크 코드 성공 여부 체크 완료                                                                                                                 |                                                                                                     |

## Used Library
```c
  plugins {
    id 'java'
}
apply plugin: 'application'
group 'nanoit.kr'
version 'v0.0.1'

repositories {
    mavenCentral()
}
compileJava.options.encoding = 'UTF-8'
dependencies {
    implementation 'org.projectlombok:lombok:1.18.22'

    implementation group: 'org.apache.commons', name: 'commons-configuration2', version: '2.0'
    implementation group: 'org.bouncycastle', name: 'bcprov-jdk15on', version: '1.68'
    implementation group: 'commons-beanutils', name: 'commons-beanutils', version: '1.9.3'

    // TEST IMPLEMENTS
    testImplementation 'org.assertj:assertj-core:3.23.1'
    testImplementation "org.junit.jupiter:junit-jupiter:5.8.1"
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.2'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.2'
    testImplementation "org.testcontainers:testcontainers:1.17.3"
    testImplementation "org.testcontainers:junit-jupiter:1.17.3"
    testImplementation "org.testcontainers:postgresql:1.17.3"
    implementation group: 'com.google.inject', name: 'guice', version: '4.2.2'
    implementation group: 'org.mybatis', name: 'mybatis-guice', version: '3.17'

    // UTILS
    implementation 'org.projectlombok:lombok:1.18.24'
    compileOnly 'org.projectlombok:lombok:1.18.24'
    annotationProcessor 'org.projectlombok:lombok:1.18.24'
    implementation group: 'com.google.guava', name: 'guava', version: '31.1-jre'
    implementation group: 'com.fasterxml.jackson.core', name: 'jackson-databind', version: '2.13.4'

    // log
    implementation 'org.slf4j:slf4j-api:1.7.36'
    implementation 'ch.qos.logback:logback-classic:1.2.11'
    implementation 'ch.qos.logback:logback-core:1.2.11'
    implementation 'ch.qos.logback:logback-access:1.2.11'

    // HTTP
    implementation group: 'org.apache.httpcomponents.client5', name: 'httpclient5', version: '5.1.3'

    // DBCP2
    implementation group: 'org.apache.commons', name: 'commons-dbcp2', version: '2.9.0'

    // JDBC DRIVER
    implementation 'org.postgresql:postgresql:42.5.0'

    // JWT
    implementation group: 'io.jsonwebtoken', name: 'jjwt-api', version: '0.11.5'

    // DB
    implementation group: 'org.mybatis', name: 'mybatis', version: '3.5.11'
}

test {
    useJUnitPlatform()
}

sourceSets {
    main {
        resources {
            srcDir "./mapper/"
        }
    }
    test {
        resources {
            srcDir "./mapper/"
        }
    }
}
````
## Project Structure

```bash

├─config
│      config.properties
│      test.properties
│
├─gradle
│  └─wrapper
│          gradle-wrapper.jar
│          gradle-wrapper.properties
│
├─mapper
│      MSG_POSTGRESQL.xml
│
└─src
    ├─main
    │  ├─java
    │  │  └─kr
    │  │      └─nanoit
    │  │          ├─core
    │  │          │  └─db
    │  │          │          DataBaseSessionManager.java
    │  │          │
    │  │          ├─extension
    │  │          │      PathUtils.java
    │  │          │      Test.java
    │  │          │
    │  │          ├─http
    │  │          │      GetMessageHandler.java
    │  │          │
    │  │          ├─model
    │  │          │  ├─dto
    │  │          │  │      HttpBadResponseDto.java
    │  │          │  │      ServerInfo.java
    │  │          │  │      UserDto.java
    │  │          │  │
    │  │          │  ├─message
    │  │          │  │      LoginMessage.java
    │  │          │  │      MessageDto.java
    │  │          │  │      MessageType.java
    │  │          │  │
    │  │          │  └─message_Structure
    │  │          │      │  IndexHeader.java
    │  │          │      │  LengthHeader.java
    │  │          │      │  PacketType.java
    │  │          │      │
    │  │          │      ├─login
    │  │          │      │      IndexLogin.java
    │  │          │      │      LengthLogin.java
    │  │          │      │
    │  │          │      ├─report
    │  │          │      └─send
    │  │          │              IndexSend.java
    │  │          │              LengthSend.java
    │  │          │
    │  │          ├─old
    │  │          │  ├─client
    │  │          │  │      ClientCrypt.java
    │  │          │  │      SimpleClient.java
    │  │          │  │
    │  │          │  ├─controller
    │  │          │  │      SocketConfig.java
    │  │          │  │      SocketUtil.java
    │  │          │  │
    │  │          │  ├─decoder
    │  │          │  │      PacketMakeable.java
    │  │          │  │      PacketMakeableImpl.java
    │  │          │  │
    │  │          │  ├─exception
    │  │          │  │  │  DecryptException.java
    │  │          │  │  │  HttpException.java
    │  │          │  │  │  ReceiveException.java
    │  │          │  │  │
    │  │          │  │  └─message
    │  │          │  │          DeleteException.java
    │  │          │  │          InsertException.java
    │  │          │  │          SelectException.java
    │  │          │  │          SendException.java
    │  │          │  │          UpdateException.java
    │  │          │  │
    │  │          │  ├─http
    │  │          │  │      HealthHandler.java
    │  │          │  │      HttpResponseDto.java
    │  │          │  │      HttpServerHandler.java
    │  │          │  │      SandBoxHttpServer.java
    │  │          │  │
    │  │          │  ├─main
    │  │          │  │      Main.java
    │  │          │  │      Test.java
    │  │          │  │
    │  │          │  ├─util
    │  │          │  │      Base64Coder.java
    │  │          │  │      Crypt.java
    │  │          │  │      GlobalVariable.java
    │  │          │  │      Mapper.java
    │  │          │  │      ResponseUtil.java
    │  │          │  │      Verification.java
    │  │          │  │
    │  │          │  └─woker
    │  │          │          Receive.java
    │  │          │          Send.java
    │  │          │
    │  │          ├─repository
    │  │          │      ReceivedMessageRepository.java
    │  │          │      ReceivedMessageRepositoryImpl.java
    │  │          │      SendToTelecomMessageRepository.java
    │  │          │
    │  │          ├─service
    │  │          │      ReceivedMessageService.java
    │  │          │      ReceivedMessageServiceImpl.java
    │  │          │      SendToTelecomMEssageService.java
    │  │          │
    │  │          └─tcp
    │  └─resources
    └─test
        ├─java
        │  ├─kr
        │  │  └─nanoit
        │  │      └─http
        │  │              HttpServerHandlerTest.java
        │  │              SandBoxHttpServerTest.java
        │  │              SqlSessionTest.java
        │  │
        │  └─message
        │          ReceivedMessageRepositoryImplTest.java
        │
        └─resources
                logback-test.xml

```

# MİKROSERVİS İŞLEMLERİ VE NOTLARIM

## 1. Kurulum adımları

    ### 1.1. Boş bir gradle projesi açtık.
    ### 1.2. dependencies.gradle dosyasını kodladık
        #### 1.2.1. ext{} bloğu içerisindeki kütüphaneleri projemize dahil ettik.
        #### 1.2.2. versions{} bloğu içerisindeki kütüphanelerin versiyonlarını belirledik.
        #### 1.2.3. libs{} bloğu içerisinde kullanacağımız kütüphaneleri belirledil.
    ### 1.3. build.gradle dosyasını kodladık, bu dosya içinde tüm alt projelerimizde ortak
    olarak kullanacağımız kütüphaneleri belirledik.
    ### 1.4. uygulamamızın mimarisi gereği ihtiyaç duyduğumuz mikroservisleri belirleyerek onları
    modül olarak ekledik.
    ### 1.5. her bir modül için eklememiz gereken aşağıdaki kod bloğunu
    build.gradle dosyalarına ekledik.
```
    buildscript {
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${versions.springBoot}")
        }
    }
```
    ### 1.6. tüm modüllerimize monolitik mimaride kullandığımız default kodlamaları ekledik.
    ### 1.7. Eğer bir modül içinde kullanmak istediğimiz özel bağımlılıklar var ise bunları 
    build.gradle dosyalarına ekledik.

## 2. MongoDB Kurulum ve Kullanım

### 2.1. MongoDB Docker üzerinde çalıştırmak

    docker kurulu olan bir sistem üzerinde command satırına girerek aşağıda 
    yeralan komutları yazarak docker üzerinde çalıştırıyoruz.

    > docker run --name dockermongo -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=BilgeAdmin -e MONGO_INITDB_ROOT_PASSWORD=root -d mongo

### 2.2. MongoDB ye bağlanmak

    DİKKAT!!!
    mongodb kullanırken admin kullanıcısı ve şifrelerini veritabanlarına 
    erişmek için kullanmayınız.
    Yeni bir veritabanı oluşturmak için admin kullanıcısı ile işlem yapılır ve
    bu veritananına atanmak üzere yeni bir kullanıcı oluşturulur. böylelikle
    admin kullanıcısına ihtiyaç kalmadan DB üzerinde işlemler gerçekleştirilir.
    1- Öncelikle bir veritabanı oluşturuyorsunuz. (FacebookDB)
    2- mongosh  kullanarak konsolda 'use DB_ADI' yazıyorsunuz ve ilgili DB ye geçiş yapıyorsunuz
    3- yine aynı ekranda yeni bir kullanıcı oluşturmanız gereklidir. bu kullanıcı yetkili olacaktır.
    db.createUser({
        user: "Java7User",
        pwd: "root",
        roles: ["readWrite", "dbAdmin"]
    })
    -- db.createUser({user: "Java7User",pwd: "root",roles: ["readWrite", "dbAdmin"]})

##  3. RabbitMQ Kurulum ve Kullanım

    ### 3.1. RabbitMQ Docker üzerinde çalıştırmak
    docker run -d --name my-rabbitmq -e RABBITMQ_DEFAULT_USER=java7 -e RABBITMQ_DEFAULT_PASS=root -p 5672:5672 -p 15672:15672 rabbitmq:3-management

    ### 3.2. RabbitMQ ye bağlanmak
    gradle import -> org.springframework.boot:spring-boot-starter-amqp:VERSION
    Rabbit Config yapılandırılır ve kuyruk yapısı tanımlanır.

## 4. Zipkin Server kurmak ve Kullanmak

    docker run --name zipkinfb -d -p 9411:9411 openzipkin/zipkin
    
    Zipkin için gerekli bağımlılılar:    
    'org.springframework.cloud:spring-cloud-starter-sleuth:3.1.7'
    'org.springframework.cloud:spring-cloud-sleuth-zipkin:3.1.7'

    application.yml içine eklenilecek kodlar:
    zipkin:
        enabled: true
        base-url: http://localhost:9411
        service:
          name: config-server

## 5. Redis Kurulum ve Kullanım

    docker run --name localredis -d -p 6379:6379 redis

    Redis için gerekli bağımlılılar:
    'org.springframework.boot:spring-boot-starter-data-redis:$VERSION'

    Redis için bağlantı kodlamalarını yapmakmız gerekli:
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        return new LettuceConnectionFactory(
                new RedisStandaloneConfiguration("localhost", 6379));
    }

## 6. ElasticSearch Kurulum ve Kullanım

    DİKKAT!!!
    Spring ile kullanımında sürüm önemlidir. Hangi Spring boot sürümünü
    kullandıysanız ona uygun bir ElasticSearch sürümü kullanmalısınız.

    1- docker network create somenetwork
    2- docker run -d --name elasticsearch --net somenetwork -p 9200:9200 -p 9300:9300 -e ES_JAVA_OPTS="-Xms512m -Xmx2048m" -e "discovery.type=single-node" elasticsearch:7.17.9

    ElasticSearch için gerekli bağımlılılar:
    'org.springframework.boot:spring-boot-starter-data-elasticsearch:$VERSION'

## 7. Projenin Docker image olarak oluşturulması

    Herhangi bir projenin paket haline getirilmesi için öncelikle
    1- Gradle -> [Projenin Adı ] -> Tasks -> build -> build
    2- Gradle -> [Projenin Adı ] -> Tasks -> build -> buildDependents
    bu iki adımdan sonra projenin altında build klasörü oluşur bu klasöt
    içerisinde jar dosyası oluşur. build->libs-> [Projenin Adı].jar

### 7.1 Dockerfile

    Dockerfile bir docker image oluşturmak için kullanılan özel 
    bir dosyadır. bunun içinde ihtiyaç duyulan tüm komutlar yer alır.
    bu komutlar ile microservisimizin çalışması için gerekli olan 
    parametreler ve bağımlılıklar belirtilir.
````
    # İşletim sistemi ve Java JDK eklenir.
    # FROM amazoncorretto:17 -- amazon corretto ile java jdk17 sürümü kullanılacak demektir.
    FROM azul/zulu-openjdk-alpine:17.0.7
    # build aldığımız jar dosyasını docker imajımızın içine kopyalıyoruz.
    COPY ConfigServerGit/build/libs/ConfigServerGit-v.1.0.jar app.jar
    # docker imajımızın çalışması için java uygulamamızı tetikliyoruz.
    ENTRYPOINT ["java","-jar","/app.jar"]
````
## Docker Imaje oluşturmak
    DİKKAT!!!!
    Docker image oluştururken docker.hub üzerindeki repoya önderilmek istenilen 
    image lar için isimlendirmeyi doğru yapmanız gereklidir. 
    hub.docker repo adınız / image adınız : versiyon numarası
    şeklinde yazmanız gereklidir.
    DİKKAT!!!! 
    ifade sonunda olan nokta (.) unutulmamalıdır. rastgele bir nokta değildir.
    docker build -t javaboost2/java7-configservergit:v.1.0 .
    docker build -t javaboost2/java7-authservice:v.1.1 .
    docker build -t javaboost2/java7-userservice:v.1.0 .
    docker build -t javaboost2/java7-gatewayservice:v.1.0 .

FROM adoptopenjdk/openjdk11:alpine-jre

# указываем точку монтирования для внешних данных внутри контейнера (как мы помним, это Linux)
VOLUME /tmp

# внешний порт, по которому наше приложение будет доступно извне
EXPOSE 8090

# указываем, где в нашем приложении лежит джарник
ARG JAR_FILE=target/ToDoList-1.0-SNAPSHOT.jar
# добавляем джарник в образ под именем Backend.jar
ADD ${JAR_FILE} Backend.jar

# команда запуска джарника
ENTRYPOINT ["java","-jar","/Backend.jar"]
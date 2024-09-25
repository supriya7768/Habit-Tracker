FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu

WORKDIR /habitTracker

COPY build/libs/Habits-Tracker-0.0.1-SNAPSHOT.jar habit-tracker.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar","habit-tracker.jar"]
# HeatPump

EtherCAT Application for controlling the Heatpump machine in Viktor's heating room. Written in Java and using the
EtherCAT fieldbus technology:  
SOEM: https://openethercatsociety.github.io/ and its  
Java implementaion ihmc-ethercat-master: https://github.com/ihmcrobotics/ihmc-ethercat-master.

## Setup

### Prerequirements SOEM and ihmc-ethercat-master

see https://github.com/ihmcrobotics/ihmc-ethercat-master

### Installing SOEM

sudo add-apt-repository ppa:halodirobotics/ppa
sudo apt update
sudo apt install soem

#### Linux Mint

append the following to /etc/security/limits.conf

```
vrees       soft    cpu     unlimited
vrees       -       rtprio  100
vrees       -       nice    40
vrees       -       memlock unlimited
```

see https://github.com/ihmcrobotics/ihmc-ethercat-master/issues/5

#### Avoid to run IntelliJ as root

In order to create raw sockets root access is needed. Therefore the excecutable has to run as root. As an alternative
you can set capabilities to the executable:

```
sudo setcap cap_net_raw,cap_net_admin=eip /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java
sudo setcap cap_net_raw,cap_net_admin=eip /usr/lib/jvm/java-8-openjdk-amd64/bin/java
sudo setcap cap_net_raw,cap_net_admin=eip /usr/lib/jvm/java-11-openjdk-amd64/bin/java
sudo setcap cap_net_raw,cap_net_admin=eip /usr/lib/jvm/adoptopenjdk-11-hotspot-amd64/bin/java
sudo setcap cap_net_raw,cap_net_admin=eip /home/vrees/.sdkman/candidates/java/current/bin/java

echo '/home/vrees/.sdkman/candidates/java/11.0.2-open/lib/jli' | sudo tee -a /etc/ld.so.conf.d/java

Remove:
sudo setcap -r  /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java
sudo setcap -r  /home/vrees/.sdkman/candidates/java/current/bin/java

```

https://github.com/ihmcrobotics/ihmc-ethercat-master/issues/5

If that isn't a feasible solution, there is a workaround, but we don't suggest that you use it on a production target. You can modify the user's PAM ulimit limits. Edit the file **/etc/security/limits.conf** with the following additions:

```
vrees       soft    cpu     unlimited
vrees       -       rtprio  100
vrees       -       nice    40
vrees       -       memlock unlimited
```

```
cd /etc/ld.so.conf.d/
sudo vi java.conf
and add /home/vrees/.sdkman/candidates/java/11.0.5-open/lib/jli
sudo ldconfig
```

**! don't forget to restart**

### Compiling C library and SWIG wrapper

Requirements

    CMake  (sudo apt install cmake)
    OpenJDK 8  (--> openjdk11)
    Swig 3.0.8 or higher. (sudo apt install swig)

A gradle wrapper is provided, optionally you can use your system gradle by replacing "./gradlew" with gradle.

    cd ihmc-ethercat-master
    mkdir build
    cd build
    cmake -DCMAKE_BUILD_TYPE=Release ..
    make
    ../gradlew publishToMavenLocal -Ptarget=JAVA   (gradle publishToMavenLocal -Ptarget=JAVA)
    ../gradlew publishToMavenLocal -Ptarget=PLATFORM

## Useful Commands

####listen EADDRINUSE

Linux:

```
lsof -i tcp:9060
kill -9 xxx
```

Windows:

```
netstat -a -o -n
taskkill /F /PID (yourprocessID)

In windows 10, open task manager and go to Details tab.
There you will find node.exe running.
End that task and your port will be released.
```

## Links

**JHister:** https://www.jhipster.tech

**Spring Boot State Machine:** https://docs.spring.io/spring-statemachine/docs/2.1.3.RELEASE/reference

**react-redux-typescript-guide:**
https://github.com/piotrwitek/react-redux-typescript-guide/blob/master/README.md#react---type-definitions-cheatsheet

**Techniques for Animating on the Canvas in React:**
https://dzone.com/articles/techniques-for-animating-on-the-canvas-in-react

**React Snipplets:**
https://github.com/jinsihou19/ReactSnippets/blob/master/README.md

**React / Redux Tutorial:**
https://reactjs.org/tutorial/tutorial.html
https://react-redux.js.org/introduction/quick-start

**reactstrap**
https://reactstrap.github.io/components/alerts

**IOTstack**
https://github.com/gcgarner/IOTstack

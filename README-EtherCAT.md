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

```

https://github.com/ihmcrobotics/ihmc-ethercat-master/issues/5

If that isn't a feasible solution, there is a workaround, but we don't suggest that you use it on a production target. You can modify the user's PAM ulimit limits. Edit the file **/etc/security/limits.conf** with the following additions:

```
vrees       soft    cpu     unlimited
vrees       -       rtprio  100
vrees       -       nice    40
vrees       -       memlock unlimited
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

# How to install docker images on Raspberry Pi

## Links

Video Andreas Spiess: https://www.youtube.com/watch?v=a6mjt8tWUws

**IOTstack** - Software and instructions: https://github.com/gcgarner/IOTstack

### ~/IOTstack/services/mariadb/mariadb.env

```
TZ=Europe/Berlin
PUID=1000
PGID=1000
MYSQL_ROOT_PASSWORD=goForGold
MYSQL_DATABASE=heatpump
MYSQL_USER=vrees
MYSQL_PASSWORD=goForGold
```

Used cooler for RPi 4: http://bit.ly/2V4DYyd

**Wiki** https://github.com/gcgarner/IOTstack/wiki

## Build and deploy to Dockerhub

##### Prerequirments - ~/.m2/settings.xml

```
<settings>
  <servers>
    <server>
      <id>registry-1.docker.io</id>
      <username>vrees</username>
      <password>.........</password>
    </server>
  </servers>
</settings>

```

```
mvn package -Pprod verify jib:build
```

##### on Raspi

```
apt install gnupg2 pass
```

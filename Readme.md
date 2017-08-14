# AstroWebService
The AstroWebService is a simple webservice allowing consumers to get planets and cusps positions. The AstroAPI uses Swiss Ephemeris.

**Planets**:

Sun, Moon, Mercury, Venus, Mars, Jupiter, Saturn, Uranus, Neptune, Pluto, Chiron, Lilith, NNode.

**House systems**:

Placidus, Koch, Porphyrius, Regiomontanus, Campanus, Equal, Vehlow Equal, Whole, Axial Rotation, Horizontal, Polich/Page, Alcabitius, Gauquelin sectors, Morinus.

**Coordinate systems**

Geocentric, Topocentric.

**Zodiac type**

Tropical, Sidereal

### Version: 1.0.0

## Documentation
- [AstrologyAPI](http://docs.astrologyapi.apiary.io)

### Prerequisites
- Java 8
- Maven 3	
- [astrologyAPI](https://github.com/Kibo/AstroAPI) in file:///${project.basedir}/localMavenRepo

To see the application in action, run the cz.kibo.astrology.service.Bootstrap program using your IDE.

### Install
**jar**
1. mvn clean package -Pjar
2. java -jar target/webservice.jar
(The application will start the embedded Jetty server at http://localhost:8080)

**war**
1. mvn clean package -Pwar
2. Deploy to your Java container (Jetty, Tomcat, GlassFish, ...)

**openshift**
1. mvn clean package -Popenshift
2. Deploy to the OpenShift_v3 WildFly container

## License
GNU public version 3
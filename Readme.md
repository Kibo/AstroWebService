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

To see the application in action, run the cz.kibo.astrology.service.Bootstrap program using your IDE.

### Install
- mvn clean package
- mvn package

**jar**
- java -jar target/AstrologyWebService.jar
(The application will start the embedded Jetty server at http://0.0.0.0:8080)

**war**
- Deploy to your Java container (Jetty, Tomcat, GlassFish, ...)

## License
GNU public version 3
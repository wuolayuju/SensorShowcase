package uam.eps.es.sensorshowcase;

/**
 * Created by Ari on 15/05/2016.
 */
public class SensorPOJO {

    public String name;
    public String vendor;
    public int type;
    public int version;

    public SensorPOJO(String name, String vendor, int type, int version) {
        this.name = name;
        this.vendor = vendor;
        this.type = type;
        this.version = version;
    }
}

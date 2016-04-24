
package dynamitechetan.tothemoon.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Moon {

    @SerializedName("azimuth")
    @Expose
    private Double azimuth;
    @SerializedName("altitude")
    @Expose
    private Double altitude;
    @SerializedName("distance")
    @Expose
    private Double distance;

    /**
     *
     * @return
     * The azimuth
     */
    public Double getAzimuth() {
        return azimuth;
    }

    /**
     *
     * @param azimuth
     * The azimuth
     */
    public void setAzimuth(Double azimuth) {
        this.azimuth = azimuth;
    }

    /**
     *
     * @return
     * The altitude
     */
    public Double getAltitude() {
        return altitude;
    }

    /**
     *
     * @param altitude
     * The altitude
     */
    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    /**
     *
     * @return
     * The distance
     */
    public Double getDistance() {
        return distance;
    }

    /**
     *
     * @param distance
     * The distance
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
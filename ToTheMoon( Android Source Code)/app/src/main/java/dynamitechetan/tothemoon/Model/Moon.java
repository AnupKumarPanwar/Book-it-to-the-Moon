
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
    @SerializedName("age")
    @Expose
    private Double age;
    @SerializedName("illumination")
    @Expose
    private Double illumination;
    @SerializedName("stage")
    @Expose
    private String stage;
    @SerializedName("dfs")
    @Expose
    private Double dfs;
    @SerializedName("fm")
    @Expose
    private String fm;
    @SerializedName("nm")
    @Expose
    private String nm;

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

    /**
     *
     * @return
     * The age
     */
    public Double getAge() {
        return age;
    }

    /**
     *
     * @param age
     * The age
     */
    public void setAge(Double age) {
        this.age = age;
    }

    /**
     *
     * @return
     * The illumination
     */
    public Double getIllumination() {
        return illumination;
    }

    /**
     *
     * @param illumination
     * The illumination
     */
    public void setIllumination(Double illumination) {
        this.illumination = illumination;
    }

    /**
     *
     * @return
     * The stage
     */
    public String getStage() {
        return stage;
    }

    /**
     *
     * @param stage
     * The stage
     */
    public void setStage(String stage) {
        this.stage = stage;
    }

    /**
     *
     * @return
     * The dfs
     */
    public Double getDfs() {
        return dfs;
    }

    /**
     *
     * @param dfs
     * The dfs
     */
    public void setDfs(Double dfs) {
        this.dfs = dfs;
    }

    /**
     *
     * @return
     * The fm
     */
    public String getFm() {
        return fm;
    }

    /**
     *
     * @param fm
     * The fm
     */
    public void setFm(String fm) {
        this.fm = fm;
    }

    /**
     *
     * @return
     * The nm
     */
    public String getNm() {
        return nm;
    }

    /**
     *
     * @param nm
     * The nm
     */
    public void setNm(String nm) {
        this.nm = nm;
    }

}

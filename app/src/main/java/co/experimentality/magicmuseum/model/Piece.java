package co.experimentality.magicmuseum.model;

/**
 * Created by juanjo on 11/9/16.
 */

public class Piece {
    private String name;
    private String temperature;
    private String ligth;
    private String id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getLigth() {
        return ligth;
    }

    public void setLigth(String ligth) {
        this.ligth = ligth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

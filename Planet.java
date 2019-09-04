public enum Planet {
    MERCURY("Mercure", 0.387), VENUS("Venus", 0.722),
    EARTH("Terre", 1.0), MARS("Mars", 1.52),
    JUPITER("Jupiter", 5.2), SATURN("Saturne", 9.58),
    URANUS("Uranus", 19.2), NEPTUNE("Neptune", 30.1);

    private String commonName;
    private double distanceFromTheSun;

    public static final double UA_IN_KM = 149597871.0;
    public static final double LIGHT_SPEED_IN_KM_PER_S = 299792.458;

    Planet(final String commonName, final double distanceFromTheSun) {
        this.commonName = commonName;
        this.distanceFromTheSun = distanceFromTheSun;
    }

    public double getDistanceFromTheSun() {
        return this.distanceFromTheSun;
    }

    public String getCommonName() {
        return this.commonName;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", this.commonName, this.distanceFromTheSun);
    }

    public double distanceInUATo(Planet otherPlanet) {
        return Math.abs(this.distanceFromTheSun - otherPlanet.distanceFromTheSun);
    }

    public double distanceInKMTo(Planet otherPlanet) {
        return this.distanceInUATo(otherPlanet) * UA_IN_KM;
    }

    public double travelTimeInSTo(Planet otherPlanet) {
        return this.distanceInKMTo(otherPlanet) / LIGHT_SPEED_IN_KM_PER_S;
    }

    public double travelTimeInSTo(Planet otherPlanet, double speedInKmPerS) {
        return this.distanceInKMTo(otherPlanet) / speedInKmPerS;
    }
}


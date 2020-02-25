package tech.lovelycheng.learning.javalang.hAndE;

import java.util.Objects;

/**
 * @author chengtong
 * @date 2020/2/23 13:16
 */
public class IdentityCard {

    private int version;

    private int areaCode;

    private int subAreaCode;

    private int birthDate;

    private int lastFour;

    public IdentityCard(int version, int areaCode, int subAreaCode, int birthDate, int lastFour) {
        this.version = version;
        this.areaCode = areaCode;
        this.subAreaCode = subAreaCode;
        this.birthDate = birthDate;
        this.lastFour = lastFour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdentityCard)) return false;
        IdentityCard that = (IdentityCard) o;
        return version == that.version &&
                areaCode == that.areaCode &&
                subAreaCode == that.subAreaCode &&
                birthDate == that.birthDate &&
                lastFour == that.lastFour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, areaCode, subAreaCode, birthDate, lastFour);
    }

}

package by.horsego.bean;

public class BetType extends Entity{

    private TypeEnum type;
    private double coefficient;


    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BetType betType = (BetType) o;

        if (Double.compare(betType.coefficient, coefficient) != 0) return false;
        return type == betType.type;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = type != null ? type.hashCode() : 0;
        temp = Double.doubleToLongBits(coefficient);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "BetType{" +
                "type=" + type +
                ", coefficient=" + coefficient +
                '}';
    }

    public static enum TypeEnum{

        VICTORY("victory"),
        FIRST_THREE("first_three"),
        OUTSIDER("outsider"),
        LAST_THREE("last_three");

        private String localName;

        public String getLocalName() {
            return localName;
        }

        TypeEnum(String localName) {
            this.localName = localName;
        }
    }
}

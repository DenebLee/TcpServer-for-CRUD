package kr.nanoit.model.message;

public enum MessageType {

    SMS("SMS"),

    NONE("NONE"),

    LOGIN("LOGIN");

    private final String property;

    MessageType(String property) {
        this.property = property;
    }

    public static MessageType fromPropertyName(String x) {
        for (MessageType currentType : MessageType.values()) {
            if (x.equals(currentType.getProperty())) {
                return currentType;
            }
        }
        return NONE;
    }

    public String getProperty() {
        return property;
    }


    @Override
    public String toString() {
        return property;
    }
}

package utils.utils;

public class MoneyUtils {
    private MoneyUtils() {
    }

    public static Float convertToDBPrecision(Float money) {
        long l = (long) (money * 1000);
        return (float) l;
    }

    public static Float convertFromDbPrecision(Float money) {
        return money / 1000;
    }
}

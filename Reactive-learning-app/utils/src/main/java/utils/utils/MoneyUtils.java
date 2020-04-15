package utils.utils;

public class MoneyUtils {
    private MoneyUtils() {
    }

    public static Double convertToDBPrecision(Double money) {
        long l = (long) (money * 1000);
        return (double) l;
    }

    public static Double convertFromDbPrecision(Double money) {
        return (long) (money / 10) / 100D;
    }

    public static Double convertMoney(Double money, Double exchangeRate) {
        return (long) (money * exchangeRate * 100) / 100d;
    }
}

package order;

import com.github.javafaker.Faker;

import java.util.List;

public class OrderGenerator {
    private static Faker faker = new Faker();

    public static String setFirstNameOrder() {
        return faker.address().firstName();
    }

    public static String setLastNameOrder() {
        return faker.address().lastName();
    }

    public static String setAddressOrder() {
        return faker.address().secondaryAddress();
    }

    public static String setMetroStationOrder() {
        return faker.address().streetAddress();
    }

    public static int setRentalTimeOrder() {
        return faker.number().randomDigit();
    }

    public static String setDeliveryDateOrder() {
        return faker.bothify("202#-10-1#");
    }

    public static String setCommentOrder() {
        return faker.letterify("??????????? ???????? ??????");
    }

    public static String setPhoneOrder() {
        return faker.bothify("##########");
    }

    public static Order colourBlackAndGray() {
        return new Order(setFirstNameOrder(), setLastNameOrder(), setAddressOrder(), setMetroStationOrder(), setPhoneOrder(), setRentalTimeOrder(), setDeliveryDateOrder(), "Saske, come back to Konoha", List.of("BLACK", "GRAY"));
    }

    public static Order colourBlack() {
        return new Order(setFirstNameOrder(), setLastNameOrder(), setAddressOrder(), setMetroStationOrder(), setPhoneOrder(), setRentalTimeOrder(), setDeliveryDateOrder(), setCommentOrder(), List.of("BLACK"));
    }

    public static Order colourGray() {
        return new Order(setFirstNameOrder(), setLastNameOrder(), setAddressOrder(), setMetroStationOrder(), setPhoneOrder(), setRentalTimeOrder(), setDeliveryDateOrder(), setCommentOrder(), List.of("GRAY"));
    }

    public static Order colourNull() {
        return new Order(setFirstNameOrder(), setLastNameOrder(), setAddressOrder(), setMetroStationOrder(), setPhoneOrder(), setRentalTimeOrder(), setDeliveryDateOrder(), setCommentOrder(), null);
    }

    public static Order colourEmpty() {
        return new Order(setFirstNameOrder(), setLastNameOrder(), setAddressOrder(), setMetroStationOrder(), setPhoneOrder(), setRentalTimeOrder(), setDeliveryDateOrder(), setCommentOrder(), List.of(""));
    }
}
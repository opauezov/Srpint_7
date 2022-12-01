package courier;

import com.github.javafaker.Faker;

public class CourierGenerator {

    private static final Faker faker = new Faker();

    public static String setLoginCourierGenerator() {
        return faker.bothify("?#?#?#?#?#");
    }

    public static String setPasswordGenerator() {
        return faker.bothify("?#?#?#?#??#?#");
    }

    public static String setFirstName() {
        return faker.address().firstName();
    }

    public static Courier getDefault() {
        return new Courier(setLoginCourierGenerator(), setPasswordGenerator(), setFirstName());
    }

    public static Courier getInUppercase() {
        return new Courier(setLoginCourierGenerator().toUpperCase(), setPasswordGenerator().toUpperCase(), setFirstName().toUpperCase());
    }

    public static Courier getWithoutLogin() {
        return new Courier(null, setPasswordGenerator(), null);
    }

    public static Courier getWithoutPassword() {
        return new Courier(setLoginCourierGenerator(), null, null);
    }

    public static Courier getEmptyPassword() {
        return new Courier(setLoginCourierGenerator(), "", null);
    }

    public static Courier getEmptyLogin() {
        return new Courier("", setPasswordGenerator(), null);
    }

    public static Courier getEmptyLoginAndPassword() {
        return new Courier("", "", null);
    }

    public static Courier authorization() {
        return new Courier(setLoginCourierGenerator(), setPasswordGenerator(), null);
    }

    public static Courier authorizationWithInvalidPassword() {
        return new Courier(setLoginCourierGenerator(), setPasswordGenerator() + 1, null);
    }
}


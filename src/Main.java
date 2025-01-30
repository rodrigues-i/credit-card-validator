import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String cardNumber = "869994893864771"; // Replace with actual card number
        Map<String, Object> result = validateCreditCardNumber(cardNumber);
        System.out.println(result);
    }

    public static Map<String, Object> validateCreditCardNumber(String cardNumber) {
        Map<String, Pattern> cardTypes = new HashMap<>();
        cardTypes.put("visa", Pattern.compile("^4[0-9]{12}(?:[0-9]{3})?$"));
        cardTypes.put("mastercard", Pattern.compile("^5[1-5][0-9]{14}$"));
        cardTypes.put("amex", Pattern.compile("^3[47][0-9]{13}$"));
        cardTypes.put("discover", Pattern.compile("^6(?:011|5[0-9]{2})[0-9]{12}$"));
        cardTypes.put("diners", Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9]{11}$"));
        cardTypes.put("jcb", Pattern.compile("^(?:2131|1800|35\\d{3})\\d{11}$"));
        cardTypes.put("enroute", Pattern.compile("^(2014|2149)\\d{11}$"));
        cardTypes.put("voyager", Pattern.compile("^8699[0-9]{11}$"));
        cardTypes.put("hipercard", Pattern.compile("^(606282|3841)[0-9]{10,12}$"));
        cardTypes.put("aura", Pattern.compile("^50[0-9]{14,17}$"));

        String cardType = null;
        for (Map.Entry<String, Pattern> entry : cardTypes.entrySet()) {
            if (entry.getValue().matcher(cardNumber).matches()) {
                cardType = entry.getKey();
                break;
            }
        }

        Map<String, Object> result = new HashMap<>();
        if (cardType == null) {
            result.put("valid", false);
            result.put("message", "Invalid card type");
            return result;
        }

        if (!luhnCheck(cardNumber)) {
            result.put("valid", false);
            result.put("message", "Invalid card number");
            return result;
        }

        result.put("valid", true);
        result.put("cardType", cardType);
        return result;
    }

    public static boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean shouldDouble = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));

            if (shouldDouble) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
            shouldDouble = !shouldDouble;
        }

        return sum % 10 == 0;
    }
}
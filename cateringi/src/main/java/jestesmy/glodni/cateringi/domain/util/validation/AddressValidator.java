package jestesmy.glodni.cateringi.domain.util.validation;

import jestesmy.glodni.cateringi.domain.model.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AddressValidator {

    private static String validateStreet(String street){
        if(!Pattern.matches("[A-Za-ząęćśźżłóń ]+\\d+[ ]?[A-Za-z]?", street)){
            return "Podaj poprawny adres";
        }
        return "";
    }

    private static String validatePostalCode(String postalCode){
        if(!Pattern.matches("\\d{2}-\\d{3}", postalCode)){
            return "Kod pocztowy musi być formatu dd-ddd";
        }
        return "";
    }

    public static List<String> validate(Address address){
        List<String> validationErrors = new ArrayList<>();
        String error = validateStreet(address.getStreet());
        if(!error.isEmpty()) validationErrors.add(error);
        error = validatePostalCode(address.getPostalCode());
        if(!error.isEmpty()) validationErrors.add(error);
        return validationErrors;
    }

}

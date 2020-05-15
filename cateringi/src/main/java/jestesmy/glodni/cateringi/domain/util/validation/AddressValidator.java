package jestesmy.glodni.cateringi.domain.util.validation;

import jestesmy.glodni.cateringi.domain.model.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AddressValidator {

    private static String validateCity(String city){
        if(!Pattern.matches("[A-ZŚŻĆŹŁŃa-ząęćśźżłóń \\-]+", city)){
            return "Podaj istniejące misato";
        } else if(city == null || city == ""){
            return "Miasto jest wymagane";
        }
        return "";
    }

    private static String validateStreet(String street){
        if(!Pattern.matches("[A-ZŚŻĆŹŁŃa-ząęćśźżłóń \\-]+\\d+[ ]?[A-Za-z]?", street)){
            return "Podaj poprawny adres";
        } else if(street == null){
            return "Ulica jest wymagana";
        }
        return "";
    }

    private static String validatePostalCode(String postalCode){
        if(!Pattern.matches("\\d{2}-\\d{3}", postalCode)){
            return "Kod pocztowy musi być formatu dd-ddd";
        } else if (postalCode == null){
            return "Kod pocztowy jest wymagany";
        }
        return "";
    }

    private static String validateApartmentNumber(String apartmentNumber){
        if(!Pattern.matches("\\d*", apartmentNumber)){
            return "Numer lokalu musi być liczbą";
        } else if (apartmentNumber == null || apartmentNumber.trim() == ""){
            return "";
        }
        return "";
    }

    public static List<String> validate(Address address){
        List<String> validationErrors = new ArrayList<>();
        String error = validateCity(address.getCity());
        if(!error.isEmpty()) validationErrors.add(error);
        error = validateStreet(address.getStreet());
        if(!error.isEmpty()) validationErrors.add(error);
        error = validatePostalCode(address.getPostalCode());
        if(!error.isEmpty()) validationErrors.add(error);
        error = validateApartmentNumber(address.getApartmentNumber());
        if(!error.isEmpty()) validationErrors.add(error);
        return validationErrors;
    }

}

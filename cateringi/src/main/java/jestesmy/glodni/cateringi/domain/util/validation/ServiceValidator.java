package jestesmy.glodni.cateringi.domain.util.validation;

import jestesmy.glodni.cateringi.domain.model.Service;
import jestesmy.glodni.cateringi.domain.model.ServiceVariant;

import java.util.ArrayList;
import java.util.List;

public class ServiceValidator {

    private static String validatePrice(double price) {
        if(price < 0)
            return "Cena/dzień nie może być liczbą ujemną";
        return "";
    }

    private static String validateDays(int days) {
        if(days < 1)
            return "Ilość dni musi być większa od 0";
        return "";
    }

    private static String validateCalories(int calories) {
        if(calories < 1)
            return "Ilośc kalorii musi być większa od 0";
        return "";
    }

    public static List<String> validate(ServiceVariant service) {
        List<String> validationErrors = new ArrayList<>();
        String error = validatePrice(service.getPrice());
        if(!error.isEmpty()) validationErrors.add(error);
        error = validateCalories(service.getCalories());
        if(!error.isEmpty()) validationErrors.add(error);
        error = validateDays(service.getDayNumber());
        if(!error.isEmpty()) validationErrors.add(error);
        return validationErrors;
    }

}

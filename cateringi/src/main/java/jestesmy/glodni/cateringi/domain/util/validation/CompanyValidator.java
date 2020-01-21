package jestesmy.glodni.cateringi.domain.util.validation;

import jestesmy.glodni.cateringi.domain.model.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyValidator {

    private static String validateRegon(String regon) {
        int[] weights;
        if(regon.matches("\\d*")) {
            if (regon.length() == 9) {
                weights = new int[] {8, 9, 2, 3, 4, 5, 6, 7};
            } else if (regon.length() == 14) {
                weights = new int[] {2, 4, 8, 5, 0, 9, 7, 3, 6, 1, 2, 4, 8 };
            } else
                return "REGON powinien mieć 9 lub 14 cyfr";
        } else
            return "REGON powinien składać się z samych cyfr";
        int checkSum=0;
        for(int i=0;i<weights.length;i++) {
            checkSum+= weights[i]*Integer.parseInt(String.valueOf(regon.charAt(i)));
        }
        checkSum %= 11;
        String lastDigit = String.valueOf(regon.charAt(regon.length()-1));
        if(checkSum==10) {
            if(lastDigit.equals("0"))
                return "";
            else {
                return "REGON jest niepoprawny";
            }
        } else {
            if(Integer.parseInt(lastDigit)==checkSum)
                return "";
            else
                return "REGON jest niepoprawny";
        }
    }

    private static String validateNip(String nip) {
        int[] weights = {6, 5, 7, 2, 3, 4, 5, 6, 7};
        if(nip.matches("\\d*")){
            if(nip.length()!=10)
                return "NIP powinien mieć 10 cyfr";
        } else
            return "NIP powinien składać się z samych cyfr";
        int checkSum = 0;
        for(int i=0;i<weights.length;i++) {
            checkSum += weights[i]* Integer.parseInt(String.valueOf(nip.charAt(i)));
        }
        checkSum %= 11;
        int lastDigit = Integer.parseInt(String.valueOf(nip.charAt(9)));
        if(lastDigit!=checkSum)
            return "NIP niepoprawny";
        return "";
    }

    public static List<String> validate(Company company) {
        List<String> validationErrors = new ArrayList<>();
        String error = validateRegon(company.getRegon());
        if(!error.isEmpty())
            validationErrors.add(error);
        error = validateNip(company.getNip());
        if(!error.isEmpty())
            validationErrors.add(error);
        return validationErrors;
    }

    public static void main (String[] args) {

    }

}

package jestesmy.glodni.cateringi.domain.util.validation;

import jestesmy.glodni.cateringi.domain.model.User;
import jestesmy.glodni.cateringi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserValidator {

    private final UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private String validateLogin(String login) {
        if(userRepository.findByUserName(login)!=null){
            return "Użytkownik o takim loginie istnieje";
        }
        return "";
    }

    public List<String> validate(User user) {
        List<String> errors = new ArrayList<>();
        String error = validateLogin(user.getUserName());
        if(!error.isEmpty())
            errors.add(error);
        return errors;
    }

}

package jestesmy.glodni.cateringi.service;

import jestesmy.glodni.cateringi.model.Account;

public interface AuthenticationService {
    /**
     *
     * @return
     */
    String getCurrentUsername();

    /**
     *
     * @return
     */
    Account getCurrentUser();
}

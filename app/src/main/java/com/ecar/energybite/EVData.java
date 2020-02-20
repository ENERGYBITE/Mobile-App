package com.ecar.energybite;

import com.ecar.energybite.activity.BaseActivity;
import com.ecar.energybite.user.User;

/**
 * Created by navin.ketu on 04-09-2019.
 */

public class EVData {

    public static User user;
    private static BaseActivity sCurrentBaseActivity  = null;

    public static void setLoggedInUser(User user) {
        EVData.user = user;
    }

    public static User getUser() {
        return EVData.user;
    }

}

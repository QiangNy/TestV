/*
 * Copyright (c) 2012-2014 Arne Schwabe
 * Distributed under the GNU GPL v2 with additional terms. For full terms see the file doc/LICENSE.txt
 */

package ludeng.com.testvi.vpnspl.core;
import android.app.Application;

import com.example.opentest.BuildConfig;


public class ICSOpenVPNApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PRNGFixes.apply();

        if (BuildConfig.DEBUG) {
            //ACRA.init(this);
        }
    }
}

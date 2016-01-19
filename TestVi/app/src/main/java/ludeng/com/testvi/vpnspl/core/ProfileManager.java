/*
 * Copyright (c) 2012-2014 Arne Schwabe
 * Distributed under the GNU GPL v2 with additional terms. For full terms see the file doc/LICENSE.txt
 */

package ludeng.com.testvi.vpnspl.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.HashMap;

import ludeng.com.testvi.vpnspl.VpnProfile;

public class ProfileManager {


    private static final String LAST_CONNECTED_PROFILE = "lastConnectedProfile";
    private static ProfileManager instance;

    private static VpnProfile mLastConnectedVpn = null;
    private HashMap<String, VpnProfile> profiles = new HashMap<>();
    private static VpnProfile tmpprofile = null;


    private static VpnProfile get(String key) {
        if (tmpprofile != null && tmpprofile.getUUIDString().equals(key))
            return tmpprofile;

        if (instance == null)
            return null;
        return instance.profiles.get(key);

    }


    private ProfileManager() {
    }

    private static void checkInstance(Context context) {
        if (instance == null) {
            instance = new ProfileManager();
          //  instance.loadVPNList(context);
        }
    }

    synchronized public static ProfileManager getInstance(Context context) {
        checkInstance(context);
        return instance;
    }

    public static void setConntectedVpnProfileDisconnected(Context c) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        Editor prefsedit = prefs.edit();
        prefsedit.putString(LAST_CONNECTED_PROFILE, null);
        prefsedit.apply();

    }

    public static void setConnectedVpnProfile(Context c, VpnProfile connectedrofile) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        Editor prefsedit = prefs.edit();

        prefsedit.putString(LAST_CONNECTED_PROFILE, connectedrofile.getUUIDString());
        prefsedit.apply();
        mLastConnectedVpn = connectedrofile;

    }

    public static VpnProfile getLastConnectedProfile(Context c, boolean onBoot) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);

        boolean useStartOnBoot = prefs.getBoolean("restartvpnonboot", false);

        if (onBoot && !useStartOnBoot)
            return null;

        String lastConnectedProfile = prefs.getString(LAST_CONNECTED_PROFILE, null);
        if (lastConnectedProfile != null)
            return get(c, lastConnectedProfile);
        else
            return null;
    }

    public void addProfile(VpnProfile profile) {
        profiles.put(profile.getUUID().toString(), profile);

    }

    public static VpnProfile get(Context context, String profileUUID) {
        checkInstance(context);
        return get(profileUUID);
    }

    public static VpnProfile getLastConnectedVpn() {
        return mLastConnectedVpn;
    }

}

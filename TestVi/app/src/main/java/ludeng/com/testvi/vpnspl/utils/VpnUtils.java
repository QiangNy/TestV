/*
 * Copyright (c) 2012-2015 Arne Schwabe
 * Distributed under the GNU GPL v2 with additional terms. For full terms see the file doc/LICENSE.txt
 */

package ludeng.com.testvi.vpnspl.utils;

import android.content.Context;
import android.util.Log;

import ludeng.com.testvi.vpnspl.VpnProfile;
import ludeng.com.testvi.vpnspl.core.NativeUtils;

import java.io.IOException;

/**
 * Created by chen on 2016/1/12.
 */
public class VpnUtils {

    public static String getFileData(Context c, int num)throws IOException, SecurityException {

        StringBuffer str = new StringBuffer();
        str.append(NativeUtils.getICon(num).toString());
        if (str.length() < 1) {
            Log.i("ICABVIEW","native get no data "+num);
        }


        return VpnProfile.DISPLAYNAME_TAG+num+VpnProfile.INLINE_TAG + str;
    }


}

package com.vito.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by zlb on 1/8/2018.
 */

public class AutoUnInstall {
    public static void UnInstallPackatge(Context context,String packageName)
    {
        Uri packageURI=Uri.parse("package:"+packageName);
        Intent uninstallIntent=new Intent(Intent.ACTION_DELETE,packageURI);
        context.startActivity(uninstallIntent);
    }
}

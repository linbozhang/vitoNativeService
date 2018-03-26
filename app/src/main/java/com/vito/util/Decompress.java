package com.vito.util;

/**
 * Created by zlb on 2/5/2018.
 */

import android.content.Context;
import android.util.Log;

import com.vito.websocketclient.LogicClient;
import com.vito.websocketclient.LogicClientKey;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author dhaval nagar
 */
public class Decompress {
    private File _zipFile;
    private InputStream _zipFileStream;
    private Context context;
    private static final String ROOT_LOCATION = "/sdcard";
    private static final String TAG = "UNZIPUTIL";

    public Decompress(Context context, File zipFile) {
        _zipFile = zipFile;
        this.context = context;

        _dirChecker("");
    }

    public Decompress(Context context, InputStream zipFile) {
        _zipFileStream = zipFile;
        this.context = context;

        _dirChecker("");
    }

    public void unzip(final String targetPath , final LogicClient logicClient) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    String realTargetpath=targetPath;
                    if(realTargetpath.endsWith("/")){
                       realTargetpath= realTargetpath.substring(0,realTargetpath.lastIndexOf("/"));
                    }
                    Log.i(TAG, "Starting to unzip");
                    InputStream fin = _zipFileStream;
                    if(fin == null) {
                        fin = new FileInputStream(_zipFile);
                    }
                    ZipInputStream zin = new ZipInputStream(fin);
                    ZipEntry ze = null;
                    while ((ze = zin.getNextEntry()) != null) {
                        Log.v(TAG, "Unzipping " + ze.getName());

                        if(ze.isDirectory()) {
                            _dirChecker(realTargetpath + "/" + ze.getName());
                        } else {
                            FileOutputStream fout = new FileOutputStream(new File(realTargetpath, ze.getName()));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            byte[] buffer = new byte[1024];
                            int count;

                            // reading and writing
                            while((count = zin.read(buffer)) != -1)
                            {
                                baos.write(buffer, 0, count);
                                byte[] bytes = baos.toByteArray();
                                fout.write(bytes);
                                baos.reset();
                            }

                            fout.close();
                            zin.closeEntry();
                        }

                    }
                    zin.close();
                    logicClient.SendMessage(LogicClientKey.MsgType_UnZipResourcesOver,"true",false);
                    Log.i(TAG, "Finished unzip");
                } catch(Exception e) {
                    logicClient.SendMessage(LogicClientKey.MsgType_UnZipResourcesOver,"false",false);
                    Log.e(TAG, "Unzip Error", e);
                }
            }
        }).start();

    }

    private void _dirChecker(String dir) {
        File f = new File(dir);
        Log.i(TAG, "creating dir " + dir);

        if(dir.length() >= 0 && !f.isDirectory() ) {
            f.mkdirs();
        }
    }
}

package com.huajiao.webview.bridge;

public class Utils {
//
//    private void loadDefaultJSCall() {
//        final File optimizedDexOutputPath = new File(Environment.getExternalStorageDirectory().toString()
//                + File.separator + "defaultjs-dex.jar");
//        Log.e("zjy","js path = "+optimizedDexOutputPath.getAbsolutePath());
//        // Initialize the class loader with the secondary dex file.
////                DexClassLoader cl = new DexClassLoader(dexInternalStoragePath.getAbsolutePath(),
////                        optimizedDexOutputPath.getAbsolutePath(),
////                        null,
////                        getClassLoader());
//        DexClassLoader cl = new DexClassLoader(optimizedDexOutputPath.getAbsolutePath(),
//                Environment.getExternalStorageDirectory().toString(), null, getClassLoader());
//        Class libProviderClazz = null;
//
//        try {
//            // Load the library class from the class loader.
//            // 载入从网络上下载的类
////                    libProviderClazz = cl.loadClass("com.example.dex.lib.LibraryProvider");
//            libProviderClazz = cl.loadClass("com.huajiao.h5plugin.bridge.DefaultJSCall");
//
//            // Cast the return object to the library interface so that the
//            // caller can directly invoke methods in the interface.
//            // Alternatively, the caller can invoke methods through reflection,
//            // which is more verbose and slow.
//            //LibraryInterface lib = (LibraryInterface) libProviderClazz.newInstance();
//            PreloadJSCall lib = (PreloadJSCall)libProviderClazz.newInstance();
//            mBridge.setDefaultJSCall(lib);
//            // Display the toast!
//            //lib.showAwesomeToast(view.getContext(), "hello 世界!");
//        } catch (Exception exception) {
//            // Handle exception gracefully here.
//            exception.printStackTrace();
//        }
//    }
}

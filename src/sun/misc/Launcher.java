// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Launcher.java

package sun.misc;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.security.*;
import java.util.*;
import sun.jkernel.DownloadManager;
import sun.net.www.ParseUtil;
import sun.security.action.GetPropertyAction;

// Referenced classes of package sun.misc:
//            URLClassPath, PathPermissions, MetaIndex

public class Launcher
{
    static class AppClassLoader extends URLClassLoader
    {

    	static class MyPrivilegedAction implements PrivilegedAction {
    		String s;
    		File afile[];
    		ClassLoader classloader;
    		public MyPrivilegedAction(String s, File afile[], ClassLoader classloader) {
    			this.s = s;
    			this.afile = afile;
    			this.classloader = classloader;    		
			}
    		
            public Object run()
            {
                URL aurl[] = s != null ? Launcher.pathToURLs(afile) : new URL[0];
                return new AppClassLoader(aurl, classloader);
            }
    	};
    	
        public static ClassLoader getAppClassLoader(ClassLoader classloader)
            throws IOException
        {
            String s = System.getProperty("java.class.path");
            File afile[] = s != null ? Launcher.getClassPath(s) : new File[0];
            return (AppClassLoader)AccessController.doPrivileged(new MyPrivilegedAction(s, afile, classloader));
        }

        public synchronized Class loadClass(String s, boolean flag)
            throws ClassNotFoundException
        {
            DownloadManager.getBootClassPathEntryForClass(s);
            int i = s.lastIndexOf('.');
            if(i != -1)
            {
                SecurityManager securitymanager = System.getSecurityManager();
                if(securitymanager != null)
                    securitymanager.checkPackageAccess(s.substring(0, i));
            }
            return super.loadClass(s, flag);
        }

        protected PermissionCollection getPermissions(CodeSource codesource)
        {
            PermissionCollection permissioncollection = super.getPermissions(codesource);
            permissioncollection.add(new RuntimePermission("exitVM"));
            return permissioncollection;
        }

        private void appendToClassPathForInstrumentation(String s)
        {
            if(!$assertionsDisabled && !Thread.holdsLock(this))
            {
                throw new AssertionError();
            } else
            {
                super.addURL(Launcher.getFileURL(new File(s)));
                return;
            }
        }

        private static AccessControlContext getContext(File afile[])
            throws MalformedURLException
        {
            PathPermissions pathpermissions = new PathPermissions(afile);
            ProtectionDomain protectiondomain = new ProtectionDomain(new CodeSource(pathpermissions.getCodeBase(), (java.security.cert.Certificate[])null), pathpermissions);
            AccessControlContext accesscontrolcontext = new AccessControlContext(new ProtectionDomain[] {
                protectiondomain
            });
            return accesscontrolcontext;
        }

        void addAppURL(URL url)
        {
            super.addURL(url);
        }

        //static final boolean $assertionsDisabled = !sun.misc.Launcher.desiredAssertionStatus();
        static final boolean $assertionsDisabled = true;


        AppClassLoader(URL aurl[], ClassLoader classloader)
        {
            super(aurl, classloader, Launcher.factory);
        }
    }

    static class ExtClassLoader extends URLClassLoader
    {
    	
    	static class MyPrivilegedAction2 implements PrivilegedAction {
    		File afile[];
    		public MyPrivilegedAction2(File afile[]) {
    			this.afile = afile;	
			}
    		
            public Object run()
            {
                int i = afile.length;
                for(int j = 0; j < i; j++)
                    MetaIndex.registerDirectory(afile[j]);

                try {
					return new ExtClassLoader(afile);
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
            }
    	};

        public static ExtClassLoader getExtClassLoader()
            throws IOException
        {
            File afile[] = getExtDirs();
            return (ExtClassLoader)AccessController.doPrivileged(new MyPrivilegedAction2(afile));
//            PrivilegedActionException privilegedactionexception;
//            throw (IOException)privilegedactionexception.getException();
        }

        void addExtURL(URL url)
        {
            super.addURL(url);
        }

        private static File[] getExtDirs()
        {
            String s = System.getProperty("java.ext.dirs");
            File afile[];
            if(s != null)
            {
                StringTokenizer stringtokenizer = new StringTokenizer(s, File.pathSeparator);
                int i = stringtokenizer.countTokens();
                afile = new File[i];
                for(int j = 0; j < i; j++)
                    afile[j] = new File(stringtokenizer.nextToken());

            } else
            {
                afile = new File[0];
            }
            return afile;
        }

        private static URL[] getExtURLs(File afile[])
            throws IOException
        {
            Vector vector = new Vector();
            for(int i = 0; i < afile.length; i++)
            {
                String as[] = afile[i].list();
                if(as == null)
                    continue;
                for(int j = 0; j < as.length; j++)
                    if(!as[j].equals("meta-index"))
                    {
                        File file = new File(afile[i], as[j]);
                        vector.add(Launcher.getFileURL(file));
                    }

            }

            URL aurl[] = new URL[vector.size()];
            vector.copyInto(aurl);
            return aurl;
        }

        public String findLibrary(String s)
        {
            s = System.mapLibraryName(s);
            for(int i = 0; i < dirs.length; i++)
            {
                String s1 = System.getProperty("os.arch");
                if(s1 != null)
                {
                    File file = new File(new File(dirs[i], s1), s);
                    if(file.exists())
                        return file.getAbsolutePath();
                }
                File file1 = new File(dirs[i], s);
                if(file1.exists())
                    return file1.getAbsolutePath();
            }

            return null;
        }

        protected Class findClass(String s)
            throws ClassNotFoundException
        {
            DownloadManager.getBootClassPathEntryForClass(s);
            return super.findClass(s);
        }

        private static AccessControlContext getContext(File afile[])
            throws IOException
        {
            PathPermissions pathpermissions = new PathPermissions(afile);
            ProtectionDomain protectiondomain = new ProtectionDomain(new CodeSource(pathpermissions.getCodeBase(), (java.security.cert.Certificate[])null), pathpermissions);
            AccessControlContext accesscontrolcontext = new AccessControlContext(new ProtectionDomain[] {
                protectiondomain
            });
            return accesscontrolcontext;
        }

        private File dirs[];

        public ExtClassLoader(File afile[])
            throws IOException
        {
            super(getExtURLs(afile), null, Launcher.factory);
            dirs = afile;
        }
    }

    private static class Factory
        implements URLStreamHandlerFactory
    {

        public URLStreamHandler createURLStreamHandler(String s)
        {
            String s1 = (new StringBuilder()).append(PREFIX).append(".").append(s).append(".Handler").toString();
            try {
            	Class class1 = Class.forName(s1);
				return (URLStreamHandler)class1.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
//            Object obj;            
//            ((ClassNotFoundException) (obj)).printStackTrace();
//            break MISSING_BLOCK_LABEL_65;
//            ((InstantiationException) (obj)).printStackTrace();
//            break MISSING_BLOCK_LABEL_65;
//            ((IllegalAccessException) (obj)).printStackTrace();
            throw new InternalError((new StringBuilder()).append("could not load ").append(s).append("system protocol handler").toString());
        }

        private static String PREFIX = "sun.net.www.protocol";


        private Factory()
        {
        }

    }


    public static Launcher getLauncher()
    {
        return launcher;
    }

    public Launcher()
    {
        ExtClassLoader extclassloader;
        try
        {
            extclassloader = ExtClassLoader.getExtClassLoader();
        }
        catch(IOException ioexception)
        {
            throw new InternalError("Could not create extension class loader");
        }
        try
        {
            loader = AppClassLoader.getAppClassLoader(extclassloader);
        }
        catch(IOException ioexception1)
        {
            throw new InternalError("Could not create application class loader");
        }
        Thread.currentThread().setContextClassLoader(loader);
        String s = System.getProperty("java.security.manager");
        if(s != null)
        {
            SecurityManager securitymanager = null;
            if("".equals(s) || "default".equals(s))
                securitymanager = new SecurityManager();
            else
                try
                {
                    securitymanager = (SecurityManager)loader.loadClass(s).newInstance();
                }
                catch(IllegalAccessException illegalaccessexception) { }
                catch(InstantiationException instantiationexception) { }
                catch(ClassNotFoundException classnotfoundexception) { }
                catch(ClassCastException classcastexception) { }
            if(securitymanager != null)
                System.setSecurityManager(securitymanager);
            else
                throw new InternalError((new StringBuilder()).append("Could not create SecurityManager: ").append(s).toString());
        }
    }

    public ClassLoader getClassLoader()
    {
        return loader;
    }

    public static void addURLToAppClassLoader(URL url)
    {
        AccessController.checkPermission(new AllPermission());
        ClassLoader classloader = getLauncher().getClassLoader();
        ((AppClassLoader)classloader).addAppURL(url);
    }

    public static void addURLToExtClassLoader(URL url)
    {
        AccessController.checkPermission(new AllPermission());
        ClassLoader classloader = getLauncher().getClassLoader();
        ((ExtClassLoader)classloader.getParent()).addExtURL(url);
    }

    static class MyPrivilegedAction3 implements PrivilegedAction {
		String s1;
		public MyPrivilegedAction3(String s1) {
			this.s1 = s1;			    		
		}		
        public Object run()
        {
            File afile1[] = Launcher.getClassPath(s1);
            int i = afile1.length;
            HashSet hashset = new HashSet();
            for(int j = 0; j < i; j++)
            {
                File file = afile1[j];
                if(!file.isDirectory())
                    file = file.getParentFile();
                if(file != null && hashset.add(file))
                    MetaIndex.registerDirectory(file);
            }

            return Launcher.pathToURLs(afile1);
        }
	};
	
    static class MyPrivilegedAction4 implements PrivilegedAction {
    	File additionalBootStrapPaths[];
		public MyPrivilegedAction4(File[] afile) {
			this.additionalBootStrapPaths = afile;			    		
		}		
        public Object run()
        {
            for(int i = 0; i < additionalBootStrapPaths.length; i++)
                Launcher.bootstrapClassPath.addURL(Launcher.getFileURL(additionalBootStrapPaths[i]));
            return null;
        }
	};	
    
    public static synchronized URLClassPath getBootstrapClassPath()
    {
        if(bootstrapClassPath == null)
        {
            String s = (String)AccessController.doPrivileged(new GetPropertyAction("sun.boot.class.path"));
            URL aurl[] = null;
            if(s != null)
            {
                String s1 = s;
                aurl = (URL[])(URL[])AccessController.doPrivileged(new MyPrivilegedAction3(s1));
            }
            bootstrapClassPath = new URLClassPath(aurl, factory);
            File afile[] = DownloadManager.getAdditionalBootStrapPaths();
            AccessController.doPrivileged(new MyPrivilegedAction4(afile));            
        }
        return bootstrapClassPath;
    }

    public static synchronized void flushBootstrapClassPath()
    {
        bootstrapClassPath = null;
    }

    private static URL[] pathToURLs(File afile[])
    {
        URL aurl[] = new URL[afile.length];
        for(int i = 0; i < afile.length; i++)
            aurl[i] = getFileURL(afile[i]);

        return aurl;
    }

    private static File[] getClassPath(String s)
    {
        File afile[];
        if(s != null)
        {
            int i = 0;
            int j = 1;
            boolean flag = false;
            int k;
            for(int i1 = 0; (k = s.indexOf(File.pathSeparator, i1)) != -1; i1 = k + 1)
                j++;

            afile = new File[j];
            int l;
            int j1;
            for(j1 = l = 0; (l = s.indexOf(File.pathSeparator, j1)) != -1; j1 = l + 1)
                if(l - j1 > 0)
                    afile[i++] = new File(s.substring(j1, l));
                else
                    afile[i++] = new File(".");

            if(j1 < s.length())
                afile[i++] = new File(s.substring(j1));
            else
                afile[i++] = new File(".");
            if(i != j)
            {
                File afile1[] = new File[i];
                System.arraycopy(afile, 0, afile1, 0, i);
                afile = afile1;
            }
        } else
        {
            afile = new File[0];
        }
        return afile;
    }

    static URL getFileURL(File file)
    {
        try
        {
            file = file.getCanonicalFile();
        }
        catch(IOException ioexception) { }
        try {
			return ParseUtil.fileToEncodedURL(file);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //malformedurlexception;
        throw new InternalError();
    }

    private static URLStreamHandlerFactory factory = new Factory();
    private static Launcher launcher = new Launcher();
    private ClassLoader loader;
    private static URLClassPath bootstrapClassPath;
    private static URLStreamHandler fileHandler;





}

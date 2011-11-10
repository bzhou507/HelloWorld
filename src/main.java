import java.io.File;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Bobby Zhou
 * Date: 11/9/11
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static HashMap<String, String> undesiredExtensionMap = new HashMap<String, String>();
    
    public static void main(String[] args){

        grabFileExtensions("C:\\attask\\src\\trunk");

        StringBuilder sb = new StringBuilder();
        sb.append("svndumpfilter exclude --pattern ");

        Object[] keys = undesiredExtensionMap.keySet().toArray();
        for(Object o : keys){
            String extension = (String)o;
            if(!desiredStrings(extension))
                sb.append("*." + extension + " ");
        }

        sb.append("< svn-redrock-repo.dmp > ultimateFilter.dmp");
        System.out.println(sb.toString());

//        File file = new File("C:\\attask\\src\\trunk");
//        File[] files = file.listFiles();
//
//        for(File f : files){
//            System.out.println(f.getAbsolutePath());
//            System.out.println(f.getName());
//            System.out.println();
//        }
    }

    private static boolean desiredStrings(String compare){
        String[] strings = {"java", "groovy", "xml" , "jsp", "css", "less", "properties", "pl", "ipr", "iml", "txt", "key", "html"};
        for(String s : strings){
            if(s.equalsIgnoreCase(compare))
                return true;
        }

        return false;
    }

    private static void grabFileExtensions(String filePath){
        File file = new File(filePath);
        File[] files = file.listFiles();

        for(File f : files){
            if(!f.isHidden())
            {
                if(f.isDirectory())
                    grabFileExtensions(f.getAbsolutePath());
                else{
                    try{
                        String[] fileNames = f.getName().split("\\.");
                        if(fileNames.length > 1){
                            String fileName = fileNames[fileNames.length-1];
                            if(undesiredExtensionMap.get(fileName) == null){
                                undesiredExtensionMap.put(fileName, "a");
                            }
                        }
                    }catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("Error on this path: " + f.getAbsolutePath());
                    }
                }
            }
        }
    }
}

package proba;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Proba {
	
	private ArrayList<String> fileList;
	private static final String OUTPUT_ZIP_FILE = "C:\\Users\\COFITIGAR\\Desktop\\images.zip";
	private static String source  = null;
	
	public Proba() {
		fileList = new ArrayList<String>();
	}

	public static void main(String[] args) throws IOException {
		
//		"C:\Users\COFITIGAR\Desktop\slike"
		

		Scanner scanner = new Scanner(System.in);  
		System.out.println("Unesite putanju do foldera: ");
		String datoteka = scanner.next(); 
		
		
		

		
		source=datoteka.toString();
		Proba zip = new Proba();
		zip.generateFileList(new File(source));
		zip.zipIt(OUTPUT_ZIP_FILE);
		
 
	}
	


    public void zipIt(String zipFile) throws IOException
    {

        byte[] buffer = new byte[1024];


        try (
                FileOutputStream fos = new FileOutputStream(zipFile);
                ZipOutputStream zos = new ZipOutputStream(fos))
        {

            System.out.println("Zipuje se u : " + zipFile);

            for (String file : this.fileList)
            {
            	String extension = "";

            	int i = file.lastIndexOf('.');
            	if (i >= 0) {
            	    extension = file.substring(i+1);
            	    
            	}
            	
            	if(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("gif") || extension.equals("png")) {
                System.out.println("U zip dodat : " + file);
                ZipEntry ze = new ZipEntry(file);
                zos.putNextEntry(ze);

                FileInputStream in = new FileInputStream(source
                        + File.separator + file);

                int len;
                while ((len = in.read(buffer)) > 0)
                {
                    zos.write(buffer, 0, len);
                }

                in.close();
                
            }
            }
        
            zos.closeEntry();
            
          
            System.out.println("Zip kreiran...");
        }

    }
    
    public void generateFileList(File node)
    {

        // add file only
        if (node.isFile())
        {
            fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
        }

        if (node.isDirectory())
        {
            String[] subNote = node.list();
            for (String filename : subNote)
            {
                generateFileList(new File(node, filename));
            }
        }

    }
    
    private String generateZipEntry(String file)
    {
        return file.substring(source.length() + 1, file.length());
    }
}
	



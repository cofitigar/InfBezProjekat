package potpis;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import proba.Proba;

public class Potpis {

	private static final String IN_FILE = "./data/slike.xml";
	private static final String OUT_FILE = "./data/potpisano.xml";
	private static final String KEY_STORE_FILE = "./data/sifra.jks";


    static {
        Security.addProvider(new BouncyCastleProvider());
        org.apache.xml.security.Init.init();
    }

    public void testIt() {
        Document doc = loadDocument(IN_FILE);
        Certificate cert = readCertificate();
        if (doc == null || cert == null)
            return;
        PrivateKey pk = readPrivateKey();
        System.out.println("Potpisuje se....");
        doc = signDocument(doc, pk, cert);
        saveDocument(doc, OUT_FILE);
        
//        Proba p = new Proba();
//        p.zaZip.add(OUT_FILE);
        
        
        System.out.println("Potpisivanje zavrseno");


    }

    private Document loadDocument(String file) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            return db.parse(new File(file));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveDocument(Document doc, String fileName) {
        try {
            File outFile = new File(fileName);
            FileOutputStream f = new FileOutputStream(outFile);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(f);

            transformer.transform(source, result);

            f.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Certificate readCertificate() {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(KEY_STORE_FILE));
            ks.load(in, "sifra".toCharArray());

            if(ks.isKeyEntry("sifra")) {
                return ks.getCertificate("sifra");
            }
            else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private PrivateKey readPrivateKey() {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(KEY_STORE_FILE));
            ks.load(in, "sifra".toCharArray());
            if(ks.isKeyEntry("sifra")) {
                return (PrivateKey) ks.getKey("sifra", "sifra".toCharArray());
            }
            else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Document signDocument(Document doc, PrivateKey privateKey, Certificate cert) {

        try {
            Element rootEl = doc.getDocumentElement();
            XMLSignature sig = new XMLSignature(doc, null, XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1);
            Transforms transforms = new Transforms(doc);
            transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
            transforms.addTransform(Transforms.TRANSFORM_C14N_WITH_COMMENTS);
            sig.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);
            sig.addKeyInfo(cert.getPublicKey());
            sig.addKeyInfo((X509Certificate) cert);
            rootEl.appendChild(sig.getElement());
            sig.sign(privateKey);
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    


    
    

}

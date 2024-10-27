package tarea7;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * La clase Tarea7b se encarga de pedir información sobre alumnos,
 * almacenarla y generar un archivo XML con los datos de los alumnos
 * convirtiendo los datos en atributos.
 * 
 * @author Aleksandar Aleksandrov
 */
public class Tarea7b extends Tarea7 {

    /**
     * Crea y agrega un atributo al elemento alumno en el documento XML.
     * 
     * @param dato El nombre del atributo que se va a crear. 
     * @param valorDato El valor de texto del atributo.
     * @param alumno El elemento padre al que se añadirá el nuevo atributo.
     * @param doc El documento XML en el que se está creando el atributo.
     */
    public static void crearElementoAlumno(String dato, String valorDato, Element alumno, Document doc) {
        
        Attr atributo = doc.createAttribute(dato);
        atributo.setValue(valorDato);
        
        alumno.setAttributeNode(atributo);
    }

    /**
     * El método principal ejecuta el programa, creando objetos 
     * Alumno y genera un archivo XML con los datos de los alumnos.
     * 
     * @throws ParseException Si hay un error en el formato de la fecha de nacimiento ingresada.
     */
    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);
        ArrayList<Alumno> listaAlumnos = new ArrayList<Alumno>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        for (int i = 1; i < 6; i++) {
            Alumno a = new Alumno();
            a.cargarAlumno(sc, i);
            listaAlumnos.add(a);
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation imp = builder.getDOMImplementation();
            
            Document doc = imp.createDocument(null, "Alumnos", null);
            doc.setXmlVersion("1.0");

            for(Alumno a : listaAlumnos) {
                Element alumno = doc.createElement("alumno");
                doc.getDocumentElement().appendChild(alumno);

                crearElementoAlumno("NIA", Integer.toString(a.getNia()), alumno, doc);
                crearElementoAlumno("Nombre", a.getNombre(), alumno, doc);
                crearElementoAlumno("Apellidos", a.getApellidos(), alumno, doc);
                crearElementoAlumno("Genero", Character.toString(a.getGenero()), alumno, doc);
                crearElementoAlumno("FechaNacimiento", dateFormat.format(a.getfNacimiento()), alumno, doc);
                crearElementoAlumno("Ciclo", a.getCiclo(), alumno, doc);
                crearElementoAlumno("Curso", a.getCurso(), alumno, doc);
                crearElementoAlumno("Grupo", a.getGrupo(), alumno, doc);
            }

            DOMSource source = new DOMSource(doc);
            StreamResult res = new StreamResult(new java.io.File("AlumnosTarea7b.xml"));
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.transform(source, res);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

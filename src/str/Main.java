package str;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Main {
	public static void main(String[] args)
	{
 
		
		//Init Hibernate
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml"); 
 
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		
		//Load course
		Object o=session.load(Course.class,new Long(1));
		Course c=(Course)o;
		// For loading Transaction scope is not necessary...
		System.out.println("Loaded object product name is___ "+c.getCourseName());
		
		
		/*
		//Save Course
		 
		Course c=new Course();
		c.setCourseId(101);
		c.setCourseName("Super cours");
		
		Transaction tx = session.beginTransaction();
		session.save(c);
		System.out.println("Object saved successfully.....!!");
		
		tx.commit();
		
		*/
		
		//Close connection
		session.close();
		factory.close();
		
		
		//Create XML
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Cours");
			doc.appendChild(rootElement);

			Element cours = doc.createElement("Cours");
			rootElement.appendChild(cours);

			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode(String.valueOf(c.getCourseId())));
			cours.appendChild(id);

			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(c.getCourseName()));
			cours.appendChild(name);
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("courses.xml"));

			transformer.transform(source, result);

			System.out.println("File saved!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
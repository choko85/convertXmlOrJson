package com.example.convert.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory;
import org.springframework.stereotype.Component;

import com.example.convert.model.Errors;
import com.example.convert.model.Reference;
import com.example.convert.model.Report;

@Component
public class FileService {

	public Report readLineFile(String fileName) {

		List<Reference> list = new ArrayList<Reference>();
		List<Errors> errors = new ArrayList<Errors>();
		AtomicInteger index = new AtomicInteger();
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			stream.peek(n -> System.out.println(n)) // debug stream
					.forEach(line -> {
						// decouper ligne avec separateur
						String[] splitted = line.split(";");
						// nombre de ligne
						int nbLine = index.incrementAndGet();
						if (getColor(splitted[1])) {
							Reference reference = new Reference();
							reference.setNumReference(splitted[0]);
							reference.setColor(splitted[1]);
							reference.setPrice(splitted[2]);
							reference.setSize(splitted[3]);
							list.add(reference);
						} else {
							Errors error = new Errors();
							error.setLine(nbLine);
							error.setValue(line);
							error.setMessage("Incorrect value for color");
							errors.add(error);

						}

					});

		} catch (IOException e) {
			e.printStackTrace();
		}
		// charger object
		Report report = new Report();
		report.setInputFile(fileName);
		report.setReferences(list);
		report.setErrors(errors);
		return report;
	}

	public void jaxbObjectToJsonOrXml(Report report, String media, String name, String path)
			throws FileNotFoundException {
		try {
			// Definissez les differentes proprietes
			Map<String, Object> properties = new HashMap<>();
			properties.put(JAXBContextProperties.MEDIA_TYPE, "application/" + media + "");
			properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);

			// Creation context avec propriete
			JAXBContext jaxbContext = JAXBContextFactory
					.createContext(new Class[] { Report.class, ObjectFactory.class }, properties);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// Marshall object
			StringWriter stringWriter = new StringWriter();
			jaxbMarshaller.marshal(report, stringWriter);
			// creation de fichier
			OutputStream os = new FileOutputStream(path + name + "." + media);
			jaxbMarshaller.marshal(report, os);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public boolean getColor(String color) {
		switch (color) {
		case "G":
			return true;
		case "B":
			return true;
		case "R":
			return true;
		}
		return false;
	}
}

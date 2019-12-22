package com.example.convert;

import java.io.FileNotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.convert.model.Report;
import com.example.convert.service.FileService;

@SpringBootApplication
public class ConvertApplication {

	private final static String XML = "xml";
	private final static String JSON = "json";

	public static void main(String[] args) throws FileNotFoundException {
		// Appel Context pour lancer Bean metier
		ConfigurableApplicationContext context = SpringApplication.run(ConvertApplication.class, args);
		FileService c = context.getBean(FileService.class);
		// recuper le fichier et chager objet
		Report report = c.readLineFile("file.txt");

		if (report != null) {
			// creation fichier Xml
			c.jaxbObjectToJsonOrXml(report, XML, "reportXml", "d:/");
			// creation fichier Json
			c.jaxbObjectToJsonOrXml(report, JSON, "reportJson", "d:/");
		}

	}
}

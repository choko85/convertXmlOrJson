package com.example.convert;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.convert.model.Errors;
import com.example.convert.model.Reference;
import com.example.convert.model.Report;
import com.example.convert.service.FileService;

import junitx.framework.FileAssert;

@RunWith(SpringRunner.class)
class ConvertApplicationTests {

	private final static String FILE_EX_JSON = "Test.json";
	private final static String FILE_AC_JSON = "fileTest.json";
	private final static String FILE_EX_XML = "Test.xml";
	private final static String FILE_AC_XML = "fileTest.xml";

	@Test
	void getFile() {

		// recuper le fichier et charge les donnees object
		FileService fileService = new FileService();
		Report rep = fileService.readLineFile("file.txt");
		System.err.println(rep.getInputFile());
		assertEquals(rep.getInputFile(), "file.txt");
		assertEquals(rep.getErrors().size(), 1);
		assertEquals(rep.getReferences().size(), 4);
	}

	@Test
	void getXmlOrJson() throws IOException {

		// Charger les donnees pour object
		FileService fileService = new FileService();
		Report report = new Report();
		Errors errors = new Errors();
		Reference reference = new Reference();
		reference.setColor("B");
		reference.setNumReference("1462100044");
		reference.setPrice("2.40");
		reference.setSize("3");
		List<Reference> references = new ArrayList<>();
		references.add(reference);
		errors.setLine(1);
		errors.setMessage("errors");
		errors.setValue("value");
		List<Errors> er = new ArrayList<>();
		er.add(errors);
		report.setErrors(er);
		report.setReferences(references);
		fileService.jaxbObjectToJsonOrXml(report, "xml", "Test", "");
		fileService.jaxbObjectToJsonOrXml(report, "json", "Test", "");

		// Test XML
		FileAssert.assertEquals(new File(FILE_EX_XML), new File(FILE_AC_XML));
		// Test JSON
		FileAssert.assertEquals(new File(FILE_EX_JSON), new File(FILE_AC_JSON));

	}

}

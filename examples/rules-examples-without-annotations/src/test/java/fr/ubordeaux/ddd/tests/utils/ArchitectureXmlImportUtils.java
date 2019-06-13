package fr.ubordeaux.ddd.tests.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fr.ubordeaux.ddd.rules.utils.ClassUtils;
import fr.ubordeaux.ddd.rules.utils.FieldUtils;
import fr.ubordeaux.ddd.rules.utils.MethodUtils;
import fr.ubordeaux.ddd.rules.utils.PackageUtils;

public class ArchitectureXmlImportUtils {
	private static NodeList getElementTagValues(Element element, String tag) {
		return element.getElementsByTagName(tag).item(0).getChildNodes();
	}

	private static String getElementTagValue(Element element, String tag) {
		return getElementTagValues(element, tag).item(0).getTextContent();
	}

	private static void importAnnotations(String name, NodeList xmlAnnotations, Map<String, Set<String>> annotatedMap) {
		for (int i = 0; i < xmlAnnotations.getLength(); ++i) {
			if (xmlAnnotations.item(i).getNodeType() == Node.ELEMENT_NODE) {
				String annotation = xmlAnnotations.item(i).getTextContent();
				if (!annotatedMap.containsKey(annotation)) {
					annotatedMap.put(annotation, new HashSet<>());
				}
				annotatedMap.get(annotation).add(name);
			}
		}
	}

	private static void importFields(NodeList xmlFields) {
		for (int i = 0; i < xmlFields.getLength(); ++i) {
			if (xmlFields.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element xmlField = (Element)xmlFields.item(i);
				importAnnotations(getElementTagValue(xmlField, "name"), getElementTagValues(xmlField, "annotations"), FieldUtils.annotatedFields);
			}
		}
	}

	private static void importMethods(NodeList xmlMethods) {
		for (int i = 0; i < xmlMethods.getLength(); ++i) {
			if (xmlMethods.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element xmlMethod = (Element)xmlMethods.item(i);
				importAnnotations(getElementTagValue(xmlMethod, "name"), getElementTagValues(xmlMethod, "annotations"), MethodUtils.annotatedMethods);
			}
		}
	}

	private static void importClasses(NodeList xmlClasses) {
		for (int i = 0; i < xmlClasses.getLength(); ++i) {
			if (xmlClasses.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element xmlClass = (Element)xmlClasses.item(i);
				importAnnotations(getElementTagValue(xmlClass, "name"), getElementTagValues(xmlClass, "annotations"), ClassUtils.annotatedClasses);
				importFields(getElementTagValues(xmlClass, "fields"));
				importMethods(getElementTagValues(xmlClass, "methods"));
			}
		}
	}

	private static void importPackages(NodeList xmlPackages, boolean minimal) {
		for (int i = 0; i < xmlPackages.getLength(); ++i) {
			if (xmlPackages.item(i).getNodeType() == Node.ELEMENT_NODE && xmlPackages.item(i).hasChildNodes()) {
				Element xmlPackage = (Element)xmlPackages.item(i);
				importAnnotations(getElementTagValue(xmlPackage, "name"), getElementTagValues(xmlPackage, "annotations"), PackageUtils.annotatedPackages);
				if (!minimal) {
					importClasses(getElementTagValues(xmlPackage, "classes"));
				}
			}
		}
	}

	protected static void importXMLPackages() {
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("../ddd-rules-examples/packages.xml"));
			importPackages(document.getDocumentElement().getChildNodes(), true);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	protected static void importXMLPackagesArchitecture() {
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("../ddd-rules-examples/packages-architecture.xml"));
			importPackages(document.getDocumentElement().getChildNodes(), false);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	protected static void importXMLClassesArchitecture() {
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("../ddd-rules-examples/classes-architecture.xml"));
			importClasses(document.getDocumentElement().getChildNodes());
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		ArchitectureImportUtils.importPackagesFromPackageInfoClasses();
	}

	protected static void importXMLMinimalArchitecture() {
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("../ddd-rules-examples/minimal-architecture.xml"));
			importClasses(document.getDocumentElement().getChildNodes());
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		ArchitectureImportUtils.importPackagesFromPackageInfoClasses();
	}
}
package fr.ubordeaux.ddd.tests;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.google.common.collect.Sets;
import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClassList;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.domain.JavaPackage;

import fr.ubordeaux.ddd.tests.utils.ArchitectureExportUtils;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ArchitectureXmlExport {
	private Element createTextNodeElement(Document document, String name, String text) {
		Element element = document.createElement(name);
		Node node = document.createTextNode(text);
		element.appendChild(node);
		return element;
	}

	private Element exportAnnotationsAsXML(Document document, Set<JavaAnnotation> annotations) {
		Element xmlAnnotations = document.createElement("annotations");
		for (JavaAnnotation annotation : annotations) {
			if (Annotation.class.isAssignableFrom(annotation.getRawType().reflect())) {
				xmlAnnotations.appendChild(createTextNodeElement(document, "annotation", annotation.getRawType().getSimpleName()));
			}
		}
		return xmlAnnotations;
	}

	private Element exportModifiersAsXML(Document document, Set<JavaModifier> modifiers) {
		Element xmlModifiers = document.createElement("modifiers");
		for (JavaModifier modifier : modifiers) {
			xmlModifiers.appendChild(createTextNodeElement(document, "modifier", modifier.name()));
		}
		return xmlModifiers;
	}

	private Element exportFieldsAsXML(Document document, Set<JavaField> fields, boolean minimal) {
		Element xmlFields = document.createElement("fields");
		for (JavaField field : fields) {
			Element xmlField = document.createElement("field");
			xmlField.appendChild(createTextNodeElement(document, "name", field.getFullName()));
			xmlField.appendChild(exportAnnotationsAsXML(document, field.getAnnotations()));
			if (!minimal) {
				xmlField.appendChild(exportModifiersAsXML(document, field.getModifiers()));
				xmlField.appendChild(createTextNodeElement(document, "type", field.getRawType().getFullName()));
			}
			xmlFields.appendChild(xmlField);
		}
		return xmlFields;
	}

	private Element exportParameterTypesAsXML(Document document, JavaClassList parameterTypes) {
		Element xmlParameterTypes = document.createElement("parameter_types");
		for (JavaClass parameterType : parameterTypes) {
			xmlParameterTypes.appendChild(createTextNodeElement(document, "parameter_type", parameterType.getFullName()));
		}
		return xmlParameterTypes;
	}

	private Element exportMethodsAsXML(Document document, Set<JavaMethod> methods, boolean minimal) {
		Element xmlMethods = document.createElement("methods");
		for (JavaMethod method : methods) {
			Element xmlMethod = document.createElement("method");
			xmlMethod.appendChild(createTextNodeElement(document, "name", method.getFullName()));
			xmlMethod.appendChild(exportAnnotationsAsXML(document, method.getAnnotations()));
			if (!minimal) {
				xmlMethod.appendChild(exportModifiersAsXML(document, method.getModifiers()));
				xmlMethod.appendChild(exportParameterTypesAsXML(document, method.getRawParameterTypes()));
				xmlMethod.appendChild(createTextNodeElement(document, "return_type", method.getRawReturnType().getFullName()));
			}
			xmlMethods.appendChild(xmlMethod);
		}
		return xmlMethods;
	}

	private Element exportClassesAsXML(Document document, Set<JavaClass> classes, boolean minimal) {
		Element xmlClasses = document.createElement("classes");
		for (JavaClass exportedClass : classes) {
			Element xmlClass = document.createElement("class");
			xmlClass.appendChild(createTextNodeElement(document, "name", exportedClass.getFullName()));
			xmlClass.appendChild(exportAnnotationsAsXML(document, exportedClass.getAnnotations()));
			xmlClass.appendChild(exportFieldsAsXML(document, exportedClass.getFields(), minimal));
			xmlClass.appendChild(exportMethodsAsXML(document, exportedClass.getMethods(), minimal));
			if (!minimal) {
				xmlClass.appendChild(exportModifiersAsXML(document, exportedClass.getModifiers()));
			}
			xmlClasses.appendChild(xmlClass);
		}
		return xmlClasses;
	}

	private Element exportPackagesAsXML(Document document, Map<JavaPackage, Set<JavaAnnotation>> packages, boolean minimal) {
		Element xmlPackages = document.createElement("packages");
		for (JavaPackage exportedPackage : packages.keySet()) {
			Element xmlPackage = document.createElement("package");
			xmlPackage.appendChild(createTextNodeElement(document, "name", exportedPackage.getName()));
			xmlPackage.appendChild(exportAnnotationsAsXML(document, packages.get(exportedPackage)));
			if (!minimal) {
				xmlPackage.appendChild(exportClassesAsXML(document, exportedPackage.getClasses(), minimal));
			}
			xmlPackages.appendChild(xmlPackage);
		}
		return xmlPackages;
	}

	public void exportAsXML(String name, Document document) {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(name));
			transformer.transform(domSource, streamResult);
		} catch (TransformerFactoryConfigurationError | TransformerException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void exportPackagesAsXML() {
		Map<JavaPackage, Set<JavaAnnotation>> packages = ArchitectureExportUtils.importAllPackages();
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			document.appendChild(exportPackagesAsXML(document, packages, true));
			exportAsXML("packages.xml", document);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void exportArchitectureFromPackagesAsXML() {
		Map<JavaPackage, Set<JavaAnnotation>> packages = ArchitectureExportUtils.importAllPackages();
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			document.appendChild(exportPackagesAsXML(document, packages, false));
			exportAsXML("packages-architecture.xml", document);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void exportArchitectureFromClassesAsXML() {
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			document.appendChild(exportClassesAsXML(document, Sets.newHashSet(Arrays.copyOf(ArchitectureExportUtils.classes.toArray(), ArchitectureExportUtils.classes.size(), JavaClass[].class)), false));
			exportAsXML("classes-architecture.xml", document);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void exportMinimalArchitectureAsXML() {
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			document.appendChild(exportClassesAsXML(document, Sets.newHashSet(Arrays.copyOf(ArchitectureExportUtils.classes.toArray(), ArchitectureExportUtils.classes.size(), JavaClass[].class)), true));
			exportAsXML("minimal-architecture.xml", document);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
}
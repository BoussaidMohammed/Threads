package org.mql.threads.parser;

import java.util.List;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.mql.threads.models.CharacterGame;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CharactersParser extends DefaultHandler {
	private List<CharacterGame> characters = new Vector<CharacterGame>();
	private CharacterGame currentChar;
	private String currentImg;
	private boolean bImg;
	private Vector<String> listImgs;

	public CharactersParser(String source) {
		SAXParserFactory factory = SAXParserFactory.newDefaultInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(source, this);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void startDocument() throws SAXException {
		System.out.println("le debut du document");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("character")) {
			currentChar = new CharacterGame();
			currentChar.setName(attributes.getValue("name"));
			currentChar.setIconsFolder(attributes.getValue("icons-folder"));
			currentChar.setImgType(attributes.getValue("img-type"));
			listImgs = new Vector<String>();
		} else if (qName.equals("img")) {
			bImg = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(bImg) {
			currentImg = new String(ch, start, length);
			listImgs.add(currentImg);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("character")) {
			
			currentChar.setRessources(listImgs);
			characters.add(currentChar);
		} else if (qName.equals("img")) {
			bImg = false;
		}
	}

	public void endDocument() throws SAXException {

	}

	public List<CharacterGame> getCharacters() {
		return characters;
	}

}

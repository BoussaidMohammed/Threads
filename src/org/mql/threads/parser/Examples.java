package org.mql.threads.parser;

import java.util.List;

import org.mql.threads.models.CharacterGame;

public class Examples {
	public static void main(String[] args) {
		CharactersParser parser = new CharactersParser("resources/characters.xml");
		List<CharacterGame> characters = parser.getCharacters();
		System.out.println(characters);
	}
	
	
	
}

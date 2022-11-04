package org.mql.threads.models;

import java.util.List;
import java.util.Vector;

public class CharacterGame {
	private String name;
	private String iconsFolder;
	private String imgType;
	private List<String> ressources;
	
	public CharacterGame() {
		ressources = new Vector<>();
	}

	public CharacterGame(String name, String iconsFolder, String imgType, List<String> ressources) {
		super();
		this.name = name;
		this.iconsFolder = iconsFolder;
		this.imgType = imgType;
		this.ressources = ressources;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIconsFolder() {
		return iconsFolder;
	}

	public void setIconsFolder(String iconsFolder) {
		if(!iconsFolder.endsWith("/")) {
			iconsFolder += "/";
		}
		this.iconsFolder = iconsFolder;
	}

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		if(!imgType.startsWith(".")) {
			imgType = "." + imgType;
		}
		this.imgType = imgType;
	}

	public List<String> getRessources() {
		return ressources;
	}

	public void setRessources(List<String> ressources) {
		this.ressources = ressources;
	}

	@Override
	public String toString() {
		return "CharacterGame [name=" + name + ", iconsFolder=" + iconsFolder + ", imgType=" + imgType + ", ressources="
				+ ressources + "]";
	}
	
	
	
	
}

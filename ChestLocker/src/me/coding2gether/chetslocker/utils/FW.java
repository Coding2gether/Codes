package me.coding2gether.chetslocker.utils;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class FW {
	private File f;
	private YamlConfiguration c;
	public FW(String filePath, String fileName) {
		this.f = new File(filePath, fileName);
		this.c = YamlConfiguration.loadConfiguration(this.f);
		
	}
	public FW setValue(String valuePath, Object value) {
		c.set(valuePath, value);
		return this;
	}
	public Object getObject(String valuePath) {
		return c.get(valuePath);
	}
	public FW addDefault(String path, Object value) {
		c.options().copyDefaults(true);
		c.addDefault(path, value);
		return this;
		
	}
	public FW setHeader(String header) {
		c.options().header(header);
		return this;
	}
	public Integer getInt(String ValuePath) {
		return c.getInt(ValuePath);
	}
	public Long getLong(String ValuePath) {
		return c.getLong(ValuePath);
	}
	public String getString(String ValuePath) {
		return c.getString(ValuePath);
	}
	public Boolean getBoolean(String ValuePath) {
		return c.getBoolean(ValuePath);
	}
	public List<String> getStringList(String ValuePath) {
		return c.getStringList(ValuePath);
	}
	public Set<String> getKeys(boolean deep){
		return c.getKeys(deep);
	}
	public ConfigurationSection getConfigurationSection(String Section) {
		return c.getConfigurationSection(Section);
	}
	public void delete() {
		this.f.delete();
	}
	public void createFile() {
		try {
			this.f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public boolean exist() {
		if(!this.f.exists()) {
			try {
				this.f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			return this.f.exists();
		}
		return this.f.exists();
	}
	public void saveFile() {
		try {
			this.c.save(this.f);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}

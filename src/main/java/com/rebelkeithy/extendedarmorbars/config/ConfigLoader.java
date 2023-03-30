package com.rebelkeithy.extendedarmorbars.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public final class ConfigLoader {
    private ConfigLoader(){}

    private static final String CONFIG_DIR = FabricLoader.getInstance().getConfigDir().toString();

    public static Config loadConfigFile(String filename) {
        Config config;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            config = gson.fromJson(new FileReader(new File(CONFIG_DIR, filename)), Config.class);
        } catch (FileNotFoundException e) {
            System.out.println("Generating config file for mod: toughness bars.");
            config = new Config();
            saveConfigFile(filename, config);
        }
        return config;
    }

    public static void saveConfigFile(String filename, Config config) {
        try {
            var writer = new FileWriter(new File(CONFIG_DIR, filename));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(config, writer);
            writer.flush();
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

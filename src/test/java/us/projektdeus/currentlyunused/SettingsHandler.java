package us.projektdeus.currentlyunused;

public class SettingsHandler {
    public static void main(String[] args) {loadSettings();}
    //For testing purposes

    public static void loadSettings(){
        FileManager fileManager = new FileManager();
        fileManager.checkDirExists("src/main/resources/");
        fileManager.checkDirExists("src/main/resources/config/");
        fileManager.checkFileExists("src/main/resources/config/settings.ini");
    }




}
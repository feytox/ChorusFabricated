package chorusfabricated;

import io.wispforest.owo.config.annotation.Config;

@Config(name = "chorus-config", wrapperName = "ChorusConfig")
public class ChorusConfigModel {

    public String chorusActivator = "NaLorY";
    public boolean chorusPlaced = false;
    public int chorusGrowSpeed = 3000;
}

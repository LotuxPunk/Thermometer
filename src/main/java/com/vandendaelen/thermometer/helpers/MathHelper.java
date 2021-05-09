package com.vandendaelen.thermometer.helpers;

public class MathHelper {

    // Maths from : https://www.reddit.com/r/Minecraft/comments/7tfntf/real_life_temperature_in_minecraft_my_own_version/
    public static double getFahrenheitTemperature(float minecraftTemp){
        return 25.27027027 + (44.86486486 * minecraftTemp);
    }

    public static double getCelsiusTemperature(double fahrenheitTemperature){
        return (fahrenheitTemperature - 32) * (float)5/(float)9;
    }
}

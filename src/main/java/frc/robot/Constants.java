// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Preferences;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class ArmConstants {
    public static final int kLowerSparkPort = 20;
    public static final int kUpperSparkPort = 21;
    public static final int kLowerEncoderChannel = 7;
    public static final int kUpperEncoderChannel = 8;
    public static final int kLowerLimitChannel = 0;
    public static final int kUpperLimitChannel = 1;

    public static final double kLowerOffset = 304.632908 - 180; // opposite - 180
    public static final double kUpperOffset = 279.201265 - 90; // vertical - 90
    public static final double kLowerMaxVolts = 4;
    public static final double kUpperMaxVolts = 6;

    public static final double kDefaultLowerP = 0.05;
    public static final double kDefaultLowerI = 0.003;
    public static final double kDefaultLowerD = 0;
    public static final double kDefaultLowerS = 0;
    public static final double kDefaultLowerG = 0.2;
    public static final double kDefaultLowerV = 0;
    public static final double kDefaultLowerA = 0;

    public static final double kDefaultUpperP = 0.06;
    public static final double kDefaultUpperI = 0.004;
    public static final double kDefaultUpperD = 0;
    public static final double kDefaultUpperS = 0;
    public static final double kDefaultUpperG = 0.2;
    public static final double kDefaultUpperV = 0;
    public static final double kDefaultUpperA = 0;

    public static void initArmPreferences() {
      Preferences.initDouble("kArmLowerP", ArmConstants.kDefaultLowerP);
      Preferences.initDouble("kArmLowerI", ArmConstants.kDefaultLowerI);
      Preferences.initDouble("kArmLowerD", ArmConstants.kDefaultLowerD);
      Preferences.initDouble("kArmLowerS", ArmConstants.kDefaultLowerS);
      Preferences.initDouble("kArmLowerG", ArmConstants.kDefaultLowerG);
      Preferences.initDouble("kArmLowerV", ArmConstants.kDefaultLowerV);
      Preferences.initDouble("kArmLowerA", ArmConstants.kDefaultLowerA);
      
      Preferences.initDouble("kArmUpperP", ArmConstants.kDefaultUpperP);
      Preferences.initDouble("kArmUpperI", ArmConstants.kDefaultUpperI);
      Preferences.initDouble("kArmUpperD", ArmConstants.kDefaultUpperD);
      Preferences.initDouble("kArmUpperS", ArmConstants.kDefaultUpperS);
      Preferences.initDouble("kArmUpperG", ArmConstants.kDefaultUpperG);
      Preferences.initDouble("kArmUpperV", ArmConstants.kDefaultUpperV);
      Preferences.initDouble("kArmUpperA", ArmConstants.kDefaultUpperA);
      
      System.out.println("Arm robot preferences initialized.");
    }

    public static void resetArmPreferences() {
      Preferences.setDouble("kArmLowerP", ArmConstants.kDefaultLowerP);
      Preferences.setDouble("kArmLowerI", ArmConstants.kDefaultLowerI);
      Preferences.setDouble("kArmLowerD", ArmConstants.kDefaultLowerD);
      Preferences.setDouble("kArmLowerS", ArmConstants.kDefaultLowerS);
      Preferences.setDouble("kArmLowerG", ArmConstants.kDefaultLowerG);
      Preferences.setDouble("kArmLowerV", ArmConstants.kDefaultLowerV);
      Preferences.setDouble("kArmLowerA", ArmConstants.kDefaultLowerA);
      
      Preferences.setDouble("kArmUpperP", ArmConstants.kDefaultUpperP);
      Preferences.setDouble("kArmUpperI", ArmConstants.kDefaultUpperI);
      Preferences.setDouble("kArmUpperD", ArmConstants.kDefaultUpperD);
      Preferences.setDouble("kArmUpperS", ArmConstants.kDefaultUpperS);
      Preferences.setDouble("kArmUpperG", ArmConstants.kDefaultUpperG);
      Preferences.setDouble("kArmUpperV", ArmConstants.kDefaultUpperV);
      Preferences.setDouble("kArmUpperA", ArmConstants.kDefaultUpperA);
      
      System.out.println("Arm robot preferences reset.");
    }

    public static double getKLowerP() {
      return Preferences.getDouble("kArmLowerP", kDefaultLowerP);
    }

    public static double getKLowerI() {
      return Preferences.getDouble("kArmLowerI", kDefaultLowerI);
    }

    public static double getKLowerD() {
      return Preferences.getDouble("kArmLowerD", kDefaultLowerD);
    }

    public static double getKLowerS() {
      return Preferences.getDouble("kArmLowerS", kDefaultLowerS);
    }

    public static double getKLowerG() {
      return Preferences.getDouble("kArmLowerG", kDefaultLowerG);
    }

    public static double getKLowerV() {
      return Preferences.getDouble("kArmLowerV", kDefaultLowerV);
    }

    public static double getKLowerA() {
      return Preferences.getDouble("kArmLowerA", kDefaultLowerA);
    }

    public static double getKUpperP() {
      return Preferences.getDouble("kArmUpperP", kDefaultUpperP);
    }

    public static double getKUpperI() {
      return Preferences.getDouble("kArmUpperI", kDefaultUpperI);
    }

    public static double getKUpperD() {
      return Preferences.getDouble("kArmUpperD", kDefaultUpperD);
    }

    public static double getKUpperS() {
      return Preferences.getDouble("kArmUpperS", kDefaultUpperS);
    }

    public static double getKUpperG() {
      return Preferences.getDouble("kArmUpperG", kDefaultUpperG);
    }

    public static double getKUpperV() {
      return Preferences.getDouble("kArmUpperV", kDefaultUpperV);
    }

    public static double getKUpperA() {
      return Preferences.getDouble("kArmUpperA", kDefaultUpperA);
    }
  }

  public static class ClawConstants {
    public static final int kSolenoidPort = 6;
  }

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kAuxDriverControllerPort = 1;
  }
}

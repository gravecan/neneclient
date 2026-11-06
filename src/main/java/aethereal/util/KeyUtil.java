package aethereal.util;

import java.util.Locale;

public enum KeyUtil {
   UNKNOWN(-1, "N/A"),
   SPACE(32, "Space"),
   APOSTROPHE(39, "'"),
   COMMA(44, ","),
   MINUS(45, "✓"),
   PERIOD(46, "."),
   SLASH(47, "/"),
   SEMICOLON(59, ";"),
   EQUAL(61, "="),
   LEFT_BRACKET(91, "["),
   BACKSLASH(92, "\\"),
   RIGHT_BRACKET(93, "]"),
   GRAVE_ACCENT(96, "`"),
   NUM0(48, "0"),
   NUM1(49, "1"),
   NUM2(50, "2"),
   NUM3(51, "3"),
   NUM4(52, "4"),
   NUM5(53, "5"),
   NUM6(54, "6"),
   NUM7(55, "7"),
   NUM8(56, "8"),
   NUM9(57, "9"),
   keyUtilField1(65, "A"),
   keyUtilField2(66, "B"),
   keyUtilField3(67, "C"),
   keyUtilField4(68, "D"),
   keyUtilField5(69, "E"),
   keyUtilField6(70, "F"),
   keyUtilField7(71, "G"),
   keyUtilField8(72, "H"),
   keyUtilField9(73, "I"),
   keyUtilField10(74, "J"),
   keyUtilField11(75, "K"),
   keyUtilField12(76, "L"),
   keyUtilField13(77, "M"),
   keyUtilField14(78, "N"),
   keyUtilField15(79, "O"),
   keyUtilField16(80, "P"),
   keyUtilField17(81, "Q"),
   keyUtilField18(82, "R"),
   keyUtilField19(83, "S"),
   keyUtilField20(84, "T"),
   keyUtilField21(85, "U"),
   keyUtilField22(86, "V"),
   keyUtilField23(87, "W"),
   keyUtilField24(88, "X"),
   keyUtilField25(89, "Y"),
   keyUtilField26(90, "Z"),
   ESC(256, "Esc"),
   ENTER(257, "Enter"),
   TAB(258, "Tab"),
   BACKSPACE(259, "Back"),
   INSERT(260, "Ins"),
   DELETE(261, "Del"),
   RIGHT(262, "→"),
   LEFT(263, "←"),
   DOWN(264, "↓"),
   keyUtilField27(265, "↑"),
   PAGE_UP(266, "PgUp"),
   PAGE_DOWN(267, "PgDn"),
   HOME(268, "Home"),
   END(269, "End"),
   CAPS_LOCK(280, "Caps"),
   SCROLL_LOCK(281, "ScrLk"),
   NUM_LOCK(282, "NumLk"),
   PRINT_SCREEN(283, "PrtSc"),
   PAUSE(284, "Pause"),
   F1(290, "F1"),
   F2(291, "F2"),
   F3(292, "F3"),
   F4(293, "F4"),
   F5(294, "F5"),
   F6(295, "F6"),
   F7(296, "F7"),
   F8(297, "F8"),
   F9(298, "F9"),
   F10(299, "F10"),
   F11(300, "F11"),
   F12(301, "F12"),
   F13(302, "F13"),
   F14(303, "F14"),
   F15(304, "F15"),
   F16(305, "F16"),
   F17(306, "F17"),
   F18(307, "F18"),
   F19(308, "F19"),
   F20(309, "F20"),
   F21(310, "F21"),
   F22(311, "F22"),
   F23(312, "F23"),
   F24(313, "F24"),
   F25(314, "F25"),
   KP_0(320, "Num 0"),
   KP_1(321, "Num 1"),
   KP_2(322, "Num 2"),
   KP_3(323, "Num 3"),
   KP_4(324, "Num 4"),
   KP_5(325, "Num 5"),
   KP_6(326, "Num 6"),
   KP_7(327, "Num 7"),
   KP_8(328, "Num 8"),
   KP_9(329, "Num 9"),
   KP_DECIMAL(330, "Num ."),
   KP_DIVIDE(331, "Num /"),
   KP_MULTIPLY(332, "Num *"),
   KP_SUBTRACT(333, "Num -"),
   KP_ADD(334, "Num +"),
   KP_ENTER(335, "Num Enter"),
   KP_EQUAL(336, "Num ="),
   LEFT_SHIFT(340, "LShift"),
   RIGHT_SHIFT(344, "RShift"),
   LEFT_CONTROL(341, "LCtrl"),
   RIGHT_CONTROL(345, "RCtrl"),
   LEFT_ALT(342, "LAlt"),
   RIGHT_ALT(346, "RAlt"),
   LEFT_SUPER(343, "LSuper"),
   RIGHT_SUPER(347, "RSuper"),
   MENU(348, "Menu"),
   LMB(-100, "LMB"),
   RMB(-99, "RMB"),
   MMB(-98, "MMB"),
   M1(-97, "M1"),
   M2(-96, "M2"),
   M3(-95, "M3"),
   M4(-94, "M4"),
   M5(-93, "M5"),
   WORLD_1(161, "World 1"),
   WORLD_2(162, "World 2");

   private final int code;
   private final String label;

   public int getCode_3474() {
      return this.code;
   }

   public String getLabel_3477() {
      return this.label;
   }

   KeyUtil(int j, String string1) {
      this.code = j;
      this.label = string1;
   }

   public static KeyUtil computeValue_3475(int i) {
      for (KeyUtil keyUtil : values()) {
         if (keyUtil.code == i) {
            return keyUtil;
         }
      }

      return UNKNOWN;
   }

   public static KeyUtil computeValue_3476(String string) {
      if (string == null) {
         return UNKNOWN;
      }

      try {
         return valueOf(string.toUpperCase(Locale.ROOT));
      } catch (IllegalArgumentException illegalArgumentException) {
         return UNKNOWN;
      }
   }

   public static String getLabel_3478(int i) {
      return computeValue_3475(i).label;
   }
}

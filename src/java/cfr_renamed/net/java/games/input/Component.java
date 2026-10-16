/*
 * Decompiled with CFR 0.152.
 */
package net.java.games.input;

public interface Component {
    public Identifier getIdentifier();

    public boolean isRelative();

    public boolean isAnalog();

    public float getDeadZone();

    public float getPollData();

    public String getName();

    public static class POV {
        final public static float OFF = 0.0f;
        final public static float CENTER = 0.0f;
        final public static float UP_LEFT = 0.125f;
        final public static float UP = 0.25f;
        final public static float UP_RIGHT = 0.375f;
        final public static float RIGHT = 0.5f;
        final public static float DOWN_RIGHT = 0.625f;
        final public static float DOWN = 0.75f;
        final public static float DOWN_LEFT = 0.875f;
        final public static float LEFT = 1.0f;
    }

    public static class Identifier {
        private final String name;

        protected Identifier(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return this.name;
        }

        public static class Key
        extends Identifier {
            final public static Key VOID = new Key("Void");
            final public static Key ESCAPE = new Key("Escape");
            final public static Key _1 = new Key("1");
            final public static Key _2 = new Key("2");
            final public static Key _3 = new Key("3");
            final public static Key _4 = new Key("4");
            final public static Key _5 = new Key("5");
            final public static Key _6 = new Key("6");
            final public static Key _7 = new Key("7");
            final public static Key _8 = new Key("8");
            final public static Key _9 = new Key("9");
            final public static Key _0 = new Key("0");
            final public static Key MINUS = new Key("-");
            final public static Key EQUALS = new Key("=");
            final public static Key BACK = new Key("Back");
            final public static Key TAB = new Key("Tab");
            final public static Key var_net_java_games_input_Component$Identifier$Key_Q;
            final public static Key var_net_java_games_input_Component$Identifier$Key_W;
            final public static Key var_net_java_games_input_Component$Identifier$Key_E;
            final public static Key var_net_java_games_input_Component$Identifier$Key_R;
            final public static Key var_net_java_games_input_Component$Identifier$Key_T;
            final public static Key var_net_java_games_input_Component$Identifier$Key_Y;
            final public static Key var_net_java_games_input_Component$Identifier$Key_U;
            final public static Key var_net_java_games_input_Component$Identifier$Key_I;
            final public static Key var_net_java_games_input_Component$Identifier$Key_O;
            final public static Key var_net_java_games_input_Component$Identifier$Key_P;
            final public static Key LBRACKET;
            final public static Key RBRACKET;
            final public static Key RETURN;
            final public static Key LCONTROL;
            final public static Key var_net_java_games_input_Component$Identifier$Key_A;
            final public static Key var_net_java_games_input_Component$Identifier$Key_S;
            final public static Key var_net_java_games_input_Component$Identifier$Key_D;
            final public static Key var_net_java_games_input_Component$Identifier$Key_F;
            final public static Key var_net_java_games_input_Component$Identifier$Key_G;
            final public static Key var_net_java_games_input_Component$Identifier$Key_H;
            final public static Key var_net_java_games_input_Component$Identifier$Key_J;
            final public static Key var_net_java_games_input_Component$Identifier$Key_K;
            final public static Key var_net_java_games_input_Component$Identifier$Key_L;
            final public static Key SEMICOLON;
            final public static Key APOSTROPHE;
            final public static Key GRAVE;
            final public static Key LSHIFT;
            final public static Key BACKSLASH;
            final public static Key var_net_java_games_input_Component$Identifier$Key_Z;
            final public static Key var_net_java_games_input_Component$Identifier$Key_X;
            final public static Key var_net_java_games_input_Component$Identifier$Key_C;
            final public static Key var_net_java_games_input_Component$Identifier$Key_V;
            final public static Key var_net_java_games_input_Component$Identifier$Key_B;
            final public static Key var_net_java_games_input_Component$Identifier$Key_N;
            final public static Key var_net_java_games_input_Component$Identifier$Key_M;
            final public static Key COMMA;
            final public static Key PERIOD;
            final public static Key SLASH;
            final public static Key RSHIFT;
            final public static Key MULTIPLY;
            final public static Key LALT;
            final public static Key SPACE;
            final public static Key CAPITAL;
            final public static Key F1;
            final public static Key F2;
            final public static Key F3;
            final public static Key F4;
            final public static Key F5;
            final public static Key F6;
            final public static Key F7;
            final public static Key F8;
            final public static Key F9;
            final public static Key F10;
            final public static Key NUMLOCK;
            final public static Key SCROLL;
            final public static Key NUMPAD7;
            final public static Key NUMPAD8;
            final public static Key NUMPAD9;
            final public static Key SUBTRACT;
            final public static Key NUMPAD4;
            final public static Key NUMPAD5;
            final public static Key NUMPAD6;
            final public static Key ADD;
            final public static Key NUMPAD1;
            final public static Key NUMPAD2;
            final public static Key NUMPAD3;
            final public static Key NUMPAD0;
            final public static Key DECIMAL;
            final public static Key F11;
            final public static Key F12;
            final public static Key F13;
            final public static Key F14;
            final public static Key F15;
            final public static Key KANA;
            final public static Key CONVERT;
            final public static Key NOCONVERT;
            final public static Key YEN;
            final public static Key NUMPADEQUAL;
            final public static Key CIRCUMFLEX;
            final public static Key AT;
            final public static Key COLON;
            final public static Key UNDERLINE;
            final public static Key KANJI;
            final public static Key STOP;
            final public static Key AX;
            final public static Key UNLABELED;
            final public static Key NUMPADENTER;
            final public static Key RCONTROL;
            final public static Key NUMPADCOMMA;
            final public static Key DIVIDE;
            final public static Key SYSRQ;
            final public static Key RALT;
            final public static Key PAUSE;
            final public static Key HOME;
            final public static Key UP;
            final public static Key PAGEUP;
            final public static Key LEFT;
            final public static Key RIGHT;
            final public static Key END;
            final public static Key DOWN;
            final public static Key PAGEDOWN;
            final public static Key INSERT;
            final public static Key DELETE;
            final public static Key LWIN;
            final public static Key RWIN;
            final public static Key APPS;
            final public static Key POWER;
            final public static Key SLEEP;
            final public static Key UNKNOWN;

            protected Key(String name) {
                super(name);
            }

            static {
                var_net_java_games_input_Component$Identifier$Key_Q = new Key("Q");
                var_net_java_games_input_Component$Identifier$Key_W = new Key("W");
                var_net_java_games_input_Component$Identifier$Key_E = new Key("E");
                var_net_java_games_input_Component$Identifier$Key_R = new Key("R");
                var_net_java_games_input_Component$Identifier$Key_T = new Key("T");
                var_net_java_games_input_Component$Identifier$Key_Y = new Key("Y");
                var_net_java_games_input_Component$Identifier$Key_U = new Key("U");
                var_net_java_games_input_Component$Identifier$Key_I = new Key("I");
                var_net_java_games_input_Component$Identifier$Key_O = new Key("O");
                var_net_java_games_input_Component$Identifier$Key_P = new Key("P");
                LBRACKET = new Key("[");
                RBRACKET = new Key("]");
                RETURN = new Key("Return");
                LCONTROL = new Key("Left Control");
                var_net_java_games_input_Component$Identifier$Key_A = new Key("A");
                var_net_java_games_input_Component$Identifier$Key_S = new Key("S");
                var_net_java_games_input_Component$Identifier$Key_D = new Key("D");
                var_net_java_games_input_Component$Identifier$Key_F = new Key("F");
                var_net_java_games_input_Component$Identifier$Key_G = new Key("G");
                var_net_java_games_input_Component$Identifier$Key_H = new Key("H");
                var_net_java_games_input_Component$Identifier$Key_J = new Key("J");
                var_net_java_games_input_Component$Identifier$Key_K = new Key("K");
                var_net_java_games_input_Component$Identifier$Key_L = new Key("L");
                SEMICOLON = new Key(";");
                APOSTROPHE = new Key("'");
                GRAVE = new Key("~");
                LSHIFT = new Key("Left Shift");
                BACKSLASH = new Key("\\");
                var_net_java_games_input_Component$Identifier$Key_Z = new Key("Z");
                var_net_java_games_input_Component$Identifier$Key_X = new Key("X");
                var_net_java_games_input_Component$Identifier$Key_C = new Key("C");
                var_net_java_games_input_Component$Identifier$Key_V = new Key("V");
                var_net_java_games_input_Component$Identifier$Key_B = new Key("B");
                var_net_java_games_input_Component$Identifier$Key_N = new Key("N");
                var_net_java_games_input_Component$Identifier$Key_M = new Key("M");
                COMMA = new Key(",");
                PERIOD = new Key(".");
                SLASH = new Key("/");
                RSHIFT = new Key("Right Shift");
                MULTIPLY = new Key("Multiply");
                LALT = new Key("Left Alt");
                SPACE = new Key(" ");
                CAPITAL = new Key("Caps Lock");
                F1 = new Key("F1");
                F2 = new Key("F2");
                F3 = new Key("F3");
                F4 = new Key("F4");
                F5 = new Key("F5");
                F6 = new Key("F6");
                F7 = new Key("F7");
                F8 = new Key("F8");
                F9 = new Key("F9");
                F10 = new Key("F10");
                NUMLOCK = new Key("Num Lock");
                SCROLL = new Key("Scroll Lock");
                NUMPAD7 = new Key("Num 7");
                NUMPAD8 = new Key("Num 8");
                NUMPAD9 = new Key("Num 9");
                SUBTRACT = new Key("Num -");
                NUMPAD4 = new Key("Num 4");
                NUMPAD5 = new Key("Num 5");
                NUMPAD6 = new Key("Num 6");
                ADD = new Key("Num +");
                NUMPAD1 = new Key("Num 1");
                NUMPAD2 = new Key("Num 2");
                NUMPAD3 = new Key("Num 3");
                NUMPAD0 = new Key("Num 0");
                DECIMAL = new Key("Num .");
                F11 = new Key("F11");
                F12 = new Key("F12");
                F13 = new Key("F13");
                F14 = new Key("F14");
                F15 = new Key("F15");
                KANA = new Key("Kana");
                CONVERT = new Key("Convert");
                NOCONVERT = new Key("Noconvert");
                YEN = new Key("Yen");
                NUMPADEQUAL = new Key("Num =");
                CIRCUMFLEX = new Key("Circumflex");
                AT = new Key("At");
                COLON = new Key("Colon");
                UNDERLINE = new Key("Underline");
                KANJI = new Key("Kanji");
                STOP = new Key("Stop");
                AX = new Key("Ax");
                UNLABELED = new Key("Unlabeled");
                NUMPADENTER = new Key("Num Enter");
                RCONTROL = new Key("Right Control");
                NUMPADCOMMA = new Key("Num ,");
                DIVIDE = new Key("Num /");
                SYSRQ = new Key("SysRq");
                RALT = new Key("Right Alt");
                PAUSE = new Key("Pause");
                HOME = new Key("Home");
                UP = new Key("Up");
                PAGEUP = new Key("Pg Up");
                LEFT = new Key("Left");
                RIGHT = new Key("Right");
                END = new Key("End");
                DOWN = new Key("Down");
                PAGEDOWN = new Key("Pg Down");
                INSERT = new Key("Insert");
                DELETE = new Key("Delete");
                LWIN = new Key("Left Windows");
                RWIN = new Key("Right Windows");
                APPS = new Key("Apps");
                POWER = new Key("Power");
                SLEEP = new Key("Sleep");
                UNKNOWN = new Key("Unknown");
            }
        }

        public static class Button
        extends Identifier {
            final public static Button _0 = new Button("0");
            final public static Button _1 = new Button("1");
            final public static Button _2 = new Button("2");
            final public static Button _3 = new Button("3");
            final public static Button _4 = new Button("4");
            final public static Button _5 = new Button("5");
            final public static Button _6 = new Button("6");
            final public static Button _7 = new Button("7");
            final public static Button _8 = new Button("8");
            final public static Button _9 = new Button("9");
            final public static Button _10 = new Button("10");
            final public static Button _11 = new Button("11");
            final public static Button _12 = new Button("12");
            final public static Button _13 = new Button("13");
            final public static Button _14 = new Button("14");
            final public static Button _15 = new Button("15");
            final public static Button _16 = new Button("16");
            final public static Button _17 = new Button("17");
            final public static Button _18 = new Button("18");
            final public static Button _19 = new Button("19");
            final public static Button _20 = new Button("20");
            final public static Button _21 = new Button("21");
            final public static Button _22 = new Button("22");
            final public static Button _23 = new Button("23");
            final public static Button _24 = new Button("24");
            final public static Button _25 = new Button("25");
            final public static Button _26 = new Button("26");
            final public static Button _27 = new Button("27");
            final public static Button _28 = new Button("28");
            final public static Button _29 = new Button("29");
            final public static Button _30 = new Button("30");
            final public static Button _31 = new Button("31");
            final public static Button TRIGGER = new Button("Trigger");
            final public static Button THUMB = new Button("Thumb");
            final public static Button THUMB2 = new Button("Thumb 2");
            final public static Button TOP = new Button("Top");
            final public static Button TOP2 = new Button("Top 2");
            final public static Button PINKIE = new Button("Pinkie");
            final public static Button BASE = new Button("Base");
            final public static Button BASE2 = new Button("Base 2");
            final public static Button BASE3 = new Button("Base 3");
            final public static Button BASE4 = new Button("Base 4");
            final public static Button BASE5 = new Button("Base 5");
            final public static Button BASE6 = new Button("Base 6");
            final public static Button DEAD = new Button("Dead");
            final public static Button var_net_java_games_input_Component$Identifier$Button_A;
            final public static Button var_net_java_games_input_Component$Identifier$Button_B;
            final public static Button var_net_java_games_input_Component$Identifier$Button_C;
            final public static Button var_net_java_games_input_Component$Identifier$Button_X;
            final public static Button var_net_java_games_input_Component$Identifier$Button_Y;
            final public static Button var_net_java_games_input_Component$Identifier$Button_Z;
            final public static Button LEFT_THUMB;
            final public static Button RIGHT_THUMB;
            final public static Button LEFT_THUMB2;
            final public static Button RIGHT_THUMB2;
            final public static Button SELECT;
            final public static Button MODE;
            final public static Button LEFT_THUMB3;
            final public static Button RIGHT_THUMB3;
            final public static Button TOOL_PEN;
            final public static Button TOOL_RUBBER;
            final public static Button TOOL_BRUSH;
            final public static Button TOOL_PENCIL;
            final public static Button TOOL_AIRBRUSH;
            final public static Button TOOL_FINGER;
            final public static Button TOOL_MOUSE;
            final public static Button TOOL_LENS;
            final public static Button TOUCH;
            final public static Button STYLUS;
            final public static Button STYLUS2;
            final public static Button UNKNOWN;
            final public static Button BACK;
            final public static Button EXTRA;
            final public static Button FORWARD;
            final public static Button LEFT;
            final public static Button MIDDLE;
            final public static Button RIGHT;
            final public static Button SIDE;

            public Button(String name) {
                super(name);
            }

            static {
                var_net_java_games_input_Component$Identifier$Button_A = new Button("A");
                var_net_java_games_input_Component$Identifier$Button_B = new Button("B");
                var_net_java_games_input_Component$Identifier$Button_C = new Button("C");
                var_net_java_games_input_Component$Identifier$Button_X = new Button("X");
                var_net_java_games_input_Component$Identifier$Button_Y = new Button("Y");
                var_net_java_games_input_Component$Identifier$Button_Z = new Button("Z");
                LEFT_THUMB = new Button("Left Thumb");
                RIGHT_THUMB = new Button("Right Thumb");
                LEFT_THUMB2 = new Button("Left Thumb 2");
                RIGHT_THUMB2 = new Button("Right Thumb 2");
                SELECT = new Button("Select");
                MODE = new Button("Mode");
                LEFT_THUMB3 = new Button("Left Thumb 3");
                RIGHT_THUMB3 = new Button("Right Thumb 3");
                TOOL_PEN = new Button("Pen");
                TOOL_RUBBER = new Button("Rubber");
                TOOL_BRUSH = new Button("Brush");
                TOOL_PENCIL = new Button("Pencil");
                TOOL_AIRBRUSH = new Button("Airbrush");
                TOOL_FINGER = new Button("Finger");
                TOOL_MOUSE = new Button("Mouse");
                TOOL_LENS = new Button("Lens");
                TOUCH = new Button("Touch");
                STYLUS = new Button("Stylus");
                STYLUS2 = new Button("Stylus 2");
                UNKNOWN = new Button("Unknown");
                BACK = new Button("Back");
                EXTRA = new Button("Extra");
                FORWARD = new Button("Forward");
                LEFT = new Button("Left");
                MIDDLE = new Button("Middle");
                RIGHT = new Button("Right");
                SIDE = new Button("Side");
            }
        }

        public static class Axis
        extends Identifier {
            final public static Axis var_net_java_games_input_Component$Identifier$Axis_X;
            final public static Axis var_net_java_games_input_Component$Identifier$Axis_Y;
            final public static Axis var_net_java_games_input_Component$Identifier$Axis_Z;
            final public static Axis RX;
            final public static Axis RY;
            final public static Axis RZ;
            final public static Axis SLIDER;
            final public static Axis SLIDER_ACCELERATION;
            final public static Axis SLIDER_FORCE;
            final public static Axis SLIDER_VELOCITY;
            final public static Axis X_ACCELERATION;
            final public static Axis X_FORCE;
            final public static Axis X_VELOCITY;
            final public static Axis Y_ACCELERATION;
            final public static Axis Y_FORCE;
            final public static Axis Y_VELOCITY;
            final public static Axis Z_ACCELERATION;
            final public static Axis Z_FORCE;
            final public static Axis Z_VELOCITY;
            final public static Axis RX_ACCELERATION;
            final public static Axis RX_FORCE;
            final public static Axis RX_VELOCITY;
            final public static Axis RY_ACCELERATION;
            final public static Axis RY_FORCE;
            final public static Axis RY_VELOCITY;
            final public static Axis RZ_ACCELERATION;
            final public static Axis RZ_FORCE;
            final public static Axis RZ_VELOCITY;
            final public static Axis POV;
            final public static Axis UNKNOWN;

            protected Axis(String name) {
                super(name);
            }

            static {
                var_net_java_games_input_Component$Identifier$Axis_X = new Axis("x");
                var_net_java_games_input_Component$Identifier$Axis_Y = new Axis("y");
                var_net_java_games_input_Component$Identifier$Axis_Z = new Axis("z");
                RX = new Axis("rx");
                RY = new Axis("ry");
                RZ = new Axis("rz");
                SLIDER = new Axis("slider");
                SLIDER_ACCELERATION = new Axis("slider-acceleration");
                SLIDER_FORCE = new Axis("slider-force");
                SLIDER_VELOCITY = new Axis("slider-velocity");
                X_ACCELERATION = new Axis("x-acceleration");
                X_FORCE = new Axis("x-force");
                X_VELOCITY = new Axis("x-velocity");
                Y_ACCELERATION = new Axis("y-acceleration");
                Y_FORCE = new Axis("y-force");
                Y_VELOCITY = new Axis("y-velocity");
                Z_ACCELERATION = new Axis("z-acceleration");
                Z_FORCE = new Axis("z-force");
                Z_VELOCITY = new Axis("z-velocity");
                RX_ACCELERATION = new Axis("rx-acceleration");
                RX_FORCE = new Axis("rx-force");
                RX_VELOCITY = new Axis("rx-velocity");
                RY_ACCELERATION = new Axis("ry-acceleration");
                RY_FORCE = new Axis("ry-force");
                RY_VELOCITY = new Axis("ry-velocity");
                RZ_ACCELERATION = new Axis("rz-acceleration");
                RZ_FORCE = new Axis("rz-force");
                RZ_VELOCITY = new Axis("rz-velocity");
                POV = new Axis("pov");
                UNKNOWN = new Axis("unknown");
            }
        }
    }
}


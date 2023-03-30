package cvut.fel.cz.pjv.model;

import javafx.scene.paint.Color;

/*
 * This class represents a state of a person
 * (not sick, sick, recovered)
 */

import javafx.scene.paint.Paint;

public enum State {
    NOT_SICK {
        public Color getColor() {
            return Color.LIGHTGOLDENRODYELLOW;
        }
    },
    SICK {
        public Color getColor() {
            return Color.LIGHTCORAL;
        }
    },
    RECOVERED {
        public Color getColor() {
            return Color.LIGHTCYAN;
        }
    };

    public Color getColor() {
        return null;
    }
}

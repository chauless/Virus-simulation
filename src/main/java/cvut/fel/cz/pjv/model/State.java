package cvut.fel.cz.pjv.model;

import javafx.scene.paint.Color;


/**
 * Represents the possible states of a person in a simulation.
 */
public enum State {

    /**
     * State indicating that the person is not sick.
     */
    NOT_SICK {
        public Color getColor() {
            return Color.LIGHTGOLDENRODYELLOW;
        }
    },

    /**
     * State indicating that the person is sick.
     */
    SICK {
        public Color getColor() {
            return Color.LIGHTCORAL;
        }
    },

    /**
     * State indicating that the person is recovered.
     */
    RECOVERED {
        public Color getColor() {
            return Color.LIGHTCYAN;
        }
    },

    /**
     * State indicating that the person is dead.
     */
    DEAD {
        public Color getColor() {
            return Color.BLACK;
        }
    };

    /**
     * @return The color associated with the state.
     */
    public abstract Color getColor();
}

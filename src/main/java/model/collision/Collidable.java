package model.collision;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public interface Collidable {
    int[] getXPoints();
    int[] getYPoints();
    int[] getRelativeXPoints(JPanel panel);
    int[] getRelativeYPoints(JPanel panel);
}

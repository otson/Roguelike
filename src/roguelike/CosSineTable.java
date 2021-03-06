/* 
 * Copyright (C) 2017 Otso Nuortimo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package roguelike;

/**
 *
 * @author otso
 */
public class CosSineTable {

    static final int precision = 360; // gradations per degree, adjust to suit

    static final int modulus = 360 * precision;
    static final float[] sin = new float[modulus]; // lookup table

    static {
    // a static initializer fills the table
        // in this implementation, units are in degrees
        for (int i = 0; i < sin.length; i++) {
            sin[i] = (float) Math.sin((i * Math.PI) / (precision * 180));
        }
    }
// Private function for table lookup

    private static float sinLookup(int a) {
        return a >= 0 ? sin[a % (modulus)] : -sin[-a % (modulus)];
    }

// These are your working functions:
    public static float sin(float a) {
        return sinLookup((int) (a * precision + 0.5f));
    }

    public static float cos(float a) {
        return sinLookup((int) ((a + 90f) * precision + 0.5f));
    }

}

/*
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.velonuboso.pruebas_evolutivos.interfaces;


/**
 * @author Rubén Héctor García Ortega <raiben@gmail.com>
 */
public final class Pair<T> {
    T element1;
    T element2;

    public Pair(T element1, T element2) {
        this.element1 = element1;
        this.element2 = element2;
    }

    public T getElement1() {
        return element1;
    }

    public T getElement2() {
        return element2;
    }
}
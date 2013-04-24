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
package org.velonuboso.pruebas_evolutivos;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.velonuboso.pruebas_evolutivos.impl.BasicOperator;
import org.velonuboso.pruebas_evolutivos.impl.ByteArrayIndividual;
import org.velonuboso.pruebas_evolutivos.impl.StringIndividual;
import org.velonuboso.pruebas_evolutivos.interfaces.Individual;
import org.velonuboso.pruebas_evolutivos.interfaces.Pair;

/**
 * @author Rubén Héctor García Ortega <raiben@gmail.com>
 */
public class Sample1 {

    public static int MAX = 100000;

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        ArrayList<Class> classes = new ArrayList<Class>();
        classes.add(StringIndividual.class);
        classes.add(ByteArrayIndividual.class);
        
        for (Class c:classes){
            Random r = new Random(0);
        
            long t0 = 0;
            
            t0 = System.currentTimeMillis();
            Individual i1 = (Individual) c.getDeclaredConstructor(int.class,Random.class).newInstance(MAX, r);
            long tCreation1 = System.currentTimeMillis();
            int count1 = BasicOperator.getInstance().count(i1, true);
            long tCount1 = System.currentTimeMillis();
            
            StringIndividual i2 = new StringIndividual(MAX, r);
            long tCreation2 = System.currentTimeMillis();
            int count2 = BasicOperator.getInstance().count(i2, true);
            long tCount2 = System.currentTimeMillis();
            
            Individual i3 = BasicOperator.getInstance().mutate(i1, r);
            long tMutation = System.currentTimeMillis();
            Pair<Individual> p = BasicOperator.getInstance().crossover(i2, i3, r);
            long tCrossover = System.currentTimeMillis();
            int count3 = BasicOperator.getInstance().count(p.getElement1(), true);
            long tCount3 = System.currentTimeMillis();
            
            
            String ret = new String();
            ret += "Class: "+c.getName()+"\n\n";
            
            ret += "First individual:\n";
            ret += "creation time: "+(tCreation1-t0)+" ms\n";
            ret += "count time: "+(tCount1-tCreation1)+" ms\n";
            ret += "number of ones: "+count1+"\n\n";
            
            ret += "Second individual:\n";
            ret += "creation time: "+(tCreation2-tCount1)+" ms\n";
            ret += "count time: "+(tCount2-tCreation2)+" ms\n";
            ret += "number of ones: "+count2+"\n\n";
            
            ret += "mutation time: "+(tMutation-tCount2)+" ms\n";
            ret += "crossover time: "+(tCrossover-tMutation)+" ms\n";
            
            ret += "Last individual:\n";
            ret += "count time: "+(tCount3-tCrossover)+" ms\n";
            ret += "number of ones: "+count3+"\n\n";
            
            System.out.println(ret);
            
        }
        
        
        
    }

    public Sample1() {
    }

    private int countOnesV1(Random r, int size) {
        // First test
        long d1 = System.currentTimeMillis();
        // generating a random string
        String str = newRandomString(r, size);

        // counting
        int countOnes = 0;
        for (int i = 0; i < size; i++) {
            if (str.charAt(i) == '1') {
                countOnes++;
            }
        }
        long duration = System.currentTimeMillis() - d1;
        System.out.println("Count = " + countOnes);
        System.out.println("Duration = " + duration);
        return countOnes;
    }

    private int countOnesV2(Random r, int sizeM) {
        // Second test
        long d1 = System.currentTimeMillis();
        // generating a random string
        int size = sizeM / 8;
        byte buffer[] = newRandomBuffer(r, sizeM);

        // counting
        int countOnes = 0;
        for (int i = 0; i < size; i++) {
            int b = buffer[i];
            for (int x = 0; x < 8; x++) {
                if ((b & (1L << x)) != 0) {
                    countOnes++;
                }
            }
        }
        long duration = System.currentTimeMillis() - d1;
        System.out.println("Count = " + countOnes);
        System.out.println("Duration = " + duration);

        return countOnes;
    }

    private String[] crossoverV1(String[] source, Random r) {

        long d1 = System.currentTimeMillis();

        String ret[] = new String[source.length];
        ret[0] = new String();
        ret[1] = new String();

        int from = r.nextInt(source[0].length()-1);
        int to = from + r.nextInt(source[0].length()-from);
        

        ret[0] = source[0].substring(0, from);
        ret[1] = source[1].substring(0, from);

        ret[0] = source[1].substring(from, to);
        ret[1] = source[0].substring(from, to);

        ret[0] = source[0].substring(to);
        ret[1] = source[1].substring(to);

        long duration = System.currentTimeMillis() - d1;
        System.out.println("Duration = " + duration);

        return ret;
    }

    private byte[][] crossoverV2(byte[][] source, Random r) {
        
        long d1 = System.currentTimeMillis();

        byte ret[][] = new byte[source.length][];
        ret[0] = new byte[source[0].length];
        ret[1] = new byte[source[0].length];

        int from = r.nextInt(source[0].length-1);
        int to = from + r.nextInt(source[0].length-from);
        
        int counter = 0;

        while (counter < from) {
            ret[0][counter] = source[0][counter];
            ret[1][counter] = source[1][counter];
        }

        while (counter < to) {
            ret[0][counter] = source[1][counter];
            ret[1][counter] = source[0][counter];
        }

        while (counter < source[0].length) {
            ret[0][counter] = source[0][counter];
            ret[1][counter] = source[1][counter];
        }

        long duration = System.currentTimeMillis() - d1;
        System.out.println("Duration = " + duration);

        return ret;
    }

    private String mutateV1(String s1, Random r) {
        long d1 = System.currentTimeMillis();


        int pos = r.nextInt(s1.length());
        String ret = new String(s1.substring(0, pos));
        
        ret += s1.charAt(pos) == '0' ? '1' : '0';

        if (pos < s1.length()) {
            ret += s1.substring(pos + 1, s1.length());
        }

        long duration = System.currentTimeMillis() - d1;
        System.out.println("Duration = " + duration);

        return ret;
    }

    private byte[] mutateV2(byte[] s1, Random r) {
        long d1 = System.currentTimeMillis();
        byte[] ret = new byte[s1.length];
        int pos = r.nextInt(s1.length);
        
        int counter = 0;
        while (counter < pos) {
            ret[counter] = s1[counter];
        }
        ret[counter] = s1[counter] == (byte)0L ? (byte)1L : (byte)0L;

        counter++;
        while (counter < pos) {
            ret[counter] = s1[counter];
        }
        
        long duration = System.currentTimeMillis() - d1;
        System.out.println("Duration = " + duration);

        return ret;
        
    }

    private byte[] newRandomBuffer(Random r, int sizeM) {
        byte buffer[] = new byte[sizeM];
        for (int i = 0; i < sizeM; i++) {
            buffer[i] = (byte) r.nextInt(256);
        }
        return buffer;
    }

    private String newRandomString(Random r, int size) {
        StringBuffer stb = new StringBuffer();
        stb.append("");
        for (int i = 0; i < size; i++) {
            stb.append(String.valueOf(r.nextInt(2)));
        }
        String str = stb.toString();
        return str;
    }
}

package com.kcasareo.location;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Kevin on 3/09/2017.
 * Class for testing the accuracy of the location helper.
 */

public class Test extends Positions {
    private StringBuilder text = new StringBuilder();


    public Test() {
        super();
        populate();
    }

    public void populate() {
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "testdata.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            // Line format:
            // x_pos y_pos range
            ArrayList<Position> positions = new ArrayList<>();
            while((line = br.readLine()) != null) {
                Scanner s = new Scanner(line);
                double pos_x = s.nextDouble();
                double pos_y = s.nextDouble();
                double range = s.nextDouble();

//                positions.add(new Position(pos_x,pos_y, range));
                if ( positions.size() == 3) {
                    // Add the three members of the buffer.
                    entries.add(new Entry(positions.get(0), positions.get(1), positions.get(2)));
                    // Flush for the next three;
                    positions.clear();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

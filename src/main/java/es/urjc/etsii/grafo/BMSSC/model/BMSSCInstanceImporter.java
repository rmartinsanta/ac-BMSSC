package es.urjc.etsii.grafo.BMSSC.model;

import es.urjc.etsii.grafo.exception.InstanceImportException;
import es.urjc.etsii.grafo.io.InstanceImporter;

import java.io.BufferedReader;
import java.io.IOException;

public class BMSSCInstanceImporter extends InstanceImporter<BMSSCInstance> {

    private static final String SEPARATOR = ",";

    @Override
    public BMSSCInstance importInstance(BufferedReader reader, String filename) throws IOException {
        // Create and return instance object from file data
        String[] header = reader.readLine().split(SEPARATOR);
        if(header.length != 3){
            throw new InstanceImportException("Invalid instance header, expected 3 fields, got: " + header.length);
        }
        int n = Integer.parseInt(header[0]);
        int d = Integer.parseInt(header[1]);
        int k = Integer.parseInt(header[2]);

        double[][] pointData = new double[n][d];
        for (int i = 0; i < n; i++) {
            String[] lineData = reader.readLine().split(SEPARATOR);
            if(lineData.length != d){
                throw new InstanceImportException("Error at line %s, expected %s dimensions, got %s".formatted(i, d, lineData.length));
            }
            for (int j = 0; j < lineData.length; j++) {
                pointData[i][j] = Double.parseDouble(lineData[j]);
            }
        }

        var instance = new BMSSCInstance(filename, n, d, k,pointData);
        return instance;
    }
}

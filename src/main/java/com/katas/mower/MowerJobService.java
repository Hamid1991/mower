package com.katas.mower;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MowerJobService {

    private final static String SPACE_LIMITER = " ";

    private final static String FIELD_DIMENSIONS_MESSAGE = "the field have the following dimensions ::: XPosition: %d | YPosition: %d%n";

    private static final String MOWER_INITIAL_POSITION_MESSAGE = "the mower %d starts with initial position ::: XPosition: %d | YPosition: %d | Orientation: %s%n";

    private static final String MOWER_INSTRUCTIONS_MESSAGE = "the mower %d have the instructions %s%n";

    private static final String MOWER_JOB_START_MESSAGE = "start the mower job %d%n";

    private static final String MOWER_NEXT_POSITION_MESSAGE = "the mower %d next position is ::: XPosition: %d | YPosition: %d | Orientation: %s%n";

    private static final String MOWER_FINAL_POSITION_MESSAGE = "the mower %d final position is ::: XPosition: %d | YPosition: %d | Orientation: %s%n";

    public static final String FILE_NOT_FOUND_MESSAGE = "mower-job.txt file is not found";

    public static MowerJobResult executeMowersJobs(String mowerJobFilePath) throws Exception {

        MowerJobResult mowerJobResult = new MowerJobResult();
        
        try {
            
            int mowerOrderIndex = 1;

            File mowerJobFile = new File(mowerJobFilePath);
            Scanner mowerJobFileReader = new Scanner(mowerJobFile);

            // extract field dimensions
            String extractedFieldDimensions = mowerJobFileReader.nextLine();
            int xDimension = Integer.parseInt(extractedFieldDimensions.split(SPACE_LIMITER)[0]);
            int yDimension = Integer.parseInt(extractedFieldDimensions.split(SPACE_LIMITER)[1]);
            System.out.printf(FIELD_DIMENSIONS_MESSAGE, xDimension, yDimension);

            FieldDimensions fieldDimensions = new FieldDimensions(xDimension, yDimension);
            
            mowerJobResult.setFieldDimensions(fieldDimensions);

            List<MowerPosition> mowersFinalPositions = new ArrayList<>();

            while (mowerJobFileReader.hasNextLine()) {

                // extract mowerInitialPosition
                String mowerInitialPosition = mowerJobFileReader.nextLine();
                int mowerXPosition = Integer.parseInt(mowerInitialPosition.split(SPACE_LIMITER)[0]);
                int mowerYPosition = Integer.parseInt(mowerInitialPosition.split(SPACE_LIMITER)[1]);
                Orientation mowerOrientation = Orientation.valueOf(mowerInitialPosition.split(SPACE_LIMITER)[2]);

                System.out.printf(MOWER_INITIAL_POSITION_MESSAGE, mowerOrderIndex, mowerXPosition, mowerYPosition, mowerOrientation);

                // extract mowerJobInstructions
                String mowerJobInstructions = mowerJobFileReader.nextLine();
                System.out.printf(MOWER_INSTRUCTIONS_MESSAGE, mowerOrderIndex, mowerJobInstructions);

                // starting mower job
                MowerPosition mowerFinalPosition = executeSingleMowerJob(mowerOrderIndex, mowerJobInstructions, mowerOrientation, mowerYPosition, yDimension, mowerXPosition, xDimension);
                mowersFinalPositions.add(mowerFinalPosition);

                mowerOrderIndex++;
            }

            mowerJobFileReader.close();
            
            mowerJobResult.setMowersFinalPositions(mowersFinalPositions);
            
        } catch (FileNotFoundException e) {
            System.out.println(FILE_NOT_FOUND_MESSAGE);
            throw new Exception(FILE_NOT_FOUND_MESSAGE);
        }

        return mowerJobResult;
    }

    private static MowerPosition executeSingleMowerJob(int mowerOrder, String mowerJobInstructions, Orientation mowerOrientation, int mowerYPosition, int yDimension, int mowerXPosition, int xDimension) {
        System.out.printf(MOWER_JOB_START_MESSAGE, mowerOrder);
        
        for (int i = 0; i <= mowerJobInstructions.length() - 1; i++) {

            char instruction = mowerJobInstructions.charAt(i);

            Instruction instructionValue = Instruction.valueOf(Character.toString(instruction));

            switch (instructionValue) {
                case A -> {
                    switch (mowerOrientation) {
                        case N ->
                                mowerYPosition = mowerYPosition + 1 > yDimension ? mowerYPosition : mowerYPosition + 1;
                        case S ->
                                mowerYPosition = mowerYPosition - 1 == -1 ? mowerYPosition : mowerYPosition - 1;
                        case E ->
                                mowerXPosition = mowerXPosition + 1 > xDimension ? mowerXPosition : mowerXPosition + 1;
                        case W ->
                                mowerXPosition = mowerXPosition - 1 == -1 ? mowerXPosition : mowerXPosition - 1;
                    }
                }

                case D -> {
                    switch (mowerOrientation) {
                        case N -> mowerOrientation = Orientation.E;
                        case S -> mowerOrientation = Orientation.W;
                        case E -> mowerOrientation = Orientation.S;
                        case W -> mowerOrientation = Orientation.N;
                    }
                }

                case G -> {
                    switch (mowerOrientation) {
                        case N -> mowerOrientation = Orientation.W;
                        case S -> mowerOrientation = Orientation.E;
                        case E -> mowerOrientation = Orientation.N;
                        case W -> mowerOrientation = Orientation.S;
                    }
                }
            }

            System.out.printf(MOWER_NEXT_POSITION_MESSAGE, mowerOrder, mowerXPosition, mowerYPosition, mowerOrientation);
        }

        System.out.printf(MOWER_FINAL_POSITION_MESSAGE, mowerOrder, mowerXPosition, mowerYPosition, mowerOrientation);

        return new MowerPosition(mowerXPosition, mowerYPosition, mowerOrientation);
    }
}
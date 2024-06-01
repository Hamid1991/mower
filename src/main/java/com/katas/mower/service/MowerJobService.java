package com.katas.mower.service;

import com.katas.mower.data.*;
import com.katas.mower.exceptions.InvalidMowerJobFileInputException;
import com.katas.mower.exceptions.OutOfFieldDimensionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.String.format;

public class MowerJobService {

    private final static String SPACE_LIMITER = " ";

    private final static String FIELD_DIMENSIONS_MESSAGE = "the field have the following dimensions ::: XPosition: %d | YPosition: %d%n";

    private static final String MOWER_INITIAL_POSITION_MESSAGE = "the mower %d starts with initial position ::: XPosition: %d | YPosition: %d | Orientation: %s%n";

    private static final String MOWER_INSTRUCTIONS_MESSAGE = "the mower %d have the instructions %s%n";

    private static final String MOWER_JOB_START_MESSAGE = "start the mower job %d%n";

    private static final String MOWER_NEXT_POSITION_MESSAGE = "the mower %d next position is ::: XPosition: %d | YPosition: %d | Orientation: %s%n";

    private static final String MOWER_FINAL_POSITION_MESSAGE = "the mower %d final position is ::: XPosition: %d | YPosition: %d | Orientation: %s%n";

    public static final String FILE_NOT_FOUND_MESSAGE = "%s file is not found%n";

    public static final String INVALID_MOWER_JOB_FILE_INPUT = "the %s input is not valid%n";

    public static final String OUT_OF_FIELD_DIMENSIONS_MESSAGE = "mower initial position : XPosition %d | YPosition %d is out of field dimensions : xDimension %d | yDimension %d%n";

    public static final String FIELD_DIMENSIONS_REGEX = "^\\d\\s\\d$";

    public static final String MOWER_INITIAL_POSITION_REGEX = "^\\d\\s\\d\\s(N|E|W|S)+$";

    public static final String INSTRUCTIONS_REGEX = "^(A|D|G)*$";

    public static MowerJobResult executeMowersJobs(String mowerJobFilePath) throws Exception {

        MowerJobResult mowerJobResult = new MowerJobResult();

        File mowerJobFile = null;
        try {

            int mowerOrderIndex = 1;

            mowerJobFile = new File(mowerJobFilePath);
            Scanner mowerJobFileReader = new Scanner(mowerJobFile);

            // extract field dimensions
            String extractedFieldDimensions = mowerJobFileReader.nextLine();

            if (!extractedFieldDimensions.matches(FIELD_DIMENSIONS_REGEX)) {
                System.out.printf(INVALID_MOWER_JOB_FILE_INPUT, "field dimensions");
                throw new InvalidMowerJobFileInputException(format(INVALID_MOWER_JOB_FILE_INPUT, "field dimensions"));
            }

            var xDimension = Integer.parseInt(extractedFieldDimensions.split(SPACE_LIMITER)[0]);
            var yDimension = Integer.parseInt(extractedFieldDimensions.split(SPACE_LIMITER)[1]);
            System.out.printf(FIELD_DIMENSIONS_MESSAGE, xDimension, yDimension);

            var fieldDimensions = new FieldDimensions(xDimension, yDimension);

            mowerJobResult.setFieldDimensions(fieldDimensions);

            List<MowerPosition> mowersFinalPositions = new ArrayList<>();

            while (mowerJobFileReader.hasNextLine()) {

                // extract mowerInitialPosition
                var mowerInitialPosition = mowerJobFileReader.nextLine();

                if (!mowerInitialPosition.matches(MOWER_INITIAL_POSITION_REGEX)) {
                    System.out.printf(INVALID_MOWER_JOB_FILE_INPUT, "mower initial position");
                    throw new InvalidMowerJobFileInputException(format(INVALID_MOWER_JOB_FILE_INPUT, "mower initial position"));
                }

                var mowerXPosition = Integer.parseInt(mowerInitialPosition.split(SPACE_LIMITER)[0]);
                var mowerYPosition = Integer.parseInt(mowerInitialPosition.split(SPACE_LIMITER)[1]);
                var mowerOrientation = Orientation.valueOf(mowerInitialPosition.split(SPACE_LIMITER)[2]);

                if (mowerXPosition > xDimension || mowerYPosition > yDimension) {
                    System.out.printf(OUT_OF_FIELD_DIMENSIONS_MESSAGE, mowerXPosition, mowerYPosition, xDimension, yDimension);
                    throw new OutOfFieldDimensionException(format(OUT_OF_FIELD_DIMENSIONS_MESSAGE, mowerXPosition, mowerYPosition, xDimension, yDimension));
                }
                System.out.printf(MOWER_INITIAL_POSITION_MESSAGE, mowerOrderIndex, mowerXPosition, mowerYPosition, mowerOrientation);

                // extract mowerJobInstructions
                var mowerJobInstructions = mowerJobFileReader.nextLine();

                if (!mowerJobInstructions.matches(INSTRUCTIONS_REGEX)) {
                    System.out.printf(INVALID_MOWER_JOB_FILE_INPUT, "instructions");
                    throw new InvalidMowerJobFileInputException(format(INVALID_MOWER_JOB_FILE_INPUT, "instructions"));
                }

                System.out.printf(MOWER_INSTRUCTIONS_MESSAGE, mowerOrderIndex, mowerJobInstructions);

                // starting mower job
                MowerPosition mowerFinalPosition = executeSingleMowerJob(mowerOrderIndex, mowerJobInstructions, mowerOrientation, mowerYPosition, yDimension, mowerXPosition, xDimension);
                mowersFinalPositions.add(mowerFinalPosition);

                mowerOrderIndex++;
            }

            mowerJobFileReader.close();

            mowerJobResult.setMowersFinalPositions(mowersFinalPositions);

        } catch (FileNotFoundException e) {
            var mowerJobFileName = mowerJobFile.getName();
            System.out.printf(FILE_NOT_FOUND_MESSAGE, mowerJobFileName);
            throw new Exception(format(FILE_NOT_FOUND_MESSAGE, mowerJobFileName));
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
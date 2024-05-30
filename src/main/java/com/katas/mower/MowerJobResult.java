package com.katas.mower;

import java.util.List;

public class MowerJobResult {

    private FieldDimensions fieldDimensions;

    List<MowerPosition> mowersFinalPositions;

    public FieldDimensions getFieldDimensions() {
        return fieldDimensions;
    }

    public void setFieldDimensions(FieldDimensions fieldDimensions) {
        this.fieldDimensions = fieldDimensions;
    }

    public List<MowerPosition> getMowersFinalPositions() {
        return mowersFinalPositions;
    }

    public void setMowersFinalPositions(List<MowerPosition> mowersFinalPositions) {
        this.mowersFinalPositions = mowersFinalPositions;
    }
}

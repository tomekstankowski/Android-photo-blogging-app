package com.tomaszstankowski.trainingapplication.event;

import java.io.File;

public class TempImageFileTransferEvent {
    public final File file;
    public final int requestCode;

    public TempImageFileTransferEvent(File file, int requestCode) {
        this.file = file;
        this.requestCode = requestCode;
    }
}

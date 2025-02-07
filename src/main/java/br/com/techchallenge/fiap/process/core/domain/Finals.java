/*
 * Copyright (c) 2025. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.core.domain;


import com.xuggle.xuggler.Global;

public class Finals {


    public static final double SECONDS_BETWEEN_FRAMES = 1;
    public static final String inputFilename = "C:\\Videos\\";
    public static final String outputFilePrefix = "C:\\Videos\\";
    public static int mVideoStreamIndex = -1;
    public static long mLastPtsWrite = Global.DEFAULT_PTS_PER_SECOND;
    public static final long MICRO_SECONDS_BETWEEN_FRAMES =
            (long) (Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);
}


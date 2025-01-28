package br.com.techchallenge.fiap.process.core.domain;/*
 * Copyright (c) 2024. MiguelProgrammer
 */

import com.xuggle.xuggler.Global;

@SuppressWarnings("test")
public class Finals {


    public static final double SECONDS_BETWEEN_FRAMES = 3;
    public static final String inputFilename = "C:\\Videos\\";
    public static final String outputFilePrefix = "C:\\Videos\\";
    public static int mVideoStreamIndex = -1;
    public static long mLastPtsWrite = Global.DEFAULT_PTS_PER_SECOND;
    public static final long MICRO_SECONDS_BETWEEN_FRAMES =
            (long) (Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);

    public static final String MESSAGE_ADM_ESTOQUE =
            "Caro adm, por favor, veja a quantia de itens cadastrado no estoque!";



    public static final String MESSAGE_RECEBIDO = "" +
            "______________________________\n\n" +
            "Pedido 'Recebido'.\n" +
            "Em instantes será concluído. \n\n" +
            "SELECIONE:\n" +
            "1 - Realizar pagamento \n2 - Alterar pedido \n\n" +
            "______________________________\n\n";

    public static final String MESSAGE_FINALIZADO =
            "______________________________\n\n" +
                    "Pedido Finalizado.\n\n" +
                    "Como você é nosso cliente especial, em isntantes você receberá uma notficação" +
                    " contendo alguns mimos, obrigado e volte sempre \n\n" +
                    "______________________________\n\n";

    public static final String MESSAGE_PREPARACAO =
            "______________________________\n\n" +
                    "Pedido Em preparação.\n\n" +
                    "Em instantes será concluído. \n\n" +
                    "Somente aguarde, obrigado.\n\n" +
                    "______________________________\n\n";

    public static final String MESSAGE_PRONTO =
            "______________________________\n\n" +
                    "Pedido está Pronto.\n\n" +
                    "Retire o quanto antes.\n\n" +
                    "______________________________\n\n";
}


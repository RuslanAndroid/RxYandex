package ru.translator.util;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.Synthesis;
import ru.yandex.speechkit.Vocalizer;

/**
 * Created by Ruslan on 07.06.2017.
 */

public abstract class SpeechNRecognicionListener implements ru.yandex.speechkit.RecognizerListener,ru.yandex.speechkit.VocalizerListener {
    @Override
    public void onRecordingBegin(Recognizer recognizer) {

    }

    @Override
    public void onSpeechDetected(Recognizer recognizer) {

    }

    @Override
    public void onSpeechEnds(Recognizer recognizer) {

    }

    @Override
    public void onRecordingDone(Recognizer recognizer) {

    }

    @Override
    public void onSoundDataRecorded(Recognizer recognizer, byte[] bytes) {

    }

    @Override
    public void onPowerUpdated(Recognizer recognizer, float v) {

    }

    @Override
    public void onPartialResults(Recognizer recognizer, Recognition recognition, boolean b) {

    }

    @Override
    public void onRecognitionDone(Recognizer recognizer, Recognition recognition) {

    }

    @Override
    public void onError(Recognizer recognizer, Error error) {

    }

    @Override
    public void onSynthesisBegin(Vocalizer vocalizer) {

    }

    @Override
    public void onSynthesisDone(Vocalizer vocalizer, Synthesis synthesis) {

    }

    @Override
    public void onPlayingBegin(Vocalizer vocalizer) {

    }

    @Override
    public void onPlayingDone(Vocalizer vocalizer) {

    }

    @Override
    public void onVocalizerError(Vocalizer vocalizer, Error error) {

    }
}

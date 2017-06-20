package ru.translator.ui.translate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.translator.App;
import ru.translator.R;
import ru.translator.adapters.DictionaryAdapter;
import ru.translator.ui.chooseLanguage.LanguageChooseFragment;
import ru.translator.interfaces.HistoryClickListener;

import ru.translator.models.Tr;
import ru.yandex.speechkit.Recognition;

import static com.jakewharton.rxbinding2.widget.RxTextView.textChanges;


//Экран перевода
public class TranslateFragment extends Fragment implements  TranslateFragmentView{

    @BindView(R.id.dict_list)            RecyclerView    resultList;
    @BindView(R.id.res_edt)              EditText        text;
    @BindView(R.id.translate_result)     TextView        translateAfter;
    @BindView(R.id.language_to)          TextView        toLangeage;
    @BindView(R.id.translate_custom)     TextView        translateBefore;
    @BindView(R.id.language_from)        TextView        fromLanguage;
    @BindView(R.id.rel_result)           RelativeLayout  relResult;
    @BindView(R.id.din)                  ImageView       sound;
    @BindView(R.id.din_result)           ImageView       soundResult;
    @BindView(R.id.clear)                ImageView       clear;
    @BindView(R.id.mic)                  ImageView       mic;


    @Inject public TranslateFragmentPresenter mPresenter;

    private         String                          languageTo              ="en",
                                                    languageFrom            ="ru";
    public static   HistoryClickListener            mHistoryClickListener;
    private         DictionaryAdapter               mDictionaryAdapter;
    private         FragmentManager mManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getDeps().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translate, container, false);

        ButterKnife.bind(this, view);
        mPresenter = new TranslateFragmentPresenterImpl();
        mPresenter.setView(this);

        mManager = getActivity().getSupportFragmentManager();
        setList();

        fromLanguage.setOnClickListener (v -> chooseLanguage(false));
        toLangeage  .setOnClickListener (v -> chooseLanguage(true));
        relResult   .setOnClickListener (v -> shareText(translateAfter.getText().toString()));
        mic         .setOnClickListener (v -> mPresenter.createAndStartRecognizer());
        sound       .setOnClickListener (v -> mPresenter.createAndStartVocalizer(text.getText().toString()));
        clear       .setOnClickListener (v -> text.getText().clear());
        soundResult .setOnClickListener (v -> mPresenter.createAndStartVocalizer(translateAfter.getText().toString()));

        mHistoryClickListener = (fromLang, toLang, fromLangText, toLangText, textToTranslate) -> {
            mPresenter.setLanguage(fromLang, toLang, fromLangText, toLangText);
            languageFrom=fromLang;
            languageTo=toLang;
            fromLanguage.setText(fromLangText);
            toLangeage.setText(toLangText);
            text.setText(textToTranslate);
        };

        textChanges(text)
                .map(CharSequence::toString)
                .subscribe(s -> {
                    if (s.length()!=0){
                        mPresenter.doTranslate(s);
                    }else {
                        mDictionaryAdapter.setInfo(null);
                        relResult.setVisibility(View.GONE);
                    }
                });
        return view;
    }

    private void setList(){
        mDictionaryAdapter = new DictionaryAdapter();
        resultList.setLayoutManager(new LinearLayoutManager(getActivity()));
        resultList.setAdapter(mDictionaryAdapter);
    }

    //отправить переведенный текст
    private void shareText(String textToShare){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.send_text));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, textToShare);
        startActivity(sharingIntent);
    }

    //переход к выбору языка
    private void chooseLanguage(final boolean dest){
        hideKeyBoard();
        String language = dest ? languageFrom : languageTo;

        LanguageChooseFragment languageChooseFragment =  LanguageChooseFragment.getInstance(dest,language);
        languageChooseFragment.setOnLanguageSelectListener((languageKey, languageValue) -> {
            getActivity().onBackPressed();

            if(dest){
                languageTo=languageKey;
                toLangeage.setText(languageValue);
            }else{
                languageFrom=languageKey;
                fromLanguage.setText(languageValue);
            }

            mPresenter.setLanguage(languageFrom,languageTo,fromLanguage.getText().toString(),toLangeage.getText().toString());

            if (text.getText().toString().length()!=0){
                mPresenter.doTranslate(text.getText().toString());
            }else {
                mDictionaryAdapter.setInfo(null);
                relResult.setVisibility(View.GONE);
            }
        });

        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left,
                R.anim.slide_out_right, R.anim.slide_in_right);
        transaction.add(R.id.first_conteiner, languageChooseFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        hideKeyBoard();
        mPresenter.onPause();
    }

    private void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(text.getWindowToken(), 0);
    }

    @Override
    public void onShortTranslate(String s) {
        relResult.setVisibility(View.VISIBLE);
        translateBefore.setText(text.getText().toString());
        translateAfter.setText(s);
    }

    @Override
    public void onFullTranslate(List<Tr> trs) {
        mDictionaryAdapter.setInfo(trs);
    }

    @Override
    public void onRecordingBegin() {
        Toast.makeText(getActivity(), R.string.speek,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(@StringRes int resId) {
        Toast.makeText(getActivity(), resId ,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPartialRecognizerResults(Recognition recognition) {
        text.setText(recognition.getBestResultText());
    }

    @Override
    public void onRecognitionDone(Recognition recognition) {
        text.setText(recognition.getBestResultText());
    }
}

package com.prashanth.imageapp.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.snackbar.Snackbar;
import com.prashanth.imageapp.R;
import com.prashanth.imageapp.ShutterstockImageApplication;
import com.prashanth.imageapp.adapter.ImageListAdapter;
import com.prashanth.imageapp.contract.APIContract;
import com.prashanth.imageapp.model.ImageDetails;
import com.prashanth.imageapp.network.ShutterstockAPI;
import com.prashanth.imageapp.presenter.ImageDetailsPresenter;
import com.prashanth.imageapp.utils.Constants;
import java.util.ArrayList;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements APIContract.ImageListView, EditText.OnEditorActionListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.search_edittext)
    EditText searchEditText;

    @Inject
    ShutterstockAPI api;

    @Inject
    ImageListAdapter adapter;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @Inject
    ImageDetailsPresenter presenter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShutterstockImageApplication.component.inject(this);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        searchEditText.setOnEditorActionListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.unsubscribe();
            presenter.onDestroy();
        }
    }

    @Override
    public void callStarted() {
        showProgressDialog();
    }

    @Override
    public void callComplete() {
        dismissProgressDialog();
    }

    @Override
    public void callFailed(Throwable throwable) {
        dismissProgressDialog();
        showSnackBarError();
    }

    @Override
    public void onResponseImageList(ArrayList<ImageDetails> imageDetails) {
        adapter.update(imageDetails);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String searchString = textView.getText().toString().trim();
            if (!searchString.isEmpty()) {
                onSearchClicked(searchString);
            }
            return true;
        }
        return false;
    }

    private void onSearchClicked(String searchString) {
        hideKeyboard();
        searchEditText.setText(searchString);
        presenter.fetchImages(Constants.CATEGORY, searchString, null,
                Constants.PAGE_NUMBER, Constants.MAX_ITEMS_PER_PAGE, this);
    }

    private void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    private void showProgressDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }
    }

    private void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showSnackBarError() {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.error), Snackbar.LENGTH_LONG)
                .show();
    }

}

package com.artifex.mupdf.viewer;

import android.graphics.RectF;

public class SearchTaskResult {
    static private SearchTaskResult singleton;
    public final String txt;
    public final int pageNumber;
    public final RectF searchBoxes[];

    SearchTaskResult(String _txt, int _pageNumber, RectF _searchBoxes[]) {
        txt = _txt;
        pageNumber = _pageNumber;
        searchBoxes = _searchBoxes;
    }

    static public SearchTaskResult get() {
        return singleton;
    }

    static public void set(SearchTaskResult r) {
        singleton = r;
    }
}

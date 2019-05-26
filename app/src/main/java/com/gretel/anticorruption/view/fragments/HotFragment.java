package com.gretel.anticorruption.view.fragments;

import com.gretel.anticorruption.R;

public class HotFragment extends ReportFragment {

    @Override
    protected void setQuery() {
        myLastQuery = myReportDatabase.orderByChild("rank").limitToLast(100);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_latest;
    }
}

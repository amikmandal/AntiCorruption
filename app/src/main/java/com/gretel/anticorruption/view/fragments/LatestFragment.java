package com.gretel.anticorruption.view.fragments;

import com.gretel.anticorruption.R;

public class LatestFragment extends ReportFragment {

    @Override
    protected void setQuery() {
        myLastQuery = myReportDatabase.orderByChild("timestamp").limitToLast(2);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_latest;
    }
}
